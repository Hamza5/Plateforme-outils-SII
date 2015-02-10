from Hypothese import Hypothese
from Agent import Agent
from MainWindow import Ui_MainWindow
from HypotheseDialog import Ui_hypothesesDialog
from AgentDialog import Ui_agentDialog
from MasseDialog import Ui_masseDialog
from DescriptionDialog import Ui_descriptionDialog
from PyQt4.QtGui import QApplication, QMainWindow, QActionGroup, QDialog, QStandardItem, QStandardItemModel, QInputDialog, QHeaderView, QLineEdit, \
    QMessageBox
from PyQt4.QtCore import SIGNAL
import sys

__author__ = 'hamza'


class MainWindow(QMainWindow, Ui_MainWindow):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.setupUi(self)
        self.title = ""
        self.description = ""

        # Make methods actions mutually-exclusives :
        action_group = QActionGroup(self)
        action_group.addAction(self.actionDempster_Shafer)
        action_group.addAction(self.actionDubois_Prade)

        # Center the main window on the screen
        self.move(QApplication.desktop().availableGeometry().center() - self.rect().center())

        # Create the models
        self.etatsModel = ItemModel(self.etatsListView)
        self.hypothesesModel = ItemModel(self.hypothesesListView)
        self.agentsModel = ItemModel(self.agentsTreeView)
        self.agentsModel.setHorizontalHeaderLabels(["Agent/Hypothèse", "Fiabilité/Masse", "Affaiblissement"])

        # Assign the models
        self.etatsListView.setModel(self.etatsModel)
        self.hypothesesListView.setModel(self.hypothesesModel)
        self.agentsTreeView.setModel(self.agentsModel)
        self.agentsTreeView.header().setResizeMode(QHeaderView.ResizeToContents)

        # Connecting Signals to slots :
        self.connect(self.actionAjouterEtat ,SIGNAL("triggered(bool)"), self.ajouterEtat)
        self.connect(self.actionSupprimerEtat, SIGNAL("triggered(bool)"), self.suprimmerEtat)
        self.connect(self.actionAjouterHypothese, SIGNAL("triggered(bool)"), self.ajouterHypothese)
        self.connect(self.actionSupprimerHypothese, SIGNAL("triggered(bool)"), self.supprimerHypothese)
        self.connect(self.actionAjouterAgent, SIGNAL("triggered(bool)"), self.ajouterAgent)
        self.connect(self.actionSupprimerAgent, SIGNAL("triggered(bool)"), self.supprimerAgent)
        self.connect(self.actionTitre, SIGNAL("triggered(bool)"), self.attribuer_titre)
        self.connect(self.actionDescription, SIGNAL("triggered(bool)"), self.attribuer_description)
        self.connect(self.actionQuitter, SIGNAL("triggered(bool)"), self.close)

    def attribuer_titre(self):
        title, ok = QInputDialog.getText(self, "Titre", "Titre", QLineEdit.Normal, self.title)
        if ok:
            self.title = title

    def attribuer_description(self):
        description_dialog = DescriptionDialog(self, self.description)
        if description_dialog.exec_() == QDialog.Accepted:
            self.description = description_dialog.get_description()

    def ajouterEtat(self):
        état, ok = QInputDialog.getText(self, "Ajouter un état", "Etat")
        if ok and état:
            item = ObjectItem(état)
            if item not in self.etatsModel:
                self.etatsModel.appendRow(item)
            else:
                QMessageBox.warning(self, 'Etat existant', "L'état '"+état+"' existe dans la liste !")

    def suprimmerEtat(self):
        selection_model = self.etatsListView.selectionModel()
        if selection_model.hasSelection():
           self.deleteSelection(self.etatsListView)
        else:
            état_item, ok = QInputDialog.getItem(self, "Supprimer un état", "Etat", self.etatsModel.list_of_str(), 0, False)
            if ok:
                for item in reversed(self.etatsModel):
                    if item.named(état_item) :
                        self.deleteHypotheseByEtat(str(item))
                        del self.etatsModel[item.row()]
                        break

    def ajouterHypothese(self):
        selection_model = self.etatsListView.selectionModel()
        états = set()
        if selection_model.hasSelection():
            model_index_list = selection_model.selectedIndexes()
            for x in model_index_list:
                états.add(str(self.etatsModel.itemFromIndex(x)))
            hypothèse = Hypothese(états)
            if ObjectItem(hypothèse) not in self.hypothesesModel:
                self.hypothesesModel.appendRow(ObjectItem(hypothèse))
            else:
                QMessageBox.warning(self, 'Hypothèse existante', "L'hypothèse "+str(hypothèse)+" existe dans la liste !")
        else :
            hypotheses_dialog = HypotheseDialog(self)
            if hypotheses_dialog.exec_() == QDialog.Accepted:
                selections = hypotheses_dialog.hypothesesDialogListView.selectedIndexes()
                états = {str(self.etatsModel.itemFromIndex(x)) for x in selections}
                hypothèse = Hypothese(états)
                if ObjectItem(hypothèse) not in self.hypothesesModel:
                    self.hypothesesModel.appendRow(ObjectItem(hypothèse))
                else:
                    QMessageBox.warning(self, 'Hypothèse existante', "L'hypothèse "+str(hypothèse)+" existe dans la liste !")

    def supprimerHypothese(self):
        selection_model = self.hypothesesListView.selectionModel()
        if selection_model.hasSelection():
            model_index_list = selection_model.selectedIndexes()
            hypothèses_rows = [x.row() for x in model_index_list]
            for i in reversed(hypothèses_rows):
                self.deleteHypothese(i)
        else:
            hypothèse_name, ok = QInputDialog.getItem(self, "Supprimer une hypothèse", "Hypothèse", self.hypothesesModel.list_of_str(), 0, False)
            if ok:
                for item in reversed(self.hypothesesModel):
                    if item.named(hypothèse_name):
                        self.deleteHypothese(item.row())
                        break

    def ajouterAgent(self):
        agent_dialog = AgentDialog(self)
        if agent_dialog.exec_() == QDialog.Accepted:
            nom = agent_dialog.nomLineEdit.text()
            fiabilité = agent_dialog.fiabiliteSpinBox.value()
            agent = Agent(nom, fiabilité)
            agent_item = ObjectItem(agent)
            if agent_item not in self.agentsModel:
                self.agentsModel.appendRow([agent_item, ObjectItem(fiabilité)])
                for i in range(agent_dialog.model.rowCount()):
                    hypothèse_item = agent_dialog.model.item(i,0)
                    masse_item = agent_dialog.model.item(i,1)
                    affaiblissement_item = agent_dialog.model.item(i,2)
                    agent.add_hypothese(hypothèse_item.item, masse_item.item, affaiblissement_item.item)
                    agent_item.appendRow([ObjectItem(hypothèse_item.item), ObjectItem(masse_item.item), ObjectItem(affaiblissement_item.item)])
            else:
                QMessageBox.warning(self, 'Agent existant', "L'agent '"+str(agent)+"' existe dans la liste !")

    def supprimerAgent(self):
        selection_model = self.agentsTreeView.selectionModel()
        if selection_model.hasSelection():
            model_index_list = selection_model.selectedIndexes()
            agents_rows = [x.row() for x in model_index_list]
            for i in reversed(agents_rows):
                del self.agentsModel[i]
        else:
            agent_name, ok = QInputDialog.getItem(self, "Supprimer un agent", "Agent", self.agentsModel.list_of_str(), 0, False)
            if ok:
                for item in reversed(self.agentsModel):
                    if item.named(agent_name):
                        del self.agentsModel[item.row()]
                        break

    def deleteSelection(self, list_view):
        selection_model = list_view.selectionModel()
        model_index_list = selection_model.selectedIndexes()
        for x in reversed(model_index_list):
            self.deleteHypotheseByEtat(str(list_view.model()[x.row()]))
            del list_view.model()[x.row()]

    def deleteHypotheseByEtat(self, état : str):
        for hypothèse_item in reversed(self.hypothesesModel):
            if état in hypothèse_item.item:
                self.deleteHypothese(hypothèse_item.row())

    def deleteHypothese(self, rowIndex : int):
        hypothèse = self.hypothesesModel[rowIndex].item
        for agent_item in self.agentsModel:
            if hypothèse in agent_item.item:
                del self.agentsModel[agent_item.row()]
        del self.hypothesesModel[rowIndex]

