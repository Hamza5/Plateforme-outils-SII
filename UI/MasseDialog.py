# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'MasseDialog.ui'
#
# Created: Sat Feb  7 19:32:42 2015
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

class Ui_masseDialog(object):
    def setupUi(self, masseDialog):
        masseDialog.setObjectName(_fromUtf8("masseDialog"))
        masseDialog.resize(402, 140)
        self.verticalLayout = QtGui.QVBoxLayout(masseDialog)
        self.verticalLayout.setObjectName(_fromUtf8("verticalLayout"))
        self.horizontalLayout = QtGui.QHBoxLayout()
        self.horizontalLayout.setObjectName(_fromUtf8("horizontalLayout"))
        self.hypotheseLabel = QtGui.QLabel(masseDialog)
        self.hypotheseLabel.setObjectName(_fromUtf8("hypotheseLabel"))
        self.horizontalLayout.addWidget(self.hypotheseLabel)
        self.hypotheseComboBox = QtGui.QComboBox(masseDialog)
        sizePolicy = QtGui.QSizePolicy(QtGui.QSizePolicy.Expanding, QtGui.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.hypotheseComboBox.sizePolicy().hasHeightForWidth())
        self.hypotheseComboBox.setSizePolicy(sizePolicy)
        self.hypotheseComboBox.setObjectName(_fromUtf8("hypotheseComboBox"))
        self.horizontalLayout.addWidget(self.hypotheseComboBox)
        self.verticalLayout.addLayout(self.horizontalLayout)
        self.horizontalLayout_2 = QtGui.QHBoxLayout()
        self.horizontalLayout_2.setObjectName(_fromUtf8("horizontalLayout_2"))
        self.masseLabel = QtGui.QLabel(masseDialog)
        self.masseLabel.setObjectName(_fromUtf8("masseLabel"))
        self.horizontalLayout_2.addWidget(self.masseLabel)
        self.masseSlider = QtGui.QSlider(masseDialog)
        self.masseSlider.setMaximum(100)
        self.masseSlider.setOrientation(QtCore.Qt.Horizontal)
        self.masseSlider.setObjectName(_fromUtf8("masseSlider"))
        self.horizontalLayout_2.addWidget(self.masseSlider)
        self.masseSpinBox = QtGui.QDoubleSpinBox(masseDialog)
        self.masseSpinBox.setMaximum(1.0)
        self.masseSpinBox.setSingleStep(0.1)
        self.masseSpinBox.setObjectName(_fromUtf8("masseSpinBox"))
        self.horizontalLayout_2.addWidget(self.masseSpinBox)
        self.verticalLayout.addLayout(self.horizontalLayout_2)
        self.horizontalLayout_3 = QtGui.QHBoxLayout()
        self.horizontalLayout_3.setObjectName(_fromUtf8("horizontalLayout_3"))
        self.affaiblissementLabel = QtGui.QLabel(masseDialog)
        self.affaiblissementLabel.setObjectName(_fromUtf8("affaiblissementLabel"))
        self.horizontalLayout_3.addWidget(self.affaiblissementLabel)
        self.affaiblissementSlider = QtGui.QSlider(masseDialog)
        self.affaiblissementSlider.setMaximum(100)
        self.affaiblissementSlider.setOrientation(QtCore.Qt.Horizontal)
        self.affaiblissementSlider.setObjectName(_fromUtf8("affaiblissementSlider"))
        self.horizontalLayout_3.addWidget(self.affaiblissementSlider)
        self.affaiblissementSpinBox = QtGui.QDoubleSpinBox(masseDialog)
        self.affaiblissementSpinBox.setMaximum(1.0)
        self.affaiblissementSpinBox.setSingleStep(0.1)
        self.affaiblissementSpinBox.setProperty("value", 0.0)
        self.affaiblissementSpinBox.setObjectName(_fromUtf8("affaiblissementSpinBox"))
        self.horizontalLayout_3.addWidget(self.affaiblissementSpinBox)
        self.verticalLayout.addLayout(self.horizontalLayout_3)
        self.buttonBox = QtGui.QDialogButtonBox(masseDialog)
        self.buttonBox.setOrientation(QtCore.Qt.Horizontal)
        self.buttonBox.setStandardButtons(QtGui.QDialogButtonBox.Cancel|QtGui.QDialogButtonBox.Ok)
        self.buttonBox.setObjectName(_fromUtf8("buttonBox"))
        self.verticalLayout.addWidget(self.buttonBox)

        self.retranslateUi(masseDialog)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("accepted()")), masseDialog.accept)
        QtCore.QObject.connect(self.buttonBox, QtCore.SIGNAL(_fromUtf8("rejected()")), masseDialog.reject)
        QtCore.QMetaObject.connectSlotsByName(masseDialog)

    def retranslateUi(self, masseDialog):
        masseDialog.setWindowTitle(_translate("masseDialog", "Affecter une hypothèse", None))
        self.hypotheseLabel.setText(_translate("masseDialog", "Hypothèse", None))
        self.masseLabel.setText(_translate("masseDialog", "Masse", None))
        self.affaiblissementLabel.setText(_translate("masseDialog", "Affaiblissement", None))

