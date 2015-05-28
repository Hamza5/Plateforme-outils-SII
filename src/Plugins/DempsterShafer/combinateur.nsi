VIAddVersionKey "ProductName" "Combinateur d'évidences"
VIAddVersionKey "ProductVersion" "1.0.0.0"
VIAddVersionKey "FileVersion" "1.0.0.0"
VIAddVersionKey "Comments" "Outil pour l'application de la théorie de Dempster-Shafer"
VIAddVersionKey "FileDescription" "Combinateur d'évidences"
VIAddVersionKey "CompanyName" "Hamza Abbad & Ahmed Zebouchi"
VIAddVersionKey "LegalCopyright" "Copyright 2015 USTHB"
VIProductVersion "1.0.0.0"
Icon "logo.ico"
OutFile "combinateur.exe"
SilentInstall silent
AutoCloseWindow true
RequestExecutionLevel user
Section
SetOutPath $EXEDIR
ExecWait "python MainGUI.py"
IfErrors 0 +2
MessageBox MB_ICONSTOP "Erreur au lancement du programme : Vérifier que Python 3 et PyQt4 sont installés"
SectionEnd