# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'HypotheseDialog.ui'
#
# Created: Mon Feb  2 19:06:17 2015
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

class Ui_hypothesesDialog(object):
    def setupUi(self, hypothesesDialog):
        hypothesesDialog.setObjectName(_fromUtf8("hypothesesDialog"))
        hypothesesDialog.resize(400, 250)
        hypothesesDialog.setModal(True)
        self.verticalLayout = QtGui.QVBoxLayout(hypothesesDialog)
        self.verticalLayout.setObjectName(_fromUtf8("verticalLayout"))
        self.groupBox = QtGui.QGroupBox(hypothesesDialog)
        self.groupBox.setObjectName(_fromUtf8("groupBox"))
        self.horizontalLayout = QtGui.QHBoxLayout(self.groupBox)
        self.horizontalLayout.setObjectName(_fromUtf8("horizontalLayout"))
        self.hypothesesDialogListView = QtGui.QListView(self.groupBox)
        self.hypothesesDialogListView.setSelectionMode(QtGui.QAbstractItemView.MultiSelection)
        self.hypothesesDialogListView.setObjectName(_fromUtf8("hypothesesDialogListView"))
        self.horizontalLayout.addWidget(self.hypothesesDialogListView)
        self.verticalLayout.addWidget(self.groupBox)
        self.buttonBox = QtGui.QDialogButtonBox(hypothesesDialog)
        self.buttonBox.setOrientation(QtCore.Qt.Horizontal)
        self.buttonBox.setStandardButtons(QtGui.QDialogButtonBox.Cancel|QtGui.QDialogButtonBox.Ok)
        self.buttonBox.setObjectName(_fromUtf8("buttonBox"))
        self.verticalLayout.addWidget(self.buttonBox)

        self.retranslateUi(hypothesesDialog)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("accepted()")), hypothesesDialog.accept)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("rejected()")), hypothesesDialog.reject)
        QtCore.QMetaObject.connectSlotsByName(hypothesesDialog)

    def retranslateUi(self, hypothesesDialog):
        hypothesesDialog.setWindowTitle(_translate("hypothesesDialog", "Fomer une hypothèse", None))
        self.groupBox.setTitle(_translate("hypothesesDialog", "Sélectionner les états pour former une hypothèse", None))

