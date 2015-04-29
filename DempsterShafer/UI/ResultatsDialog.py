# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'ResultatsDialog.ui'
#
# Created: Tue Apr 28 21:14:20 2015
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

class Ui_resultsDialog(object):
    def setupUi(self, resultsDialog):
        resultsDialog.setObjectName(_fromUtf8("resultsDialog"))
        resultsDialog.setWindowModality(QtCore.Qt.ApplicationModal)
        resultsDialog.resize(509, 371)
        resultsDialog.setLocale(QtCore.QLocale(QtCore.QLocale.French, QtCore.QLocale.France))
        resultsDialog.setModal(True)
        self.verticalLayout = QtGui.QVBoxLayout(resultsDialog)
        self.verticalLayout.setObjectName(_fromUtf8("verticalLayout"))
        self.titleLabel = QtGui.QLabel(resultsDialog)
        self.titleLabel.setAlignment(QtCore.Qt.AlignCenter)
        self.titleLabel.setObjectName(_fromUtf8("titleLabel"))
        self.verticalLayout.addWidget(self.titleLabel)
        self.descriptionLabel = QtGui.QLabel(resultsDialog)
        self.descriptionLabel.setAlignment(QtCore.Qt.AlignCenter)
        self.descriptionLabel.setObjectName(_fromUtf8("descriptionLabel"))
        self.verticalLayout.addWidget(self.descriptionLabel)
        self.methodLabel = QtGui.QLabel(resultsDialog)
        self.methodLabel.setObjectName(_fromUtf8("methodLabel"))
        self.verticalLayout.addWidget(self.methodLabel)
        self.resultsTableWidget = QtGui.QTableWidget(resultsDialog)
        self.resultsTableWidget.setAutoFillBackground(True)
        self.resultsTableWidget.setEditTriggers(QtGui.QAbstractItemView.NoEditTriggers)
        self.resultsTableWidget.setSelectionBehavior(QtGui.QAbstractItemView.SelectRows)
        self.resultsTableWidget.setObjectName(_fromUtf8("resultsTableWidget"))
        self.resultsTableWidget.setColumnCount(4)
        self.resultsTableWidget.setRowCount(0)
        item = QtGui.QTableWidgetItem()
        item.setTextAlignment(QtCore.Qt.AlignLeft|QtCore.Qt.AlignVCenter)
        self.resultsTableWidget.setHorizontalHeaderItem(0, item)
        item = QtGui.QTableWidgetItem()
        item.setTextAlignment(QtCore.Qt.AlignHCenter|QtCore.Qt.AlignVCenter|QtCore.Qt.AlignCenter)
        self.resultsTableWidget.setHorizontalHeaderItem(1, item)
        item = QtGui.QTableWidgetItem()
        item.setTextAlignment(QtCore.Qt.AlignHCenter|QtCore.Qt.AlignVCenter|QtCore.Qt.AlignCenter)
        self.resultsTableWidget.setHorizontalHeaderItem(2, item)
        item = QtGui.QTableWidgetItem()
        item.setTextAlignment(QtCore.Qt.AlignHCenter|QtCore.Qt.AlignVCenter|QtCore.Qt.AlignCenter)
        self.resultsTableWidget.setHorizontalHeaderItem(3, item)
        self.resultsTableWidget.horizontalHeader().setStretchLastSection(True)
        self.resultsTableWidget.verticalHeader().setSortIndicatorShown(True)
        self.verticalLayout.addWidget(self.resultsTableWidget)
        self.horizontalLayout_2 = QtGui.QHBoxLayout()
        self.horizontalLayout_2.setObjectName(_fromUtf8("horizontalLayout_2"))
        self.decisionMethodLabel = QtGui.QLabel(resultsDialog)
        self.decisionMethodLabel.setObjectName(_fromUtf8("decisionMethodLabel"))
        self.horizontalLayout_2.addWidget(self.decisionMethodLabel)
        self.rechercheLineEdit = QtGui.QLineEdit(resultsDialog)
        sizePolicy = QtGui.QSizePolicy(QtGui.QSizePolicy.Preferred, QtGui.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.rechercheLineEdit.sizePolicy().hasHeightForWidth())
        self.rechercheLineEdit.setSizePolicy(sizePolicy)
        self.rechercheLineEdit.setObjectName(_fromUtf8("rechercheLineEdit"))
        self.horizontalLayout_2.addWidget(self.rechercheLineEdit)
        self.verticalLayout.addLayout(self.horizontalLayout_2)
        self.decisionLabel = QtGui.QLabel(resultsDialog)
        self.decisionLabel.setObjectName(_fromUtf8("decisionLabel"))
        self.verticalLayout.addWidget(self.decisionLabel)
        self.horizontalLayout = QtGui.QHBoxLayout()
        self.horizontalLayout.setObjectName(_fromUtf8("horizontalLayout"))
        self.agentButton = QtGui.QPushButton(resultsDialog)
        self.agentButton.setObjectName(_fromUtf8("agentButton"))
        self.horizontalLayout.addWidget(self.agentButton)
        self.buttonBox = QtGui.QDialogButtonBox(resultsDialog)
        self.buttonBox.setStandardButtons(QtGui.QDialogButtonBox.Ok)
        self.buttonBox.setCenterButtons(False)
        self.buttonBox.setObjectName(_fromUtf8("buttonBox"))
        self.horizontalLayout.addWidget(self.buttonBox)
        self.verticalLayout.addLayout(self.horizontalLayout)

        self.retranslateUi(resultsDialog)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("accepted()")), resultsDialog.accept)
        QtCore.QMetaObject.connectSlotsByName(resultsDialog)

    def retranslateUi(self, resultsDialog):
        resultsDialog.setWindowTitle(_translate("resultsDialog", "Résultats", None))
        self.titleLabel.setText(_translate("resultsDialog", "Titre", None))
        self.descriptionLabel.setText(_translate("resultsDialog", "Description", None))
        self.methodLabel.setText(_translate("resultsDialog", "Méthod de fusion", None))
        self.resultsTableWidget.setSortingEnabled(True)
        item = self.resultsTableWidget.horizontalHeaderItem(0)
        item.setText(_translate("resultsDialog", "Hypothèse", None))
        item = self.resultsTableWidget.horizontalHeaderItem(1)
        item.setText(_translate("resultsDialog", "Masse", None))
        item = self.resultsTableWidget.horizontalHeaderItem(2)
        item.setText(_translate("resultsDialog", "Croyance", None))
        item = self.resultsTableWidget.horizontalHeaderItem(3)
        item.setText(_translate("resultsDialog", "Plausibilité", None))
        self.decisionMethodLabel.setText(_translate("resultsDialog", "Méthode de décision", None))
        self.rechercheLineEdit.setToolTip(_translate("resultsDialog", "Sélectionner les les hypothèses recherchées", None))
        self.rechercheLineEdit.setPlaceholderText(_translate("resultsDialog", "Rechercher", None))
        self.decisionLabel.setText(_translate("resultsDialog", "Décision", None))
        self.agentButton.setText(_translate("resultsDialog", "&Créer un nouvel agent à partir de ces résultats", None))

