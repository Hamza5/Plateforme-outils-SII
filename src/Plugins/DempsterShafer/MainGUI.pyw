#!/usr/bin/env python3
import sys
import traceback
import re
import subprocess
from os.path import join, splitext, dirname, realpath

try:
    from PyQt4.QtGui import QApplication, QMainWindow, QActionGroup, QDialog, QStandardItem, QStandardItemModel, \
        QInputDialog, QHeaderView, QLineEdit, QMessageBox, QFileDialog, QCloseEvent, QTableWidgetItem, QMovie, QLabel\
        , QIcon
    from PyQt4.QtCore import SIGNAL, QModelIndex, Qt, QThread, QMimeData, QTranslator
except ImportError as e:
    print('Can not use PyQt4 !', e.msg, file=sys.stderr, sep='\n')
    sys.exit(2)

try:
    from lxml.etree import ElementTree, Element, tostring, XMLSchema, ParseError
except ImportError:
    from xml.etree.ElementTree import Element, ElementTree, tostring, ParseError
    from xml.dom.minidom import parseString

from HelperClasses.Etat import Etat
from HelperClasses.Hypothese import Hypothese
from HelperClasses.Agent import Agent
from UI.MainWindow import Ui_MainWindow
from UI.HypotheseDialog import Ui_hypothesesDialog
from UI.AgentDialog import Ui_agentDialog
from UI.MasseDialog import Ui_masseDialog
from UI.DescriptionDialog import Ui_descriptionDialog
from UI.ResultatsDialog import Ui_resultsDialog
from UI.WaitDialog import Ui_waitDialog
import HelperClasses.Etat
import HelperClasses.Agent


__author__ = 'Hamza Abbad'
app_name = 'Combinateur d\'évidences'
devloppers = 'Hamza Abbad & Ahmed Zebouchi'
_version = '1.0.1'


