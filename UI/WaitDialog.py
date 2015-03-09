# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'WaitDialog.ui'
#
# Created: Mon Mar  9 15:29:09 2015
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

class Ui_waitDialog(object):
    def setupUi(self, waitDialog):
        waitDialog.setObjectName(_fromUtf8("waitDialog"))
        waitDialog.setWindowModality(QtCore.Qt.ApplicationModal)
        waitDialog.resize(301, 145)
        sizePolicy = QtGui.QSizePolicy(QtGui.QSizePolicy.Minimum, QtGui.QSizePolicy.Preferred)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(waitDialog.sizePolicy().hasHeightForWidth())
        waitDialog.setSizePolicy(sizePolicy)
        waitDialog.setCursor(QtGui.QCursor(QtCore.Qt.BusyCursor))
        waitDialog.setLocale(QtCore.QLocale(QtCore.QLocale.French, QtCore.QLocale.France))
        waitDialog.setModal(True)
        self.verticalLayout = QtGui.QVBoxLayout(waitDialog)
        self.verticalLayout.setObjectName(_fromUtf8("verticalLayout"))
        self.iconLabel = QtGui.QLabel(waitDialog)
        self.iconLabel.setText(_fromUtf8(""))
        self.iconLabel.setAlignment(QtCore.Qt.AlignCenter)
        self.iconLabel.setObjectName(_fromUtf8("iconLabel"))
        self.verticalLayout.addWidget(self.iconLabel)
        self.waitLabel = QtGui.QLabel(waitDialog)
        font = QtGui.QFont()
        font.setPointSize(12)
        self.waitLabel.setFont(font)
        self.waitLabel.setScaledContents(False)
        self.waitLabel.setAlignment(QtCore.Qt.AlignCenter)
        self.waitLabel.setObjectName(_fromUtf8("waitLabel"))
        self.verticalLayout.addWidget(self.waitLabel)
        self.buttonBox = QtGui.QDialogButtonBox(waitDialog)
        self.buttonBox.setOrientation(QtCore.Qt.Horizontal)
        self.buttonBox.setStandardButtons(QtGui.QDialogButtonBox.Cancel)
        self.buttonBox.setCenterButtons(True)
        self.buttonBox.setObjectName(_fromUtf8("buttonBox"))
        self.verticalLayout.addWidget(self.buttonBox)

        self.retranslateUi(waitDialog)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("accepted()")), waitDialog.accept)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("rejected()")), waitDialog.reject)
        QtCore.QMetaObject.connectSlotsByName(waitDialog)

    def retranslateUi(self, waitDialog):
        waitDialog.setWindowTitle(_translate("waitDialog", "Veuillez attendre", None))
        self.waitLabel.setText(_translate("waitDialog", "<html><head/><body><p><span style=\" font-weight:600;\">Calcul en cours</span><br/>Veuillez patienter</p></body></html>", None))

