VIAddVersionKey "ProductName" "Plateforme d'outils SII"
VIAddVersionKey "ProductVersion" "1.0.0.0"
VIAddVersionKey "FileVersion" "1.0.0.0"
VIAddVersionKey "Comments" "Plateforme contenant les outils de SII"
VIAddVersionKey "FileDescription" "Plateforme d'outils SII"
VIAddVersionKey "CompanyName" "Hamza Abbad & Ahmed Zebouchi"
VIAddVersionKey "LegalCopyright" "Copyright 2015 USTHB"
VIProductVersion "1.0.0.0"
Icon "logo.ico"
OutFile "plateforme.exe"
SilentInstall silent
AutoCloseWindow true
RequestExecutionLevel user
Section
SetOutPath $EXEDIR
ExecWait "javaw -cp .;jgraphx.jar -splash:splash.png MainWindow"
IfErrors 0 +2
MessageBox MB_ICONSTOP "Erreur au lancement du programme : Vérifier que Java est bien installé"
SectionEnd