class MainWindow(QMainWindow, Ui_MainWindow):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.setupUi(self)
        self.setWindowIcon(QIcon("logo.png"))
        self.untitled = "Sans titre"
        self.title = self.untitled
        self.description = ""
        self.agentsModelHeaderLabels = ["Agent/Hypothèse", "Fiabilité/Masse", "Activé"]
        self.setUnmodified()
        self.executable = 'Main'  # Command of the engine executable
        self.input = ''  # Input file for calculation
        self.output = ''  # Output file of calculation
        self.round_digits = 3
        self.last_path = ""

        # Make methods actions mutually-exclusives :
        self.action_group = QActionGroup(self)
        self.action_group.addAction(self.actionDempster_Shafer)
        self.action_group.addAction(self.actionDubois_Prade)
        self.action_group.addAction(self.actionSmets)
        self.action_group.addAction(self.actionYager)
        self.actionDempster_Shafer.setData('Dempster-Shafer')
        self.actionDubois_Prade.setData('Dubois-Prade')
        self.actionSmets.setData('Smets')
        self.actionYager.setData('Yager')

        # Make decision actions mutually-exclusives :
        self.action_group2 = QActionGroup(self)
        self.action_group2.addAction(self.actionOptimiste)
        self.action_group2.addAction(self.actionPessimiste)
        self.action_group2.addAction(self.actionPignistique)
        self.actionOptimiste.setData('Optimiste')
        self.actionPessimiste.setData('Pessimiste')
        self.actionPignistique.setData('Pignistique')

        # Center the main window on the screen
        self.move(QApplication.desktop().availableGeometry().center() - self.rect().center())

        # Create the models
        self.etatsModel = ItemModel(self.etatsListView)
        self.hypothesesModel = ItemModel(self.hypothesesListView)
        self.agentsModel = ItemModel(self.agentsTreeView)
        self.agentsModel.setHorizontalHeaderLabels(self.agentsModelHeaderLabels)

        # Assign the models
        self.etatsListView.setModel(self.etatsModel)
        self.hypothesesListView.setModel(self.hypothesesModel)
        self.agentsTreeView.setModel(self.agentsModel)
        self.agentsTreeView.header().setResizeMode(QHeaderView.ResizeToContents)

        # Create the context menus
        self.etatsListView.addActions([self.actionAjouterEtat, self.actionSupprimerEtat])
        self.hypothesesListView.addActions([self.actionAjouterHypothese, self.actionSupprimerHypothese])
        self.agentsTreeView.addActions([self.actionAjouterAgent, self.actionModifierAgent, self.actionSupprimerAgent])
        self.etatsListView.setContextMenuPolicy(Qt.ActionsContextMenu)
        self.hypothesesListView.setContextMenuPolicy(Qt.ActionsContextMenu)
        self.agentsTreeView.setContextMenuPolicy(Qt.ActionsContextMenu)

        # Add the default status message
        self.statusbar.addWidget(QLabel(app_name + ' ' + _version + ' - '+devloppers))

        # Connecting Signals to slots :
        self.connect(self.actionAjouterEtat, SIGNAL("triggered(bool)"), self.ajouterEtat)
        self.connect(self.actionSupprimerEtat, SIGNAL("triggered(bool)"), self.suprimmerEtat)
        self.connect(self.actionAjouterHypothese, SIGNAL("triggered(bool)"), self.ajouterHypothese)
        self.connect(self.actionSupprimerHypothese, SIGNAL("triggered(bool)"), self.supprimerHypothese)
        self.connect(self.actionAjouterAgent, SIGNAL("triggered(bool)"), self.ajouterAgent)
        self.connect(self.actionSupprimerAgent, SIGNAL("triggered(bool)"), self.supprimerAgent)
        self.connect(self.actionTitre, SIGNAL("triggered(bool)"), self.attribuer_titre)
        self.connect(self.actionDescription, SIGNAL("triggered(bool)"), self.attribuer_description)
        self.connect(self.actionQuitter, SIGNAL("triggered(bool)"), self.close)
        self.connect(self.actionEnregistrer_1, SIGNAL("triggered(bool)"), self.enregistrer)
        self.connect(self.actionEnregistrer, SIGNAL("triggered(bool)"), self.enregistrer_sous)
        self.connect(self.actionModifierAgent, SIGNAL("triggered(bool)"), self.modifierAgent)
        self.connect(self.actionOuvrir, SIGNAL("triggered(bool)"), self.ouvrir)
        self.connect(self.actionNouveau, SIGNAL("triggered(bool)"), self.nouveau)
        self.connect(self.actionCalculer, SIGNAL("triggered(bool)"), self.calculer)
        self.connect(self.action_group, SIGNAL("triggered(QAction *)"), self.setModified)
        self.connect(self.actionOuvrir_des_resultats, SIGNAL("triggered(bool)"), self.afficher)
        self.connect(self.action_group2, SIGNAL("triggered(QAction *)"), self.setModified)

    def attribuer_titre(self):
        title, ok = QInputDialog.getText(self, "Titre", "Titre", QLineEdit.Normal, self.title)
        if ok:
            if title:
                self.title = title
            else:
                self.title = self.untitled
            self.setModified()

    def attribuer_description(self):
        description_dialog = DescriptionDialog(self, self.description)
        if description_dialog.exec_() == QDialog.Accepted:
            self.description = description_dialog.get_description()
            self.setModified()

    def nouveau(self):
        if self.edited:
            msg = QMessageBox(self)
            msg.setWindowTitle('Changements non sauvegardés')
            msg.setText('<b>Vous n\'avez pas sauvegardé vos changements</b>')
            msg.setInformativeText('Voulez vous continuer ?')
            msg.setStandardButtons(QMessageBox.Yes | QMessageBox.No)
            msg.setIcon(QMessageBox.Warning)
            ok = msg.exec_()
            if ok == QMessageBox.No:
                return False
        # Empty the models
        self.title = self.untitled
        self.description = ''
        self.actionDempster_Shafer.setChecked(True)
        self.actionOptimiste.setChecked(True)
        self.etatsModel.clear()
        self.hypothesesModel.clear()
        self.agentsModel.clear()
        self.agentsModel.setHorizontalHeaderLabels(self.agentsModelHeaderLabels)
        self.setUnmodified()
        self.input = ''
        self.output = ''
        return True

    def enregistrer(self):
        if self.input:
            return self.enregistrer_sous(self.input)
        else:
            file_path = QFileDialog.getSaveFileName(self, 'Enregistrer', self.last_path, 'Données (*.dsti.xml)')
            if not file_path:
                return False
            return self.enregistrer_sous(file_path)

    def enregistrer_sous(self, path=''):
        if isinstance(path, str) and path != '':
            file_path = path
        else:
            file_path = QFileDialog.getSaveFileName(self, 'Enregistrer sous', self.last_path, 'Données (*.dsti.xml)')
        if not file_path:
            return False
        try:  # Using xml.etree.ElementTree
            root = Element('DSTI')
        except ValueError:  # Using lxml.etree
            root = Element('DSTI')
        title = Element('Title')
        title.text = self.title
        root.append(title)
        description = Element('Description')
        description.text = self.description
        root.append(description)
        method = Element('Method')
        method.text = self.action_group.checkedAction().data()
        root.append(method)
        decision = Element('Decision')
        decision.text = self.action_group2.checkedAction().data()
        root.append(decision)
        etats = Element('Etats')
        for état_item in self.etatsModel:
            etats.append(Element('Etat', {'id': état_item.item.id(), 'title': str(état_item)}))
        root.append(etats)
        hypotheses = Element('Hypotheses')
        for hypothèse_item in self.hypothesesModel:
            hypotheses.append(Element('Hypothese', {'id': hypothèse_item.item.id()}))
        root.append(hypotheses)
        agents = Element('Agents')
        for agent_item in self.agentsModel:
            agent = Element('Agent',
                            {'id': agent_item.item.id(), 'name': str(agent_item),
                             'reliability': str(agent_item.item.reliability),
                             'disabled': str(agent_item.item.disabled).lower()
                             })
            for hmw in agent_item.item:
                agent.append(Element('Knowledge', {'id': hmw[0].id(), 'mass': str(hmw[1])}))
            agents.append(agent)
        root.append(agents)
        try:  # Using xml.etree.ElementTree
            dom = parseString(tostring(root, encoding='utf-8'))
            pretty_xml = dom.toprettyxml(encoding='UTF-8')
        except NameError:  # Using lxml.etree
            pretty_xml = tostring(root, encoding='UTF-8', xml_declaration=True, pretty_print=True)
        try:
            file = open(file_path, mode='wb')
            file.write(pretty_xml)
            file.close()
            self.setUnmodified()
            self.input = file_path
            self.output = splitext(splitext(self.input)[0])[0] + '.dsto.xml'
            self.last_path = dirname(self.output)
            return True
        except OSError:
            QMessageBox.critical(self, 'Erreur', '<b>Impossible de sauvegarder le fichier</b><br>'+file_path)
            return False

    def ouvrir(self):
        file_path = QFileDialog.getOpenFileName(self, 'Ouvrir', self.last_path, 'Données (*.dsti.xml)')
        if not file_path:
            return
        # Create the document
        try:
            tree = ElementTree(file=file_path)
        except OSError:  # Can't open the file
            QMessageBox.critical(self, 'Erreur', '<b>Impossible d\'ouvrir le fichier</b><br>'+file_path)
            return
        except ParseError:  # XML contains syntax errors
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>Document invalide !</b>')
            msg.setInformativeText('Le document contient des erreurs')
            msg.setIcon(QMessageBox.Critical)
            msg.exec_()
            return
        except SyntaxError:
            QMessageBox.critical(self, 'Erreur', '<b>Le fichier est invalide</b>')
            return
        try:
            schema = XMLSchema(file=join(dirname(realpath(__file__)), 'validation.xsd'))
            if not schema(tree):
                msg = QMessageBox(self)
                msg.setWindowTitle('Erreur')
                msg.setText('<b>Document invalide !</b>')
                msg.setInformativeText('Vérifier que vous avez choisie le bon fichier')
                msg.setIcon(QMessageBox.Critical)
                msg.exec_()
                return
        except NameError:  # lxml library not found, can't validate the document
            # msg = QMessageBox(self)
            # msg.setWindowTitle('Erreur')
            # msg.setText('<b>La bibliothèque <i>lxml</i> est introuvable</b>')
            # msg.setInformativeText('Le programme ne peut pas assurer la validité de du fichier choisie')
            # msg.setIcon(QMessageBox.Warning)
            # msg.exec_()
            pass
        if not self.nouveau():
            return
        try:
            # The title and the description
            self.title = tree.find('Title').text
            self.setWindowTitle(app_name + ' ' + _version + (' - ' + self.title if self.title != '' else ''))
            self.description = tree.find('Description').text
            # The method
            method = tree.find('Method').text
            for action in self.action_group.actions():
                if method == action.data():
                    action.setChecked(True)
                    break
            decision = tree.find('Decision').text
            for action in self.action_group2.actions():
                if decision == action.data():
                    action.setChecked(True)
                    break
            # Les états
            highest_id = 0  # For états
            for element in tree.iter('Etat'):
                état = Etat(element.get('title'))
                état.order = int(re.search(r'\d+', element.attrib['id']).group())
                highest_id = max(highest_id, état.order)
                état_item = ObjectItem(état)
                if état_item not in self.etatsModel:  # Check if the état isn't doubled
                    self.etatsModel.appendRow(état_item)
                else:
                    raise ValueError
            # To protect old états IDs from being overwritten when editing a saved file
            HelperClasses.Etat.idf = highest_id+1
            # Les hypothèses
            for element in tree.iter('Hypothese'):
                états = []
                ids = element.attrib['id'].split('-')
                for idf in ids:
                    for état_item in self.etatsModel:
                        if état_item.item.id() == idf and état_item.item not in états:  # The état mustn't be doubled
                            états.append(état_item.item)
                if len(états) < len(ids):  # Can't find some états
                    raise ValueError
                hypothèse = Hypothese(états)
                for hypothèse_item in self.hypothesesModel:
                    if hypothèse == hypothèse_item.item:
                        raise ValueError
                self.hypothesesModel.appendRow(ObjectItem(hypothèse))
            # Les agents
            highest_id = 0  # For agents
            for element in tree.iter('Agent'):
                agent = Agent(element.get('name'), float(element.get('reliability')))
                agent.disable(element.get('disabled') == 'true')
                agent.order = int(re.search(r'\d+', element.attrib['id']).group())
                highest_id = max(highest_id, agent.order)
                mass_sum = 0
                for hypothèse_element in element:
                    found = False
                    for hypothèse_item in self.hypothesesModel:
                        if hypothèse_element.attrib['id'] == hypothèse_item.item.id():
                            mass = float(hypothèse_element.attrib['mass'])
                            if hypothèse_item.item not in agent:  # Check if the hypothesis isn't doubled
                                agent.add_hypothese(hypothèse_item.item, mass)
                            else:
                                raise ValueError
                            mass_sum += mass
                            found = True
                            break
                    if not found:
                        raise ValueError
                if mass_sum > 1:
                    raise ValueError
                agent_item = ObjectItem(agent)
                for hmw in agent:
                    agent_item.appendRow([ObjectItem(hmw[0]), ObjectItem(hmw[1])])
                if agent_item not in self.agentsModel:
                    self.agentsModel.appendRow([agent_item, ObjectItem(agent.reliability),
                                                ObjectItem('Désactivé' if agent.disabled else 'Activé')])
                else:
                    raise ValueError
            HelperClasses.Agent.idf = highest_id+1
            self.input = file_path
            self.output = splitext(splitext(self.input)[0])[0] + '.dsto.xml'
            self.last_path = dirname(self.input)
        except (ValueError, KeyError, AttributeError):
            self.nouveau()
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>Document invalide !</b>')
            msg.setInformativeText('Vérifier que vous avez choisie le bon fichier')
            msg.setIcon(QMessageBox.Critical)
            msg.exec_()

    def ajouterEtat(self):
        état, ok = QInputDialog.getText(self, "Ajouter un état", "Etat")
        if ok and état:
            item = ObjectItem(Etat(état))
            if item not in self.etatsModel:
                self.etatsModel.appendRow(item)
            else:
                msg = QMessageBox(self)
                msg.setWindowTitle('Erreur')
                msg.setText("<b>L'état '" + str(état) + "' existe dans la liste !</b>")
                msg.setInformativeText('Vous ne pouvez pas ajouter un état plus d\'une fois')
                msg.setIcon(QMessageBox.Warning)
                msg.exec_()
                return
        else:
            return
        self.setModified()

    def suprimmerEtat(self):
        if self.etatsModel.rowCount() == 0:
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>Aucun état trouvé !</b>')
            msg.setInformativeText('Vous devez ajouter un état d\'abord')
            msg.setIcon(QMessageBox.Warning)
            msg.exec_()
            return
        selection_model = self.etatsListView.selectionModel()
        if selection_model.hasSelection():
            if not self.delete_états_selection():
                return
        else:
            état_item, ok = QInputDialog.getItem(self, "Supprimer un état", "Etat", self.etatsModel.list_of_str(), 0,
                                                 False)
            if ok:
                for item in reversed(self.etatsModel):
                    if item.named(état_item):
                        if self.deleteHypotheseByEtat(self.etatsListView.model()[item.row()].item):
                            del self.etatsModel[item.row()]
                            break
            else:
                return
        self.setModified()

    def ajouterHypothese(self, hypothesis=None):
        if self.etatsModel.rowCount() == 0:
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>Aucun état trouvé !</b>')
            msg.setInformativeText('Vous ne pouvez pas ajouter une hypothèse avant d\'ajouter les états')
            msg.setIcon(QMessageBox.Warning)
            msg.exec_()
            return
        if hypothesis:
            hypothesis_item = ObjectItem(hypothesis)
            if hypothesis_item not in self.hypothesesModel:
                self.hypothesesModel.appendRow(hypothesis_item)
            return
        selection_model = self.etatsListView.selectionModel()
        états = list()
        if selection_model.hasSelection():
            model_index_list = selection_model.selectedIndexes()
            for x in model_index_list:
                états.append(self.etatsModel.itemFromIndex(x).item)
            hypothèse = Hypothese(états)
            if ObjectItem(hypothèse) not in self.hypothesesModel:
                self.hypothesesModel.appendRow(ObjectItem(hypothèse))
            else:
                msg = QMessageBox(self)
                msg.setWindowTitle('Erreur')
                msg.setText("<b>L'hypothèse " + str(hypothèse) + " existe dans la liste !</b>")
                msg.setInformativeText('Vous ne pouvez pas ajouter une hypothèse plus d\'une fois')
                msg.setIcon(QMessageBox.Warning)
                msg.exec_()
                return
        else:
            hypotheses_dialog = HypotheseDialog(self)
            if hypotheses_dialog.exec_() == QDialog.Accepted:
                selections = hypotheses_dialog.hypothesesDialogListView.selectedIndexes()
                états = [self.etatsModel.itemFromIndex(x).item for x in selections]
                hypothèse = Hypothese(états)
                if ObjectItem(hypothèse) not in self.hypothesesModel:
                    self.hypothesesModel.appendRow(ObjectItem(hypothèse))
                else:
                    msg = QMessageBox(self)
                    msg.setWindowTitle('Erreur')
                    msg.setText("<b>L'hypothèse " + str(hypothèse) + " existe dans la liste !</b>")
                    msg.setInformativeText('Vous ne pouvez pas ajouter une hypothèse plus d\'une fois')
                    msg.setIcon(QMessageBox.Warning)
                    msg.exec_()
                    return
        self.setModified()

    def supprimerHypothese(self):
        if self.hypothesesModel.rowCount() == 0:
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>Aucune hypothèse trouvée !</b>')
            msg.setInformativeText('Vous devez ajouter une hypothèse d\'abord')
            msg.setIcon(QMessageBox.Warning)
            msg.exec_()
            return
        selection_model = self.hypothesesListView.selectionModel()
        if selection_model.hasSelection():
            model_index_list = selection_model.selectedIndexes()
            hypothèses_rows = [x.row() for x in model_index_list]
            for i in reversed(hypothèses_rows):
                self.deleteHypothese(i)
        else:
            hypothèse_name, ok = QInputDialog.getItem(self, "Supprimer une hypothèse", "Hypothèse",
                                                      self.hypothesesModel.list_of_str(), 0, False)
            if ok:
                for item in reversed(self.hypothesesModel):
                    if item.named(hypothèse_name):
                        self.deleteHypothese(item.row())
                        break
            else:
                return
        self.setModified()

    def ajouterAgent(self, name=None, enabled=None, reliability=None, hypotheses=None, masses=None):
        if self.hypothesesModel.rowCount() == 0:
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>Aucune hypothèse trouvée !</b>')
            msg.setInformativeText('Vous ne pouvez pas ajouter un agent avant d\'ajouter les hypothèses')
            msg.setIcon(QMessageBox.Warning)
            msg.exec_()
            return
        agent_dialog = AgentDialog(self)
        if hypotheses:
            agent_dialog.nomLineEdit.setText(name)
            agent_dialog.activeCheckBox.setChecked(enabled)
            agent_dialog.fiabiliteSpinBox.setValue(reliability)
            agent = Agent(name, reliability)
            agent.disable(not enabled)
            for hypothesis, mass in zip(hypotheses, masses):
                agent.add_hypothese(hypothesis, mass)
                agent_dialog.model.appendRow([ObjectItem(hypothesis), ObjectItem(mass)])
        if agent_dialog.exec_() == QDialog.Accepted:
            nom = agent_dialog.nomLineEdit.text()
            fiabilité = agent_dialog.fiabiliteSpinBox.value()
            activé = agent_dialog.activeCheckBox.isChecked()
            agent = Agent(nom, fiabilité)
            agent.disable(not activé)
            agent_item = ObjectItem(agent)
            if agent_item not in self.agentsModel:
                self.agentsModel.appendRow([agent_item, ObjectItem(fiabilité),
                                            ObjectItem('Désactivé' if agent.disabled else 'Activé')])
                for i in range(agent_dialog.model.rowCount()):
                    hypothèse_item = agent_dialog.model.item(i, 0)
                    masse_item = agent_dialog.model.item(i, 1)
                    agent.add_hypothese(hypothèse_item.item, masse_item.item)
                    agent_item.appendRow([ObjectItem(hypothèse_item.item), ObjectItem(masse_item.item)])
            else:
                msg = QMessageBox(self)
                msg.setWindowTitle('Erreur')
                msg.setText("<b>L'agent '" + str(agent) + "' existe dans la liste !</b>")
                msg.setInformativeText('Vous ne pouvez pas ajouter un agent plus d\'une fois')
                msg.setIcon(QMessageBox.Warning)
                msg.exec_()
                return
        else:
            return
        self.setModified()

    def supprimerAgent(self):
        if self.agentsModel.rowCount() == 0:
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>Aucun agent trouvé</b>')
            msg.setInformativeText('Vous devez ajouter un agent d\'abord')
            msg.setIcon(QMessageBox.Warning)
            msg.exec_()
            return
        selection_model = self.agentsTreeView.selectionModel()
        if selection_model.hasSelection():
            model_index_list = selection_model.selectedIndexes()
            agents_rows = [x.row() for x in model_index_list]
            for i in reversed(agents_rows):
                del self.agentsModel[i]
        else:
            agent_name, ok = QInputDialog.getItem(self, "Supprimer un agent", "Agent", self.agentsModel.list_of_str(),
                                                  0, False)
            if ok:
                for item in reversed(self.agentsModel):
                    if item.named(agent_name):
                        del self.agentsModel[item.row()]
                        break
            else:
                return
        self.setModified()

    def modifierAgent(self):
        if self.agentsModel.rowCount() == 0:
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>Aucun agent trouvé</b>')
            msg.setInformativeText('Vous devez ajouter un agent d\'abord')
            msg.setIcon(QMessageBox.Warning)
            msg.exec_()
            return
        selection_model = self.agentsTreeView.selectionModel()
        if selection_model.hasSelection():
            model_index_list = selection_model.selectedIndexes()
            # Edit the first agent in the selection
            if not self.editAgent(model_index_list[0]):
                return
        else:
            agent_name, ok = QInputDialog.getItem(self, "Modifier un agent", "Agent", self.agentsModel.list_of_str(),
                                                  0, False)
            if ok:
                for item in self.agentsModel:
                    if item.named(agent_name):
                        if not self.editAgent(item.index()):
                            return
                        else:
                            break
            else:
                return
        self.setModified()

    def calculer(self):
        agents_available = False
        for agent_item in self.agentsModel:
            if not agent_item.item.disabled:
                agents_available = True
                break
        if not agents_available:
            msg = QMessageBox(self)
            msg.setWindowTitle('Pas d\'agents')
            msg.setText('<b>Aucun agent trouvé</b>')
            msg.setInformativeText('Il faut au moins un agent <em>activé</em> pour le calcul')
            msg.setIcon(QMessageBox.Warning)
            msg.exec_()
            return
        if self.edited:
            msg = QMessageBox(self)
            msg.setWindowTitle('Changements non sauvegardés')
            msg.setText('<b>Vous n\'avez pas sauvegardé vos changements</b>')
            msg.setInformativeText('Voulez vous sauvegarder vos changements maintenant ?')
            msg.setIcon(QMessageBox.Warning)
            msg.setStandardButtons(QMessageBox.Yes | QMessageBox.No)
            ok = msg.exec_()
            if ok == QMessageBox.No:
                return
            if not self.enregistrer():
                return
        try:
            java_cmd = 'javaw' if sys.platform.startswith('win') else 'java';
            args = [java_cmd, '-cp',  dirname(realpath(__file__)), self.executable, self.input, self.output]
            wait_dialog = WaitDialog(self)
            launcher = ProgramLauncher(self, args, wait_dialog)
            wait_dialog.process = launcher
            if wait_dialog.exec_() == QDialog.Accepted:
                if launcher.return_code > 0:
                    raise OSError
                self.afficher(self.output)
        except OSError:
            QMessageBox.critical(self, 'Erreur', '<b>Une erreur est survenu dans le programme de calcul</b>')
            return

    def afficher(self, path=None):
        opened_from_menu = not path
        if not path:
            path = QFileDialog.getOpenFileName(self, 'Ouvrir', self.last_path, 'Résultats (*.dsto.xml)')
            if not path:
                return
        try:
            tree = ElementTree(file=path)
        except OSError:  # Can't open the file
            QMessageBox.critical(self, 'Erreur', '<b>Impossible d\'ouvrir le fichier</b><br>'+path)
            return
        except ParseError:  # XML contains syntax errors
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>Document invalide !</b>')
            msg.setInformativeText('Le document contient des erreurs')
            msg.setIcon(QMessageBox.Critical)
            msg.exec_()
            return
        except SyntaxError:
            QMessageBox.critical(self, 'Erreur', '<b>Le fichier est invalide</b>')
            return
        try:
            schema = XMLSchema(file=join(dirname(realpath(__file__)), 'validation_output.xsd'))
            if not schema(tree):
                msg = QMessageBox(self)
                msg.setWindowTitle('Erreur')
                msg.setText('<b>Document invalide !</b>')
                msg.setInformativeText('Le fichier contient des erreurs')
                msg.setIcon(QMessageBox.Critical)
                msg.exec_()
                return
        except NameError:
            pass
        i = 0
        try:
            results_dialog = ResultsDialog(self)
            results_dialog.titleLabel.setText('<b>'+tree.find('Title').text+'</b>')
            description = tree.find('Description').text or ""
            results_dialog.descriptionLabel.setText('<i>'+description.replace('\n', '<br>')+'</i>')
            results_dialog.methodLabel.setText('Méthode de fusion : '+tree.find('Method').text)
            results_dialog.decisionMethodLabel.setText('Méthode de décision : '+tree.find('Decision').text)
            états = []
            for element in tree.iter('Etat'):
                état = Etat(element.attrib['title'])
                état.order = int(re.search(r'\d+', element.attrib['id']).group())
                états.append(état)
            hypotheses_elements = tree.findall('Hypotheses/Hypothese')
            results_dialog.resultsTableWidget.setRowCount(len(hypotheses_elements))
            for hypothèse_element in hypotheses_elements:
                mass = round(float(hypothèse_element.attrib['mass']), self.round_digits)
                bel = round(float(hypothèse_element.find('Bel').text), self.round_digits)
                pl = round(float(hypothèse_element.find('Pl').text), self.round_digits)
                idf = hypothèse_element.attrib['id']
                hypothèse_états = [état for état in états if état.id() in idf.split('-')]
                hypothèse = Hypothese(hypothèse_états)
                results_dialog.resultsTableWidget.setItem(i, 0, QTableWidgetItem(str(hypothèse)))
                results_dialog.resultsTableWidget.setItem(i, 1, QTableWidgetItem(str(mass)))
                results_dialog.resultsTableWidget.setItem(i, 2, QTableWidgetItem(str(bel)))
                results_dialog.resultsTableWidget.setItem(i, 3, QTableWidgetItem(str(pl)))
                if hypothèse_element.attrib['id'] == tree.find('ResultatDecision').text:
                    results_dialog.decisionLabel.setText('Décision : '+str(hypothèse))
                i += 1
        except (ValueError, KeyError, AttributeError):
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>Document invalide !</b>')
            msg.setInformativeText('Le fichier contient des erreurs')
            msg.setIcon(QMessageBox.Critical)
            msg.exec_()
            return
        self.last_path = dirname(self.output)
        if opened_from_menu:
            results_dialog.agentButton.setEnabled(False)
        results_dialog.exec_()

    def editAgent(self, selectedIndex: QModelIndex):
        agent_dialog = AgentDialog(self)
        # Extract the agent object
        agent = self.agentsModel[selectedIndex.row()].item
        # Setting up the dialog
        agent_dialog.setWindowTitle("Modifier l'agent")
        agent_dialog.nomLineEdit.setText(agent.name)
        agent_dialog.fiabiliteSpinBox.setValue(agent.reliability)
        agent_dialog.activeCheckBox.setChecked(not agent.disabled)
        for hmw in agent:
            agent_dialog.model.appendRow([ObjectItem(hmw[0]), ObjectItem(hmw[1])])
        # Show the dialog
        if agent_dialog.exec_() == QDialog.Accepted:
            # Edit the object
            agent.name = agent_dialog.nomLineEdit.text()
            agent.reliability = agent_dialog.fiabiliteSpinBox.value()
            agent.disable(not agent_dialog.activeCheckBox.isChecked())
            # Edit the model
            was_expanded = self.agentsTreeView.isExpanded(selectedIndex)
            self.agentsTreeView.collapse(selectedIndex)  # Program will crash if the edited item is expanded
            self.agentsModel.setItem(selectedIndex.row(), 0, ObjectItem(agent))
            self.agentsModel.setItem(selectedIndex.row(), 1, ObjectItem(agent.reliability))
            self.agentsModel.setItem(selectedIndex.row(), 2, ObjectItem('Désactivé' if agent.disabled else 'Activé'))
            if was_expanded:
                self.agentsTreeView.expand(selectedIndex)
            # Delete the old hypotheses
            agent.clear_hypotheses()
            self.agentsModel[selectedIndex.row()].removeRows(0, self.agentsModel[selectedIndex.row()].rowCount())
            for i in range(agent_dialog.model.rowCount()):  # Insert the new ones
                hypothèse_item = agent_dialog.model.item(i, 0)
                masse_item = agent_dialog.model.item(i, 1)
                agent.add_hypothese(hypothèse_item.item, masse_item.item)
            for hmw in agent:
                self.agentsModel[selectedIndex.row()].appendRow([ObjectItem(hmw[0]), ObjectItem(hmw[1])])
            return True
        else:
            return False

    def delete_états_selection(self):
        selection_model = self.etatsListView.selectionModel()
        model_index_list = selection_model.selectedIndexes()
        états_rows = [x.row() for x in reversed(model_index_list)]
        états_deleted = False
        for i in états_rows:
            if not self.deleteHypotheseByEtat(self.etatsListView.model()[i].item):
                continue
            del self.etatsListView.model()[i]
            états_deleted = True
        return états_deleted

    def deleteHypotheseByEtat(self, état: Etat):
        hypothèsesIndices = []
        for hypothèse_item in reversed(self.hypothesesModel):
            if état in hypothèse_item.item:
                hypothèsesIndices.append(hypothèse_item.row())
        if hypothèsesIndices:
            msg = QMessageBox(self)
            msg.setWindowTitle('Avertissement')
            msg.setText('<b>Les hypothèses suivantes seront supprimées</b><br>'+'<br>'.join([self.hypothesesModel[i].text() for i in hypothèsesIndices]))
            msg.setInformativeText('Voulez vous continuer ?')
            msg.setStandardButtons(QMessageBox.Yes | QMessageBox.No)
            msg.setIcon(QMessageBox.Warning)
            ok = msg.exec_()
            if ok == QMessageBox.Yes:
                for i in hypothèsesIndices:
                    if not self.deleteHypothese(i):
                        return False
                return True
            return False
        return True

    def deleteHypothese(self, rowIndex: int):
        hypothèse = self.hypothesesModel[rowIndex].item
        agentsIndices = []
        for agent_item in self.agentsModel:
            if hypothèse in agent_item.item:
                agentsIndices.append(agent_item.row())
        if agentsIndices:
            msg = QMessageBox(self)
            msg.setWindowTitle('Avertissement')
            msg.setText('<b>Les agents suivants seront supprimées</b><br>'+'<br>'.join([self.agentsModel[i].text() for i in agentsIndices]))
            msg.setInformativeText('Voulez vous continuer ?')
            msg.setStandardButtons(QMessageBox.Yes | QMessageBox.No)
            msg.setIcon(QMessageBox.Warning)
            ok = msg.exec_()
            if ok == QMessageBox.Yes:
                for agentIndex in agentsIndices:
                    del self.agentsModel[agentIndex]
            else:
                return False
        del self.hypothesesModel[rowIndex]
        return True

    def closeEvent(self, close_event: QCloseEvent):  # Reimplementing the window close event
        if self.edited:
            msg = QMessageBox(self)
            msg.setWindowTitle('Changements non sauvegardés')
            msg.setText('<b>Vous n\'avez pas sauvegardé vos changements</b>')
            msg.setInformativeText('Voulez vous sauvegarder les changements avant de quitter ?')
            msg.setIcon(QMessageBox.Warning)
            msg.setStandardButtons(QMessageBox.Save | QMessageBox.Discard | QMessageBox.Cancel)
            ok = msg.exec_()
            if ok == QMessageBox.Save:
                if self.enregistrer():
                    close_event.accept()
                else:
                    close_event.ignore()
            elif ok == QMessageBox.Discard:
                close_event.accept()
            else:
                close_event.ignore()

    def setModified(self):
        self.edited = True
        self.setWindowTitle(app_name + ' ' + _version + ' - ' + self.title + '*')

    def setUnmodified(self):
        self.edited = False
        self.setWindowTitle(app_name + ' ' + _version + ' - ' + self.title)


