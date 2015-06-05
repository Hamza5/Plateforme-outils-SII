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
#Icon ${ICON}
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
File "*.png"
File "*.ico"
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
Delete "'$DESKTOP\Combinateur d'évidences.lnk'"
Delete "'$SMPROGRAMS\$StartMenuFolder\Combinateur d'évidences.lnk'"
Delete "'$DESKTOP\${APPNAME}.lnk'"
Delete "'$SMPROGRAMS\$StartMenuFolder\${APPNAME}.lnk'"
Delete "$SMPROGRAMS\$StartMenuFolder\Désinstaller.lnk"
RMDir "$SMPROGRAMS\$StartMenuFolder"
Delete "$INSTDIR\${EXE}"
Delete "$INSTDIR\${ICON}"
Delete "$INSTDIR\*.class"
Delete "$INSTDIR\*.jar"
Delete "$INSTDIR\logo.png"
Delete "$INSTDIR\logo.ico"
Delete "$INSTDIR\splash.png"
RMDir /r "$INSTDIR\Plugins"
Delete "$INSTDIR\${UNINSTALLER}"
RMDir $INSTDIR
DeleteRegKey HKLM "${REG_UNINSTALL}"
SectionEnd