# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'DescriptionDialog.ui'
#
# Created: Tue Feb 10 17:57:55 2015
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

class Ui_descriptionDialog(object):
    def setupUi(self, descriptionDialog):
        descriptionDialog.setObjectName(_fromUtf8("descriptionDialog"))
        descriptionDialog.resize(400, 200)
        self.verticalLayout = QtGui.QVBoxLayout(descriptionDialog)
        self.verticalLayout.setObjectName(_fromUtf8("verticalLayout"))
        self.descriptionGroupBox = QtGui.QGroupBox(descriptionDialog)
        self.descriptionGroupBox.setObjectName(_fromUtf8("descriptionGroupBox"))
        self.horizontalLayout = QtGui.QHBoxLayout(self.descriptionGroupBox)
        self.horizontalLayout.setObjectName(_fromUtf8("horizontalLayout"))
        self.descriptionPlainTextEdit = QtGui.QPlainTextEdit(self.descriptionGroupBox)
        self.descriptionPlainTextEdit.setObjectName(_fromUtf8("descriptionPlainTextEdit"))
        self.horizontalLayout.addWidget(self.descriptionPlainTextEdit)
        self.verticalLayout.addWidget(self.descriptionGroupBox)
        self.buttonBox = QtGui.QDialogButtonBox(descriptionDialog)
        self.buttonBox.setOrientation(QtCore.Qt.Horizontal)
        self.buttonBox.setStandardButtons(QtGui.QDialogButtonBox.Cancel|QtGui.QDialogButtonBox.Ok)
        self.buttonBox.setObjectName(_fromUtf8("buttonBox"))
        self.verticalLayout.addWidget(self.buttonBox)

        self.retranslateUi(descriptionDialog)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("accepted()")), descriptionDialog.accept)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("rejected()")), descriptionDialog.reject)
        QtCore.QMetaObject.connectSlotsByName(descriptionDialog)

    def retranslateUi(self, descriptionDialog):
        descriptionDialog.setWindowTitle(_translate("descriptionDialog", "Description", None))
        self.descriptionGroupBox.setTitle(_translate("descriptionDialog", "Description du problème", None))