class DescriptionDialog(QDialog, Ui_descriptionDialog):
    def __init__(self, parent: MainWindow, text: str):
        super(DescriptionDialog, self).__init__(parent)
        self.setupUi(self)
        self.descriptionPlainTextEdit.setPlainText(text)

    def get_description(self):
        return self.descriptionPlainTextEdit.toPlainText()


class HypotheseDialog(QDialog, Ui_hypothesesDialog):
    def __init__(self, parent: MainWindow):
        super(HypotheseDialog, self).__init__(parent)
        self.setupUi(self)
        # Set the model of the dialog list view to the parent's etats list view model
        self.hypothesesDialogListView.setModel(parent.etatsModel)


class AgentDialog(QDialog, Ui_agentDialog):
    def __init__(self, parent: MainWindow):
        super(AgentDialog, self).__init__(parent)
        self.setupUi(self)
        # Set the model for the hypotheses/masses table view
        self.model = ItemModel(self)
        self.hypothesesTableView.setModel(self.model)
        # Link the spinbox with the slider
        self.connect(self.fiabiliteSlider, SIGNAL("valueChanged(int)"),
                     lambda value: self.fiabiliteSpinBox.setValue(value / 100))
        self.connect(self.fiabiliteSpinBox, SIGNAL("valueChanged(double)"),
                     lambda value: self.fiabiliteSlider.setValue(value * 100))
        # Link the buttons
        self.connect(self.ajouterMasseButton, SIGNAL("clicked()"), self.ajouterHypothese)
        self.connect(self.supprimerMasseButton, SIGNAL("clicked()"), self.supprimerHypothese)
        # Set the table
        self.model.setHorizontalHeaderLabels(["Hypothèse", "Masse"])
        self.hypothesesTableView.horizontalHeader().setResizeMode(QHeaderView.ResizeToContents)

    def accept(self):  # Reimplementing the dialog accept method
        if self.nomLineEdit.text() == '':
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>L\'agent ne peut pas être sans nom !</b>')
            msg.setInformativeText('Vous devez donner un nom à cet agent')
            msg.setIcon(QMessageBox.Warning)
            msg.exec_()
            return
        s = 0
        for i in range(len(self.model)):
            s += self.model.item(i, 1).item
        s = round(s, self.parent().round_digits)
        if s > 1:
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>La somme de masses ne peut pas dépasser 1.0 !</b>')
            msg.setInformativeText('La somme de vos masses est '+str(s))
            msg.setIcon(QMessageBox.Warning)
            msg.exec_()
            return
        elif s < 1:
            omega = Hypothese([x.item for x in self.parent().etatsModel])
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>La somme des masses est inférieur à 1.0 !</b>')
            msg.setInformativeText(str(round(1-s, self.parent().round_digits)) + ' non attribué, voulez vous l\'ajouter à ' + str(omega) + ' ?')
            msg.setIcon(QMessageBox.Warning)
            msg.setStandardButtons(QMessageBox.Yes | QMessageBox.No)
            ok = msg.exec_()
            if ok == QMessageBox.No:
                return
        super(AgentDialog, self).accept()

    def ajouterHypothese(self):
        masse_dialog = MasseDialog(self)
        if masse_dialog.exec_() == QDialog.Accepted:
            hypothèse_index = masse_dialog.hypotheseComboBox.currentIndex()
            masse = masse_dialog.masseSpinBox.value()
            hypothèse = self.parent().hypothesesModel[hypothèse_index].item
            hypothèse_item = ObjectItem(hypothèse)
            if hypothèse_item not in self.model:
                self.model.appendRow([hypothèse_item, ObjectItem(masse)])
                self.hypothesesTableView.resizeColumnsToContents()
            else:
                for i in range(len(self.model)):
                    if self.model[i].named(str(hypothèse)):
                        self.model.setItem(i, 1, ObjectItem(masse))
                        break

    def supprimerHypothese(self):
        selection_model = self.hypothesesTableView.selectionModel()
        if selection_model.hasSelection():
            model_index_list = selection_model.selectedRows()
            hypothèses_rows = [x.row() for x in model_index_list]
            for i in reversed(hypothèses_rows):
                del self.model[i]
        else:
            hypothèse_name, ok = QInputDialog.getItem(self, "Supprimer une hypothèse", "Hypothèse",
                                                      self.model.list_of_str(), 0, False)
            if ok:
                for item in reversed(self.model):
                    if item.named(hypothèse_name):
                        del self.model[item.row()]
                        break


