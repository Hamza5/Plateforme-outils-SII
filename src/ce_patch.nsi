!include MUI2.nsh
# Constants
!define APPNAME "patch de Combinateur d'évidences"
!define COMPANYNAME "USTHB"
!define EXE "ce_patch-1.0.1.exe"
!define ICON "Plugins\DempsterShafer\logo.ico"

Name "${APPNAME}"
OutFile "${EXE}"
RequestExecutionLevel admin
Unicode true
InstallDir "$PROGRAMFILES\${COMPANYNAME}\Plateforme d'outils SII"
XPStyle on
!define MUI_ICON ${ICON}
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
# Install language
!insertmacro MUI_LANGUAGE "French"

Section "Install"
SetOutPath "$INSTDIR\Plugins\DempsterShafer"
File "Plugins\DempsterShafer\MainGUI.pyw"
File "Plugins\DempsterShafer\combinateur.exe"
SectionEnd
