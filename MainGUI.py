#!/usr/bin/env python3
import sys
import re
import subprocess
from os.path import join, splitext

try:
    from PyQt4.QtGui import QApplication, QMainWindow, QActionGroup, QDialog, QStandardItem, QStandardItemModel, \
        QInputDialog, QHeaderView, QLineEdit, QMessageBox, QFileDialog, QCloseEvent, QTableWidgetItem, QMovie
    from PyQt4.QtCore import SIGNAL, QModelIndex, Qt, QThread, QMimeData
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


__author__ = 'hamza'
app_name = 'Calculateur de Dempster-Shafer'


class MainWindow(QMainWindow, Ui_MainWindow):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.setupUi(self)
        self.untitled = "Sans titre"
        self.title = self.untitled
        self.description = ""
        self.agentsModelHeaderLabels = ["Agent/Hypothèse", "Fiabilité/Masse", "Activé/Affaiblissement"]
        self.setUnmodified()
        self.executable = 'Main'  # Command of the engine executable
        self.input = 'input.dsti.xml'  # Input file for calculation
        self.output = 'output.dsto.xml'  # Output file of calculation
        self.round_digits = 3

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
        self.connect(self.actionEnregistrer, SIGNAL("triggered(bool)"), self.enregistrer)
        self.connect(self.actionModifierAgent, SIGNAL("triggered(bool)"), self.modifierAgent)
        self.connect(self.actionOuvrir, SIGNAL("triggered(bool)"), self.ouvrir)
        self.connect(self.actionNouveau, SIGNAL("triggered(bool)"), self.nouveau)
        self.connect(self.actionCalculer, SIGNAL("triggered(bool)"), self.calculer)
        self.connect(self.action_group, SIGNAL("selected(QAction *)"), self.setModified)
        self.connect(self.actionOuvrir_des_resultats, SIGNAL("triggered(bool)"), self.afficher)
        self.connect(self.action_group2, SIGNAL("selected(QAction *)"), self.setModified)

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
        return True

    def enregistrer(self, path=''):
        if isinstance(path, str) and path != '':
            file_path = path
        else:
            file_path = QFileDialog.getSaveFileName(self, 'Enregistrer', '', 'Données (*.dsti.xml)')
        if not file_path:
            return False
        try:  # Using xml.etree.ElementTree
            root = Element('DSTI',
                           {'xmlns:xsi': 'http://www.w3.org/2001/XMLSchema-instance',
                            'xsi:noNamespaceSchemaLocation': 'validation.xsd'},
                           )
        except ValueError:  # Using lxml.etree
            root = Element('DSTI',
                           {'{http://www.w3.org/2001/XMLSchema-instance}noNamespaceSchemaLocation': 'validation.xsd'},
                            nsmap = {'xsi': 'http://www.w3.org/2001/XMLSchema-instance'}
                           )
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
                agent.append(Element('Knowledge', {'id': hmw[0].id(), 'mass': str(hmw[1]), 'weaking': str(hmw[2])}))
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
            return True
        except OSError as e:
            QMessageBox.critical(self, 'Erreur', '<b>Impossible de sauvegarder le fichier '+e.filename+'</b>')
            return False

    def ouvrir(self):
        file_path = QFileDialog.getOpenFileName(self, 'Ouvrir', '', 'Données (*.dsti.xml)')
        if not file_path:
            return
        # Create the document
        try:
            tree = ElementTree(file=file_path)
        except OSError as e:  # Can't open the file
            QMessageBox.critical(self, 'Erreur', '<b>Impossible d\'ouvrir le fichier '+e.filename+'</b>')
            return
        except ParseError:  # XML contains syntax errors
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>Document invalide !</b>')
            msg.setInformativeText('Le document contient des erreurs')
            msg.setIcon(QMessageBox.Critical)
            msg.exec_()
            return
        try:
            schema = XMLSchema(file='validation.xsd')
            if not schema(tree):
                msg = QMessageBox(self)
                msg.setWindowTitle('Erreur')
                msg.setText('<b>Document invalide !</b>')
                msg.setInformativeText('Vérifier que vous avez choisie le bon fichier')
                msg.setIcon(QMessageBox.Critical)
                msg.exec_()
                return
        except NameError:  # lxml library not found, can't validate the document
            msg = QMessageBox(self)
            msg.setWindowTitle('Erreur')
            msg.setText('<b>La bibliothèque <i>lxml</i> est introuvable</b>')
            msg.setInformativeText('Le programme ne peut pas assurer la validité de du fichier choisie')
            msg.setIcon(QMessageBox.Warning)
            msg.exec_()
        if not self.nouveau():
            return
        try:
            # The title and the description
            self.title = tree.find('Title').text
            self.setWindowTitle(app_name + (' - ' + self.title if self.title != '' else ''))
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
                            weaking = float(hypothèse_element.attrib['weaking'])
                            if hypothèse_item.item not in agent:  # Check if the hypothesis isn't doubled
                                agent.add_hypothese(hypothèse_item.item, mass, weaking)
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
                    agent_item.appendRow([ObjectItem(hmw[0]), ObjectItem(hmw[1]), ObjectItem(hmw[2])])
                if agent_item not in self.agentsModel:
                    self.agentsModel.appendRow([agent_item, ObjectItem(agent.reliability),
                                                ObjectItem('Désactivé' if agent.disabled else 'Activé')])
                else:
                    raise ValueError
            HelperClasses.Agent.idf = highest_id+1
            self.input = file_path
            self.output = splitext(splitext(self.input)[0])[0] + '.dsto.xml'
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
            self.delete_états_selection()
        else:
            état_item, ok = QInputDialog.getItem(self, "Supprimer un état", "Etat", self.etatsModel.list_of_str(), 0,
                                                 False)
            if ok:
                for item in reversed(self.etatsModel):
                    if item.named(état_item):
                        self.deleteHypotheseByEtat(self.etatsListView.model()[item.row()].item)
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
                agent.add_hypothese(hypothesis, mass, 0)
                agent_dialog.model.appendRow([ObjectItem(hypothesis), ObjectItem(mass), ObjectItem(0)])
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
                    affaiblissement_item = agent_dialog.model.item(i, 2)
                    agent.add_hypothese(hypothèse_item.item, masse_item.item, affaiblissement_item.item)
                    agent_item.appendRow([ObjectItem(hypothèse_item.item), ObjectItem(masse_item.item),
                                          ObjectItem(affaiblissement_item.item)])
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
            msg.setInformativeText('Vos changements seront automatiquement sauvegardés.\nVoulez vous continuer ?')
            msg.setIcon(QMessageBox.Warning)
            msg.setStandardButtons(QMessageBox.Yes | QMessageBox.No)
            ok = msg.exec_()
            if ok == QMessageBox.No:
                return
            self.enregistrer(self.input)
        try:
            args = ['java', '-cp',  '.', self.executable, self.input, self.output]
            wait_dialog = WaitDialog(self)
            launcher = ProgramLauncher(self, args, wait_dialog)
            wait_dialog.process = launcher
            if wait_dialog.exec_() == QDialog.Accepted:
                self.afficher(self.output)
        except OSError as e:
            QMessageBox.critical(self, 'Erreur', 'Impossible de lancer le programme de calcul\n'+e.filename)
            return

    def afficher(self, path=None):
        if not path:
            path = QFileDialog.getOpenFileName(self, 'Ouvrir', '', 'Résultats (*.dsto.xml)')
            if not path:
                return
        try:
            tree = ElementTree(file=path)
        except OSError as e:  # Can't open the file
            QMessageBox.critical(self, 'Erreur', '<b>Impossible d\'ouvrir le fichier '+e.filename+'</b>')
            return
        try:
            schema = XMLSchema(file='validation_output.xsd')
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
            results_dialog.descriptionLabel.setText('<i>'+tree.find('Description').text.replace('\n', '<br>')+'</i>')
            results_dialog.methodLabel.setText('Méthode de fusion : '+tree.find('Method').text)
            results_dialog.decisionMethodLabel.setText('Méthode de décision : '+tree.find('Decision').text)
            états = []
            for element in tree.iter('Etat'):
                état = Etat(element.attrib['title'])
                état.order = int(re.search(r'\d+', element.attrib['id']).group())
                états.append(état)
            hypotheses_elements = tree.findall('Hypotheses/Hypothese')
            results_dialog.resultsTableWidget.setRowCount(len(hypotheses_elements))
            results_dialog.resultsTableWidget.horizontalHeader().setStretchLastSection(True)
            results_dialog.resultsTableWidget.horizontalHeader().setResizeMode(QHeaderView.ResizeToContents)
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
            agent_dialog.model.appendRow([ObjectItem(hmw[0]), ObjectItem(hmw[1]), ObjectItem(hmw[2])])
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
                affaiblissement_item = agent_dialog.model.item(i, 2)
                agent.add_hypothese(hypothèse_item.item, masse_item.item, affaiblissement_item.item)
            for hmw in agent:
                self.agentsModel[selectedIndex.row()].appendRow([ObjectItem(hmw[0]), ObjectItem(hmw[1]), ObjectItem(hmw[2])])
            return True
        else:
            return False

    def delete_états_selection(self):
        selection_model = self.etatsListView.selectionModel()
        model_index_list = selection_model.selectedIndexes()
        for x in reversed(model_index_list):
            self.deleteHypotheseByEtat(self.etatsListView.model()[x.row()].item)
            del self.etatsListView.model()[x.row()]

    def deleteHypotheseByEtat(self, état: Etat):
        for hypothèse_item in reversed(self.hypothesesModel):
            if état in hypothèse_item.item:
                self.deleteHypothese(hypothèse_item.row())

    def deleteHypothese(self, rowIndex: int):
        hypothèse = self.hypothesesModel[rowIndex].item
        for agent_item in self.agentsModel:
            if hypothèse in agent_item.item:
                del self.agentsModel[agent_item.row()]
        del self.hypothesesModel[rowIndex]

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
        self.setWindowTitle(app_name + ' - ' + self.title + '*')

    def setUnmodified(self):
        self.edited = False
        self.setWindowTitle(app_name + ' - ' + self.title)


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
        self.model.setHorizontalHeaderLabels(["Hypothèse", "Masse", "Affaiblissement"])
        self.hypothesesTableView.horizontalHeader().setResizeMode(QHeaderView.ResizeToContents)
        # Disable the agent reliability if the used method is Dempster-Shafer
        if parent.action_group.checkedAction().data() == 'Dempster-Shafer':
            self.fiabiliteLabel.setEnabled(False)
            self.fiabiliteSlider.setEnabled(False)
            self.fiabiliteSpinBox.setEnabled(False)

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
        s = round(s, 5)
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
            msg.setInformativeText(str(round(1-s, 2)) + ' non attribué, voulez vous l\'ajouter à ' + str(omega) + ' ?')
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
            affaiblissement = masse_dialog.affaiblissementSpinBox.value()
            hypothèse = self.parent().hypothesesModel[hypothèse_index].item
            hypothèse_item = ObjectItem(hypothèse)
            if hypothèse_item not in self.model:
                self.model.appendRow([hypothèse_item, ObjectItem(masse), ObjectItem(affaiblissement)])
                self.hypothesesTableView.resizeColumnsToContents()
            else:
                for i in range(len(self.model)):
                    if self.model[i].named(str(hypothèse)):
                        self.model.setItem(i, 1, ObjectItem(masse))
                        self.model.setItem(i, 2, ObjectItem(affaiblissement))
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
        # Link the mass and the weaking sliders and the spin boxes
        # Mass
        self.connect(self.masseSlider, SIGNAL("valueChanged(int)"),
                     lambda value: self.masseSpinBox.setValue(value / 100))
        self.connect(self.masseSpinBox, SIGNAL("valueChanged(double)"),
                     lambda value: self.masseSlider.setValue(value * 100))
        self.connect(self.masseSpinBox, SIGNAL("valueChanged(double)"), self.remaining_mass)
        # Weaking
        self.connect(self.affaiblissementSlider, SIGNAL("valueChanged(int)"),
                     lambda value: self.affaiblissementSpinBox.setValue(value / 100))
        self.connect(self.affaiblissementSpinBox, SIGNAL("valueChanged(double)"),
                     lambda value: self.affaiblissementSlider.setValue(value * 100))
        # Link the hypotheses combo box
        self.connect(self.hypotheseComboBox, SIGNAL('currentIndexChanged(const QString&)'), self.mass_weaking_change)
        if len(self.parent().model) == 0:
            return
        # Set the right values for the selected option for the first time
        selection_model = self.parent().hypothesesTableView.selectionModel()
        if len(selection_model.selectedRows()) > 0:
            model_index = selection_model.selectedRows()[0]
            self.hypotheseComboBox.setCurrentIndex(self.hypothese_index(model_index.row()))
            # Required when the first hypothesis in the table is the first hypothesis in the combo box
            self.mass_weaking_change(self.hypotheseComboBox.currentText())
        else:
            self.hypotheseComboBox.setCurrentIndex(self.hypothese_index(0))
            # Required when the first hypothesis in the table is the first hypothesis in the combo box
            self.mass_weaking_change(self.hypotheseComboBox.currentText())

    def mass_weaking_change(self, current_text: str):
        for i in range(self.parent().model.rowCount()):
            if self.parent().model[i].named(current_text):
                mass = self.parent().model.item(i, 1).item
                weaking = self.parent().model.item(i, 2).item
                self.masseSpinBox.setValue(mass)
                self.affaiblissementSpinBox.setValue(weaking)
                return
        self.masseSpinBox.setValue(0)  # For non affected hypotheses
        self.affaiblissementSpinBox.setValue(0)

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
        self.remainingMassLabel.setText('Masse restante : '+str(round(1-s, 2)))


class ResultsDialog(QDialog, Ui_resultsDialog):
    def __init__(self, parent: MainWindow):
        super(ResultsDialog, self).__init__(parent)
        self.setupUi(self)
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
        self.iconLabel.setMovie(QMovie(join('UI', 'loader.gif')))

    def exec_(self):
        self.connect(self, SIGNAL('rejected()'), self.process.quit)
        self.iconLabel.movie().start()
        self.process.start()
        return super(WaitDialog, self).exec_()


class ProgramLauncher(QThread):
    def __init__(self, parent: MainWindow, args: list, wait_dialog: WaitDialog):
        super(ProgramLauncher, self).__init__(parent)
        self.process = None
        self.args = args
        self.dialog = wait_dialog
        self.connect(self, SIGNAL('finished()'), self.dialog.accept)

    def run(self):
        with subprocess.Popen(self.args) as self.process:
            self.process.wait()

    def quit(self):
        self.process.terminate()
        super(ProgramLauncher, self).quit()


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

if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())