class MasseDialog(QDialog, Ui_masseDialog):
    def __init__(self, parent: AgentDialog):
        super(MasseDialog, self).__init__(parent)
        self.setupUi(self)
        # Set the model
        self.model = parent.parent().hypothesesModel  # The model of the hypotheses from the main window
        self.hypotheseComboBox.setModel(self.model)
        # Link the mass slider wuth the spin box
        # Mass
        self.connect(self.masseSlider, SIGNAL("valueChanged(int)"),
                     lambda value: self.masseSpinBox.setValue(value / 100))
        self.connect(self.masseSpinBox, SIGNAL("valueChanged(double)"),
                     lambda value: self.masseSlider.setValue(value * 100))
        self.connect(self.masseSpinBox, SIGNAL("valueChanged(double)"), self.remaining_mass)
        # Link the hypotheses combo box
        self.connect(self.hypotheseComboBox, SIGNAL('currentIndexChanged(const QString&)'), self.mass_change)
        if len(self.parent().model) == 0:
            return
        # Set the right values for the selected option for the first time
        selection_model = self.parent().hypothesesTableView.selectionModel()
        if len(selection_model.selectedRows()) > 0:
            model_index = selection_model.selectedRows()[0]
            self.hypotheseComboBox.setCurrentIndex(self.hypothese_index(model_index.row()))
            # Required when the first hypothesis in the table is the first hypothesis in the combo box
            self.mass_change(self.hypotheseComboBox.currentText())
        else:
            self.hypotheseComboBox.setCurrentIndex(self.hypothese_index(0))
            # Required when the first hypothesis in the table is the first hypothesis in the combo box
            self.mass_change(self.hypotheseComboBox.currentText())

    def mass_change(self, current_text: str):
        for i in range(self.parent().model.rowCount()):
            if self.parent().model[i].named(current_text):
                mass = self.parent().model.item(i, 1).item
                self.masseSpinBox.setValue(mass)
                return
        self.masseSpinBox.setValue(0)  # For non affected hypotheses

    def hypothese_index(self, agent_hypothese_index: int):
        agent_hypotheses_model = self.parent().model
        hypothèse = agent_hypotheses_model[agent_hypothese_index].item
        for i in range(len(self.model)):
            if self.model[i].named(str(hypothèse)):
                return i
        return 0

    def remaining_mass(self):
        agent_hypotheses_model = self.parent().model
        s = 0
        for i in range(len(agent_hypotheses_model)):
            if str(agent_hypotheses_model[i].item) == self.hypotheseComboBox.currentText():
                continue
            s += agent_hypotheses_model.item(i, 1).item
        s += self.masseSpinBox.value()
        self.remainingMassLabel.setText('Masse restante : '+str(round(1-s, self.parent().parent().round_digits)))


