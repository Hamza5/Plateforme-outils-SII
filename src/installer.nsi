!include MUI2.nsh
!include FileFunc.nsh
# Constants
!define APPNAME "Plateforme d'outils SII"
!define COMPANYNAME "USTHB"
!define EXE "plateforme.exe"
!define ICON "install.ico"
!define REG_UNINSTALL "Software\Microsoft\Windows\CurrentVersion\Uninstall\PlateformeOutilsSII"
!define UNINSTALLER "Uninstall.exe"
!define PYTHON_PATH "C:\Python34"
!define PYTHON_EXE "python-3.4.3.msi"
!define PYQT4_EXE "PyQt4-4.11.3-gpl-Py3.4-Qt5.3.2-x32.exe"

Name "${APPNAME}"
OutFile "Plateforme_outils_SII_install.exe"
RequestExecutionLevel admin
InstallDir "$PROGRAMFILES\${COMPANYNAME}\${APPNAME}"
XPStyle on
!define MUI_ICON ${ICON}
!define MUI_WELCOMEFINISHPAGE_BITMAP "${NSISDIR}\Contrib\Graphics\Wizard\orange.bmp"
!define MUI_UNWELCOMEFINISHPAGE_BITMAP "${NSISDIR}\Contrib\Graphics\Wizard\orange-uninstall.bmp"
!define MUI_WELCOMEPAGE_TITLE_3LINES
Var StartMenuFolder
# Install pages
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_STARTMENU Application $StartMenuFolder
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH
# Uninstall pages
!insertmacro MUI_UNPAGE_WELCOME
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
!insertmacro MUI_UNPAGE_FINISH
# Install language
!insertmacro MUI_LANGUAGE "French"
# Messages
LangString python3_inst ${LANG_FRENCH} "Installation de Python 3.4"
LangString pyqt4_inst ${LANG_FRENCH} "Installation de PyQt4"

Section "Install"
SetOutPath "C:\"
File /r "cygwin"
SetOutPath "$INSTDIR"
File ${PYQT4_EXE}
File ${PYTHON_EXE}
File ${EXE}
File "*.class"
File "*.jar"
File "logo.png"
File "splash.png"
File "logo.ico"
File /r "Plugins"
DetailPrint $(python3_inst)
ExecWait "msiexec /i ${PYTHON_EXE} /qb! TARGETDIR=${PYTHON_PATH} ALLUSERS=1"
Delete ${PYTHON_EXE}
DetailPrint $(pyqt4_inst)
ExecWait "${PYQT4_EXE} /S /SD /D=${PYTHON_PATH}"
Delete ${PYQT4_EXE}
WriteUninstaller "$INSTDIR\${UNINSTALLER}"
WriteRegStr HKLM "${REG_UNINSTALL}" "DisplayName" "${APPNAME}"
WriteRegStr HKLM "${REG_UNINSTALL}" "UninstallString" "$INSTDIR\${UNINSTALLER}"
WriteRegStr HKLM "${REG_UNINSTALL}" "DisplayIcon" "$INSTDIR\${EXE}"
WriteRegStr HKLM "${REG_UNINSTALL}" "Publisher" "${COMPANYNAME}"
WriteRegDWORD HKLM "${REG_UNINSTALL}" "NoModify" 1
WriteRegDWORD HKLM "${REG_UNINSTALL}" "NoRepair" 1
${GetSize} "$INSTDIR" "/S=0K" $0 $1 $2
IntFmt $0 "0x%08X" $0
WriteRegDWORD HKLM "${REG_UNINSTALL}" "EstimatedSize" "$0"
!insertmacro MUI_STARTMENU_WRITE_BEGIN Application
	CreateDirectory "$SMPROGRAMS\$StartMenuFolder"
	CreateShortCut "$SMPROGRAMS\$StartMenuFolder\${APPNAME}.lnk" "$INSTDIR\${EXE}"
	CreateShortCut "$SMPROGRAMS\$StartMenuFolder\Désinstaller.lnk" "$INSTDIR\${UNINSTALLER}"
	CreateShortCut "$DESKTOP\${APPNAME}.lnk" "$INSTDIR\${EXE}"
	CreateShortCut "$SMPROGRAMS\$StartMenuFolder\Combinateur d'évidences.lnk" "$INSTDIR\Plugins\DempsterShafer\combinateur.exe"
  CreateShortCut "$DESKTOP\Combinateur d'évidences.lnk" "$INSTDIR\Plugins\DempsterShafer\combinateur.exe"
!insertmacro MUI_STARTMENU_WRITE_END
SectionEnd

Section "Uninstall"
Delete /REBOOTOK "$DESKTOP\Combinateur d'évidences.lnk"
Delete /REBOOTOK "$SMPROGRAMS\$StartMenuFolder\Combinateur d'évidences.lnk"
Delete /REBOOTOK "$DESKTOP\${APPNAME}.lnk"
Delete /REBOOTOK "$SMPROGRAMS\$StartMenuFolder\${APPNAME}.lnk"
Delete /REBOOTOK "$SMPROGRAMS\$StartMenuFolder\Désinstaller.lnk"
RMDir /REBOOTOK "$SMPROGRAMS\$StartMenuFolder"
Delete /REBOOTOK "$INSTDIR\${EXE}"
Delete /REBOOTOK "$INSTDIR\${ICON}"
Delete /REBOOTOK "$INSTDIR\*.class"
Delete /REBOOTOK "$INSTDIR\*.jar"
Delete /REBOOTOK "$INSTDIR\logo.png"
Delete /REBOOTOK "$INSTDIR\logo.ico"
Delete /REBOOTOK "$INSTDIR\splash.png"
RMDir /REBOOTOK /r "$INSTDIR\Plugins"
Delete /REBOOTOK "$INSTDIR\${UNINSTALLER}"
RMDir $INSTDIR
DeleteRegKey HKLM "${REG_UNINSTALL}"
SectionEnd