class DescriptionDialog(QDialog, Ui_descriptionDialog):
    def __init__(self, parent : MainWindow, text : str):
        super(DescriptionDialog, self).__init__(parent)
        self.setupUi(self)
        self.descriptionPlainTextEdit.setPlainText(text)

    def get_description(self):
        return self.descriptionPlainTextEdit.toPlainText()

class HypotheseDialog(QDialog, Ui_hypothesesDialog):
    def __init__(self, parent : MainWindow):
        super(HypotheseDialog, self).__init__(parent)
        self.setupUi(self)
        # Set the model of the dialog list view to the parent's etats list view model
        self.hypothesesDialogListView.setModel(parent.etatsModel)


class AgentDialog(QDialog, Ui_agentDialog):
    def __init__(self, parent : MainWindow):
        super(AgentDialog, self).__init__(parent)
        self.setupUi(self)
        # Set the model for the hyopotheses/masses table view
        self.model = ItemModel(self)
        self.hypothesesTableView.setModel(self.model)
        # Link the spinbox with the slider
        self.connect(self.fiabiliteSlider, SIGNAL("valueChanged(int)"), lambda value: self.fiabiliteSpinBox.setValue(value/100))
        self.connect(self.fiabiliteSpinBox, SIGNAL("valueChanged(double)"), lambda value: self.fiabiliteSlider.setValue(value*100))
        # Link the buttons
        self.connect(self.ajouterMasseButton, SIGNAL("clicked()"), self.ajouterHypothese)
        self.connect(self.supprimerMasseButton, SIGNAL("clicked()"), self.supprimerHypothese)
        # Set the table
        self.model.setHorizontalHeaderLabels(["Hypothèse", "Masse", "Affaiblissement"])
        self.hypothesesTableView.horizontalHeader().setResizeMode(QHeaderView.ResizeToContents)

    def ajouterHypothese(self):
        masse_dialog = MasseDialog(self)
        if masse_dialog.exec_() == QDialog.Accepted:
            hypothèse_title = masse_dialog.hypotheseComboBox.currentText()
            masse = masse_dialog.masseSpinBox.value()
            affaiblissement = masse_dialog.affaiblissementSpinBox.value()
            hypothèse_item = ObjectItem(Hypothese(eval(hypothèse_title)))
            if hypothèse_item not in self.model:
                self.model.appendRow([hypothèse_item, ObjectItem(masse), ObjectItem(affaiblissement)])
                self.hypothesesTableView.resizeColumnsToContents()
            else:
                answer = QMessageBox.warning(self, "Hypothèse existante", "L'hypothèse "+hypothèse_title+" existe, voulez vouz la mettre à jour ?", QMessageBox.Yes | QMessageBox.No)
                if answer == QMessageBox.No: return
                for i in range(len(self.model)):
                    if self.model[i].named(hypothèse_title):
                        self.model.setItem(i, 1, ObjectItem(masse))
                        self.model.setItem(i, 2, ObjectItem(affaiblissement))
                        break

    def supprimerHypothese(self):
        selection_model = self.hypothesesTableView.selectionModel()
        if selection_model.hasSelection():
            model_index_list = selection_model.selectedIndexes()
            hypothèses_rows = [x.row() for x in model_index_list]
            for i in reversed(hypothèses_rows):
                del self.model[i]
        else:
            hypothèse_name, ok = QInputDialog.getItem(self, "Supprimer une hypothèse", "Hypothèse", self.model.list_of_str(), 0, False)
            if ok:
                for item in reversed(self.model):
                    if item.named(hypothèse_name):
                        del self.model[item.row()]
                        break