class ResultsDialog(QDialog, Ui_resultsDialog):
    def __init__(self, parent: MainWindow):
        super(ResultsDialog, self).__init__(parent)
        self.setupUi(self)
        self.resultsTableWidget.horizontalHeader().setStretchLastSection(True)
        self.connect(self.agentButton, SIGNAL('clicked()'), self.add_agent)
        self.connect(self.rechercheLineEdit, SIGNAL('textChanged(const QString&)'), self.search)

    def search(self, text):
        items = self.resultsTableWidget.findItems(text, Qt.MatchContains)
        self.resultsTableWidget.clearSelection()
        if not text:
            return
        for table_item in items:
            table_item.setSelected(True)

    def add_agent(self):
        hypotheses = []
        masses = []
        for i in range(self.resultsTableWidget.rowCount()):
            mass = float(self.resultsTableWidget.item(i, 1).text())
            if mass == 0:
                continue
            hypothesis_str = self.resultsTableWidget.item(i, 0).text()
            états = []
            for état_str in hypothesis_str[1:-1].split(', '):
                état_str = état_str[1:-1]
                état = Etat(état_str)
                for état_item in self.parent().etatsModel:
                    if str(état) == str(état_item.item):
                        état.order = état_item.item.order
                        break
                états.append(état)
            hypothèse = Hypothese(états)
            self.parent().ajouterHypothese(hypothèse)
            hypotheses.append(hypothèse)
            masses.append(mass)
        self.parent().ajouterAgent(name='', enabled=True, reliability=1, hypotheses=hypotheses, masses=masses)


