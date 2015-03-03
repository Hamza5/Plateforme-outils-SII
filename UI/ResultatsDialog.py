# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'ResultatsDialog.ui'
#
# Created: Tue Mar  3 10:50:13 2015
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
        self.resultsTableWidget = QtGui.QTableWidget(resultsDialog)
        self.resultsTableWidget.setAutoFillBackground(True)
        self.resultsTableWidget.setObjectName(_fromUtf8("resultsTableWidget"))
        self.resultsTableWidget.setColumnCount(4)
        self.resultsTableWidget.setRowCount(0)
        item = QtGui.QTableWidgetItem()
        self.resultsTableWidget.setHorizontalHeaderItem(0, item)
        item = QtGui.QTableWidgetItem()
        self.resultsTableWidget.setHorizontalHeaderItem(1, item)
        item = QtGui.QTableWidgetItem()
        self.resultsTableWidget.setHorizontalHeaderItem(2, item)
        item = QtGui.QTableWidgetItem()
        self.resultsTableWidget.setHorizontalHeaderItem(3, item)
        self.verticalLayout.addWidget(self.resultsTableWidget)
        self.buttonBox = QtGui.QDialogButtonBox(resultsDialog)
        self.buttonBox.setStandardButtons(QtGui.QDialogButtonBox.Ok)
        self.buttonBox.setCenterButtons(True)
        self.buttonBox.setObjectName(_fromUtf8("buttonBox"))
        self.verticalLayout.addWidget(self.buttonBox)

        self.retranslateUi(resultsDialog)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("accepted()")), resultsDialog.accept)
        QtCore.QMetaObject.connectSlotsByName(resultsDialog)

    def retranslateUi(self, resultsDialog):
        resultsDialog.setWindowTitle(_translate("resultsDialog", "Résultats", None))
        item = self.resultsTableWidget.horizontalHeaderItem(0)
        item.setText(_translate("resultsDialog", "Hypothèse", None))
        item = self.resultsTableWidget.horizontalHeaderItem(1)
        item.setText(_translate("resultsDialog", "Masse", None))
        item = self.resultsTableWidget.horizontalHeaderItem(2)
        item.setText(_translate("resultsDialog", "Croyance", None))
        item = self.resultsTableWidget.horizontalHeaderItem(3)
        item.setText(_translate("resultsDialog", "Plausibilité", None))

