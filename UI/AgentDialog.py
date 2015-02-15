# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'AgentDialog.ui'
#
# Created: Fri Feb 13 22:24:32 2015
#      by: PyQt4 UI code generator 4.10.4
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    def _fromUtf8(s):
        return s

try:
    _encoding = QtGui.QApplication.UnicodeUTF8
    def _translate(context, text, disambig):
        return QtGui.QApplication.translate(context, text, disambig, _encoding)
except AttributeError:
    def _translate(context, text, disambig):
        return QtGui.QApplication.translate(context, text, disambig)

class Ui_agentDialog(object):
    def setupUi(self, agentDialog):
        agentDialog.setObjectName(_fromUtf8("agentDialog"))
        agentDialog.resize(400, 250)
        self.verticalLayout = QtGui.QVBoxLayout(agentDialog)
        self.verticalLayout.setObjectName(_fromUtf8("verticalLayout"))
        self.horizontalLayout = QtGui.QHBoxLayout()
        self.horizontalLayout.setObjectName(_fromUtf8("horizontalLayout"))
        self.nomLabel = QtGui.QLabel(agentDialog)
        self.nomLabel.setObjectName(_fromUtf8("nomLabel"))
        self.horizontalLayout.addWidget(self.nomLabel)
        self.nomLineEdit = QtGui.QLineEdit(agentDialog)
        self.nomLineEdit.setObjectName(_fromUtf8("nomLineEdit"))
        self.horizontalLayout.addWidget(self.nomLineEdit)
        self.activeCheckBox = QtGui.QCheckBox(agentDialog)
        self.activeCheckBox.setChecked(True)
        self.activeCheckBox.setObjectName(_fromUtf8("activeCheckBox"))
        self.horizontalLayout.addWidget(self.activeCheckBox)
        self.verticalLayout.addLayout(self.horizontalLayout)
        self.horizontalLayout_2 = QtGui.QHBoxLayout()
        self.horizontalLayout_2.setObjectName(_fromUtf8("horizontalLayout_2"))
        self.fiabiliteLabel = QtGui.QLabel(agentDialog)
        self.fiabiliteLabel.setObjectName(_fromUtf8("fiabiliteLabel"))
        self.horizontalLayout_2.addWidget(self.fiabiliteLabel)
        self.fiabiliteSlider = QtGui.QSlider(agentDialog)
        self.fiabiliteSlider.setMaximum(100)
        self.fiabiliteSlider.setProperty("value", 100)
        self.fiabiliteSlider.setOrientation(QtCore.Qt.Horizontal)
        self.fiabiliteSlider.setObjectName(_fromUtf8("fiabiliteSlider"))
        self.horizontalLayout_2.addWidget(self.fiabiliteSlider)
        self.fiabiliteSpinBox = QtGui.QDoubleSpinBox(agentDialog)
        self.fiabiliteSpinBox.setMaximum(1.0)
        self.fiabiliteSpinBox.setSingleStep(0.1)
        self.fiabiliteSpinBox.setProperty("value", 1.0)
        self.fiabiliteSpinBox.setObjectName(_fromUtf8("fiabiliteSpinBox"))
        self.horizontalLayout_2.addWidget(self.fiabiliteSpinBox)
        self.verticalLayout.addLayout(self.horizontalLayout_2)
        self.hypothesesGroupBox = QtGui.QGroupBox(agentDialog)
        self.hypothesesGroupBox.setObjectName(_fromUtf8("hypothesesGroupBox"))
        self.verticalLayout_2 = QtGui.QVBoxLayout(self.hypothesesGroupBox)
        self.verticalLayout_2.setObjectName(_fromUtf8("verticalLayout_2"))
        self.horizontalLayout_3 = QtGui.QHBoxLayout()
        self.horizontalLayout_3.setObjectName(_fromUtf8("horizontalLayout_3"))
        spacerItem = QtGui.QSpacerItem(40, 20, QtGui.QSizePolicy.Expanding, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout_3.addItem(spacerItem)
        self.ajouterMasseButton = QtGui.QToolButton(self.hypothesesGroupBox)
        self.ajouterMasseButton.setObjectName(_fromUtf8("ajouterMasseButton"))
        self.horizontalLayout_3.addWidget(self.ajouterMasseButton)
        self.supprimerMasseButton = QtGui.QToolButton(self.hypothesesGroupBox)
        self.supprimerMasseButton.setObjectName(_fromUtf8("supprimerMasseButton"))
        self.horizontalLayout_3.addWidget(self.supprimerMasseButton)
        spacerItem1 = QtGui.QSpacerItem(40, 20, QtGui.QSizePolicy.Expanding, QtGui.QSizePolicy.Minimum)
        self.horizontalLayout_3.addItem(spacerItem1)
        self.verticalLayout_2.addLayout(self.horizontalLayout_3)
        self.hypothesesTableView = QtGui.QTableView(self.hypothesesGroupBox)
        self.hypothesesTableView.setSelectionBehavior(QtGui.QAbstractItemView.SelectRows)
        self.hypothesesTableView.setObjectName(_fromUtf8("hypothesesTableView"))
        self.verticalLayout_2.addWidget(self.hypothesesTableView)
        self.verticalLayout.addWidget(self.hypothesesGroupBox)
        self.buttonBox = QtGui.QDialogButtonBox(agentDialog)
        self.buttonBox.setOrientation(QtCore.Qt.Horizontal)
        self.buttonBox.setStandardButtons(QtGui.QDialogButtonBox.Cancel|QtGui.QDialogButtonBox.Ok)
        self.buttonBox.setObjectName(_fromUtf8("buttonBox"))
        self.verticalLayout.addWidget(self.buttonBox)

        self.retranslateUi(agentDialog)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("accepted()")), agentDialog.accept)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("rejected()")), agentDialog.reject)
        QtCore.QMetaObject.connectSlotsByName(agentDialog)

    def retranslateUi(self, agentDialog):
        agentDialog.setWindowTitle(_translate("agentDialog", "Ajouter un agent", None))
        self.nomLabel.setText(_translate("agentDialog", "Nom", None))
        self.activeCheckBox.setText(_translate("agentDialog", "Activé", None))
        self.fiabiliteLabel.setText(_translate("agentDialog", "Fiabilité", None))
        self.hypothesesGroupBox.setTitle(_translate("agentDialog", "Hypothèses / Masses", None))
        self.ajouterMasseButton.setText(_translate("agentDialog", "Affecter une hypothèse", None))
        self.supprimerMasseButton.setText(_translate("agentDialog", "Supprimer", None))