class WaitDialog(QDialog, Ui_waitDialog):
    def __init__(self, parent: MainWindow):
        super(WaitDialog, self).__init__(parent)
        self.setupUi(self)
        self.process = None
        self.iconLabel.setMovie(QMovie(join(dirname(realpath(__file__)), join('UI', 'loader.gif'))))

    def exec_(self):
        self.connect(self, SIGNAL('rejected()'), self.process.exit)
        self.iconLabel.movie().start()
        self.process.start()
        return super(WaitDialog, self).exec_()


class ProgramLauncher(QThread):
    def __init__(self, parent: MainWindow, args: list, wait_dialog: WaitDialog):
        super(ProgramLauncher, self).__init__(parent)
        self.process = None
        self.args = args
        self.dialog = wait_dialog
        self.return_code = 0
        self.stderr_data = ''
        self.connect(self, SIGNAL('finished()'), self.dialog.accept)

    def run(self):
        with subprocess.Popen(self.args, cwd=self.args[2], stderr=subprocess.PIPE, universal_newlines=True) as self.process:
            self.stderr_data = self.process.communicate()[1]
            self.return_code = self.process.returncode
            if self.process.returncode > 0:
                self.exit(self.process.returncode)

    def exit(self, return_code=0):
        try:
            self.process.terminate()
        except ProcessLookupError:
            pass
        super(ProgramLauncher, self).exit(return_code)