class MasseDialog(QDialog, Ui_masseDialog):
    def __init__(self, parent : AgentDialog):
        super(MasseDialog, self).__init__(parent)
        self.setupUi(self)
        # Set the model
        self.model = parent.parent().hypothesesModel # The model of the hypotheses from the main window
        self.hypotheseComboBox.setModel(self.model)
        # Link the mass and the weaking sliders and the spin boxes
        # Mass
        self.connect(self.masseSlider, SIGNAL("valueChanged(int)"), lambda value: self.masseSpinBox.setValue(value/100))
        self.connect(self.masseSpinBox, SIGNAL("valueChanged(double)"), lambda value: self.masseSlider.setValue(value*100))
        # Weaking
        self.connect(self.affaiblissementSlider, SIGNAL("valueChanged(int)"), lambda value: self.affaiblissementSpinBox.setValue(value/100))
        self.connect(self.affaiblissementSpinBox, SIGNAL("valueChanged(double)"), lambda value: self.affaiblissementSlider.setValue(value*100))


class ItemModel(QStandardItemModel):
    def __init__(self, parent=None):
        super(ItemModel, self).__init__(parent)

    def __contains__(self, item: QStandardItem) -> bool:
        for i in range(self.rowCount()):
            current_item = self.item(i)
            if current_item == item : return True
        return False

    def __iter__(self):
        l = []
        for i in range(self.rowCount()):
            current_item = self.item(i)
            l.append(current_item)
        return iter(l)

    def __getitem__(self, item_index):
        return self.item(item_index)

    def __delitem__(self, item_index):
        self.removeRow(item_index)

    def __len__(self):
        return self.rowCount()

    def list_of_str(self) -> list:
        l = []
        for x in self.__iter__():
            l.append(str(x))
        return l


class ObjectItem(QStandardItem):
    def __init__(self, obj=None):
        super(ObjectItem, self).__init__(str(obj) if obj!=None else None)
        self.item = obj
        self.setEditable(False)

    def __eq__(self, other):
        if isinstance(other, QStandardItem):
            return self.item == other.item
        else: return False

    def __str__(self):
        return self.text()

    def named(self, name : str) -> bool:
        return name == str(self.item)

if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec_())