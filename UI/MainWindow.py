# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'MainWindow.ui'
#
# Created: Tue Mar 10 08:56:01 2015
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

class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName(_fromUtf8("MainWindow"))
        MainWindow.resize(600, 600)
        MainWindow.setLocale(QtCore.QLocale(QtCore.QLocale.French, QtCore.QLocale.France))
        self.centralwidget = QtGui.QWidget(MainWindow)
        self.centralwidget.setObjectName(_fromUtf8("centralwidget"))
        self.verticalLayout_4 = QtGui.QVBoxLayout(self.centralwidget)
        self.verticalLayout_4.setObjectName(_fromUtf8("verticalLayout_4"))
        self.splitter = QtGui.QSplitter(self.centralwidget)
        sizePolicy = QtGui.QSizePolicy(QtGui.QSizePolicy.Preferred, QtGui.QSizePolicy.Expanding)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.splitter.sizePolicy().hasHeightForWidth())
        self.splitter.setSizePolicy(sizePolicy)
        self.splitter.setOrientation(QtCore.Qt.Vertical)
        self.splitter.setObjectName(_fromUtf8("splitter"))
        self.etatsGroupBox = QtGui.QGroupBox(self.splitter)
        self.etatsGroupBox.setObjectName(_fromUtf8("etatsGroupBox"))
        self.horizontalLayout = QtGui.QHBoxLayout(self.etatsGroupBox)
        self.horizontalLayout.setObjectName(_fromUtf8("horizontalLayout"))
        self.etatsListView = QtGui.QListView(self.etatsGroupBox)
        self.etatsListView.setSelectionMode(QtGui.QAbstractItemView.ExtendedSelection)
        self.etatsListView.setObjectName(_fromUtf8("etatsListView"))
        self.horizontalLayout.addWidget(self.etatsListView)
        self.hypothesesGroupBox = QtGui.QGroupBox(self.splitter)
        self.hypothesesGroupBox.setObjectName(_fromUtf8("hypothesesGroupBox"))
        self.horizontalLayout_2 = QtGui.QHBoxLayout(self.hypothesesGroupBox)
        self.horizontalLayout_2.setObjectName(_fromUtf8("horizontalLayout_2"))
        self.hypothesesListView = QtGui.QListView(self.hypothesesGroupBox)
        self.hypothesesListView.setSelectionMode(QtGui.QAbstractItemView.ExtendedSelection)
        self.hypothesesListView.setObjectName(_fromUtf8("hypothesesListView"))
        self.horizontalLayout_2.addWidget(self.hypothesesListView)
        self.AgentsGroupBox = QtGui.QGroupBox(self.splitter)
        self.AgentsGroupBox.setObjectName(_fromUtf8("AgentsGroupBox"))
        self.horizontalLayout_3 = QtGui.QHBoxLayout(self.AgentsGroupBox)
        self.horizontalLayout_3.setObjectName(_fromUtf8("horizontalLayout_3"))
        self.agentsTreeView = QtGui.QTreeView(self.AgentsGroupBox)
        self.agentsTreeView.setObjectName(_fromUtf8("agentsTreeView"))
        self.horizontalLayout_3.addWidget(self.agentsTreeView)
        self.verticalLayout_4.addWidget(self.splitter)
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtGui.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 600, 21))
        self.menubar.setObjectName(_fromUtf8("menubar"))
        self.menuFichier = QtGui.QMenu(self.menubar)
        self.menuFichier.setObjectName(_fromUtf8("menuFichier"))
        self.menuEdition = QtGui.QMenu(self.menubar)
        self.menuEdition.setObjectName(_fromUtf8("menuEdition"))
        self.menuHypotheses = QtGui.QMenu(self.menuEdition)
        self.menuHypotheses.setLocale(QtCore.QLocale(QtCore.QLocale.French, QtCore.QLocale.France))
        self.menuHypotheses.setObjectName(_fromUtf8("menuHypotheses"))
        self.menuAgents = QtGui.QMenu(self.menuEdition)
        self.menuAgents.setLocale(QtCore.QLocale(QtCore.QLocale.French, QtCore.QLocale.France))
        self.menuAgents.setObjectName(_fromUtf8("menuAgents"))
        self.menuEtats_du_monde = QtGui.QMenu(self.menuEdition)
        self.menuEtats_du_monde.setLocale(QtCore.QLocale(QtCore.QLocale.French, QtCore.QLocale.France))
        self.menuEtats_du_monde.setObjectName(_fromUtf8("menuEtats_du_monde"))
        self.menuM_thode_de_d_cision = QtGui.QMenu(self.menuEdition)
        self.menuM_thode_de_d_cision.setObjectName(_fromUtf8("menuM_thode_de_d_cision"))
        self.menuM_thode_de_calcule = QtGui.QMenu(self.menuEdition)
        self.menuM_thode_de_calcule.setLocale(QtCore.QLocale(QtCore.QLocale.French, QtCore.QLocale.France))
        self.menuM_thode_de_calcule.setObjectName(_fromUtf8("menuM_thode_de_calcule"))
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtGui.QStatusBar(MainWindow)
        self.statusbar.setObjectName(_fromUtf8("statusbar"))
        MainWindow.setStatusBar(self.statusbar)
        self.actionTitre = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("document-edit"))
        self.actionTitre.setIcon(icon)
        self.actionTitre.setObjectName(_fromUtf8("actionTitre"))
        self.actionDescription = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("document-properties"))
        self.actionDescription.setIcon(icon)
        self.actionDescription.setObjectName(_fromUtf8("actionDescription"))
        self.actionOuvrir = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("document-open"))
        self.actionOuvrir.setIcon(icon)
        self.actionOuvrir.setObjectName(_fromUtf8("actionOuvrir"))
        self.actionEnregistrer = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("document-save"))
        self.actionEnregistrer.setIcon(icon)
        self.actionEnregistrer.setObjectName(_fromUtf8("actionEnregistrer"))
        self.actionQuitter = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("application-exit"))
        self.actionQuitter.setIcon(icon)
        self.actionQuitter.setMenuRole(QtGui.QAction.QuitRole)
        self.actionQuitter.setObjectName(_fromUtf8("actionQuitter"))
        self.actionDempster_Shafer = QtGui.QAction(MainWindow)
        self.actionDempster_Shafer.setCheckable(True)
        self.actionDempster_Shafer.setChecked(True)
        self.actionDempster_Shafer.setObjectName(_fromUtf8("actionDempster_Shafer"))
        self.actionDubois_Prade = QtGui.QAction(MainWindow)
        self.actionDubois_Prade.setCheckable(True)
        self.actionDubois_Prade.setObjectName(_fromUtf8("actionDubois_Prade"))
        self.actionAjouterEtat = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("list-add"))
        self.actionAjouterEtat.setIcon(icon)
        self.actionAjouterEtat.setObjectName(_fromUtf8("actionAjouterEtat"))
        self.actionSupprimerEtat = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("list-remove"))
        self.actionSupprimerEtat.setIcon(icon)
        self.actionSupprimerEtat.setObjectName(_fromUtf8("actionSupprimerEtat"))
        self.actionAjouterHypothese = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("list-add"))
        self.actionAjouterHypothese.setIcon(icon)
        self.actionAjouterHypothese.setObjectName(_fromUtf8("actionAjouterHypothese"))
        self.actionSupprimerHypothese = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("list-remove"))
        self.actionSupprimerHypothese.setIcon(icon)
        self.actionSupprimerHypothese.setObjectName(_fromUtf8("actionSupprimerHypothese"))
        self.actionAjouterAgent = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("list-add"))
        self.actionAjouterAgent.setIcon(icon)
        self.actionAjouterAgent.setObjectName(_fromUtf8("actionAjouterAgent"))
        self.actionSupprimerAgent = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("list-remove"))
        self.actionSupprimerAgent.setIcon(icon)
        self.actionSupprimerAgent.setObjectName(_fromUtf8("actionSupprimerAgent"))
        self.actionModifierAgent = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("edit-rename"))
        self.actionModifierAgent.setIcon(icon)
        self.actionModifierAgent.setObjectName(_fromUtf8("actionModifierAgent"))
        self.actionSmets = QtGui.QAction(MainWindow)
        self.actionSmets.setCheckable(True)
        self.actionSmets.setObjectName(_fromUtf8("actionSmets"))
        self.actionNouveau = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("document-new"))
        self.actionNouveau.setIcon(icon)
        self.actionNouveau.setObjectName(_fromUtf8("actionNouveau"))
        self.actionCalculer = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("system-run"))
        self.actionCalculer.setIcon(icon)
        self.actionCalculer.setObjectName(_fromUtf8("actionCalculer"))
        self.actionYager = QtGui.QAction(MainWindow)
        self.actionYager.setCheckable(True)
        self.actionYager.setObjectName(_fromUtf8("actionYager"))
        self.actionOuvrir_des_resultats = QtGui.QAction(MainWindow)
        icon = QtGui.QIcon.fromTheme(_fromUtf8("document-import"))
        self.actionOuvrir_des_resultats.setIcon(icon)
        self.actionOuvrir_des_resultats.setObjectName(_fromUtf8("actionOuvrir_des_resultats"))
        self.actionOptimiste = QtGui.QAction(MainWindow)
        self.actionOptimiste.setCheckable(True)
        self.actionOptimiste.setChecked(True)
        self.actionOptimiste.setObjectName(_fromUtf8("actionOptimiste"))
        self.actionPessimiste = QtGui.QAction(MainWindow)
        self.actionPessimiste.setCheckable(True)
        self.actionPessimiste.setObjectName(_fromUtf8("actionPessimiste"))
        self.actionPignistique = QtGui.QAction(MainWindow)
        self.actionPignistique.setCheckable(True)
        self.actionPignistique.setObjectName(_fromUtf8("actionPignistique"))
        self.menuFichier.addAction(self.actionNouveau)
        self.menuFichier.addAction(self.actionOuvrir)
        self.menuFichier.addAction(self.actionOuvrir_des_resultats)
        self.menuFichier.addAction(self.actionEnregistrer)
        self.menuFichier.addSeparator()
        self.menuFichier.addAction(self.actionQuitter)
        self.menuHypotheses.addAction(self.actionAjouterHypothese)
        self.menuHypotheses.addAction(self.actionSupprimerHypothese)
        self.menuAgents.addAction(self.actionAjouterAgent)
        self.menuAgents.addAction(self.actionSupprimerAgent)
        self.menuAgents.addAction(self.actionModifierAgent)
        self.menuEtats_du_monde.addAction(self.actionAjouterEtat)
        self.menuEtats_du_monde.addAction(self.actionSupprimerEtat)
        self.menuM_thode_de_d_cision.addAction(self.actionOptimiste)
        self.menuM_thode_de_d_cision.addAction(self.actionPessimiste)
        self.menuM_thode_de_d_cision.addAction(self.actionPignistique)
        self.menuM_thode_de_calcule.addAction(self.actionDempster_Shafer)
        self.menuM_thode_de_calcule.addAction(self.actionDubois_Prade)
        self.menuM_thode_de_calcule.addAction(self.actionSmets)
        self.menuM_thode_de_calcule.addAction(self.actionYager)
        self.menuEdition.addAction(self.actionTitre)
        self.menuEdition.addAction(self.actionDescription)
        self.menuEdition.addSeparator()
        self.menuEdition.addAction(self.menuM_thode_de_calcule.menuAction())
        self.menuEdition.addAction(self.menuM_thode_de_d_cision.menuAction())
        self.menuEdition.addSeparator()
        self.menuEdition.addAction(self.menuEtats_du_monde.menuAction())
        self.menuEdition.addAction(self.menuHypotheses.menuAction())
        self.menuEdition.addAction(self.menuAgents.menuAction())
        self.menuEdition.addSeparator()
        self.menuEdition.addAction(self.actionCalculer)
        self.menubar.addAction(self.menuFichier.menuAction())
        self.menubar.addAction(self.menuEdition.menuAction())

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        MainWindow.setWindowTitle(_translate("MainWindow", "Calculateur", None))
        self.etatsGroupBox.setTitle(_translate("MainWindow", "Etats du monde", None))
        self.hypothesesGroupBox.setTitle(_translate("MainWindow", "Hypothèses", None))
        self.AgentsGroupBox.setTitle(_translate("MainWindow", "Agents", None))
        self.menuFichier.setTitle(_translate("MainWindow", "&Fichier", None))
        self.menuEdition.setTitle(_translate("MainWindow", "&Projet", None))
        self.menuHypotheses.setTitle(_translate("MainWindow", "&Hypothèses", None))
        self.menuAgents.setTitle(_translate("MainWindow", "&Agents", None))
        self.menuEtats_du_monde.setTitle(_translate("MainWindow", "&Etats du monde", None))
        self.menuM_thode_de_d_cision.setTitle(_translate("MainWindow", "Méthode de décision", None))
        self.menuM_thode_de_calcule.setTitle(_translate("MainWindow", "&Méthode de fusion", None))
        self.actionTitre.setText(_translate("MainWindow", "&Titre", None))
        self.actionTitre.setIconText(_translate("MainWindow", "&Titre", None))
        self.actionTitre.setShortcut(_translate("MainWindow", "Ctrl+T", None))
        self.actionDescription.setText(_translate("MainWindow", "&Description", None))
        self.actionDescription.setIconText(_translate("MainWindow", "&Description", None))
        self.actionDescription.setShortcut(_translate("MainWindow", "Ctrl+D", None))
        self.actionOuvrir.setText(_translate("MainWindow", "&Ouvrir", None))
        self.actionOuvrir.setIconText(_translate("MainWindow", "&Ouvrir", None))
        self.actionOuvrir.setShortcut(_translate("MainWindow", "Ctrl+O", None))
        self.actionEnregistrer.setText(_translate("MainWindow", "&Enregistrer", None))
        self.actionEnregistrer.setIconText(_translate("MainWindow", "&Enregistrer", None))
        self.actionEnregistrer.setShortcut(_translate("MainWindow", "Ctrl+S", None))
        self.actionQuitter.setText(_translate("MainWindow", "&Quitter", None))
        self.actionQuitter.setIconText(_translate("MainWindow", "&Quitter", None))
        self.actionQuitter.setShortcut(_translate("MainWindow", "Ctrl+Q", None))
        self.actionDempster_Shafer.setText(_translate("MainWindow", "Dempster-Shafer", None))
        self.actionDubois_Prade.setText(_translate("MainWindow", "Dubois-Prade", None))
        self.actionAjouterEtat.setText(_translate("MainWindow", "Ajouter", None))
        self.actionAjouterEtat.setShortcut(_translate("MainWindow", "Ctrl+E", None))
        self.actionSupprimerEtat.setText(_translate("MainWindow", "Supprimer", None))
        self.actionSupprimerEtat.setShortcut(_translate("MainWindow", "Ctrl+Shift+E", None))
        self.actionAjouterHypothese.setText(_translate("MainWindow", "Ajouter", None))
        self.actionAjouterHypothese.setShortcut(_translate("MainWindow", "Ctrl+H", None))
        self.actionSupprimerHypothese.setText(_translate("MainWindow", "Supprimer", None))
        self.actionSupprimerHypothese.setShortcut(_translate("MainWindow", "Ctrl+Shift+H", None))
        self.actionAjouterAgent.setText(_translate("MainWindow", "Ajouter", None))
        self.actionAjouterAgent.setShortcut(_translate("MainWindow", "Ctrl+G", None))
        self.actionSupprimerAgent.setText(_translate("MainWindow", "Supprimer", None))
        self.actionSupprimerAgent.setShortcut(_translate("MainWindow", "Ctrl+Shift+G", None))
        self.actionModifierAgent.setText(_translate("MainWindow", "Modifier", None))
        self.actionModifierAgent.setShortcut(_translate("MainWindow", "Alt+G", None))
        self.actionSmets.setText(_translate("MainWindow", "Smets", None))
        self.actionNouveau.setText(_translate("MainWindow", "&Nouveau", None))
        self.actionNouveau.setIconText(_translate("MainWindow", "&Nouveau", None))
        self.actionNouveau.setShortcut(_translate("MainWindow", "Ctrl+N", None))
        self.actionCalculer.setText(_translate("MainWindow", "&Calculer", None))
        self.actionCalculer.setShortcut(_translate("MainWindow", "Ctrl+K", None))
        self.actionYager.setText(_translate("MainWindow", "Yager", None))
        self.actionOuvrir_des_resultats.setText(_translate("MainWindow", "Ouvrir des &résultats", None))
        self.actionOptimiste.setText(_translate("MainWindow", "Optimiste", None))
        self.actionPessimiste.setText(_translate("MainWindow", "Pessimiste", None))
        self.actionPignistique.setText(_translate("MainWindow", "Pignistique", None))