class ObjectItem(QStandardItem):
    def __init__(self, obj=None):
        super(ObjectItem, self).__init__(str(obj) if obj is not None else None)
        self.item = obj
        self.setEditable(False)

    def __eq__(self, other) -> bool:
        if isinstance(other, ObjectItem):
            return self.item == other.item
        else:
            return False

    def __str__(self) -> str:
        return self.text()

    def named(self, name: str) -> bool:
        return name == str(self.item)


class ItemModel(QStandardItemModel):
    def __init__(self, parent=None):
        super(ItemModel, self).__init__(parent)

    def __contains__(self, item: QStandardItem) -> bool:
        for i in range(self.rowCount()):
            current_item = self.item(i)
            if current_item == item:
                return True
        return False

    def __iter__(self) -> iter:
        l = []
        for i in range(self.rowCount()):
            current_item = self.item(i)
            l.append(current_item)
        return iter(l)

    def __getitem__(self, item_index) -> ObjectItem:
        return self.item(item_index)

    def __delitem__(self, item_index):
        self.removeRow(item_index)

    def __len__(self) -> int:
        return self.rowCount()

    def list_of_str(self) -> list:
        l = []
        for x in self.__iter__():
            l.append(str(x))
        return l


def exception_handler(type, value, tb):  # Show the error to the user instead of printing it in the STDERR
    msg = QMessageBox()
    msg.setWindowTitle('Erreur fatale')
    msg.setText('<b>Une erreur inconnue est survenue</b>')
    msg.setInformativeText('Veuillez contacter le développeur')
    msg.setDetailedText(str(value)+'\n'+'\n'.join(traceback.format_tb(tb)))
    msg.setIcon(QMessageBox.Critical)
    msg.exec_()

if __name__ == '__main__':
    sys.excepthook = exception_handler  # Change the default exception handler
    app = QApplication(sys.argv)
    # Translate standard buttons
    translator = QTranslator()
    if translator.load('qt_fr'):
        app.installTranslator(translator)
    else:
        msg = QMessageBox()
        msg.setWindowTitle('Avertissement')
        msg.setText('<b>Le fichier qt_fr.qm est manquant !</b>')
        msg.setIcon(QMessageBox.Warning)
        msg.exec_()

    window = MainWindow()
    window.show()
    # Load Java for the first time
    java_cmd = 'javaw' if sys.platform.startswith('win') else 'java'
    with subprocess.Popen([java_cmd, '-cp',  dirname(realpath(__file__)), window.executable], stderr=subprocess.DEVNULL):
        pass
    sys.exit(app.exec_())
