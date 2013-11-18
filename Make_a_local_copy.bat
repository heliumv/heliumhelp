rem dieser Batch kopiert die Daten von d: auf c: und richtet sie so her, dass anschließend
rem das hv_help.prg gestartet werden kann und daran anschließend das jHelpBuilder
rem Daran denken, dass der hv_help nur hinzufügt, das bedeutet, dass wenn Dateien entfernt werden, diese im map.jhm per Hand entfernt werden müssen.

rem die .# Dateien sollten vorher aus dem Source entfernt werden

rem die Pfade sind fix festgelegt
rem 2013-11-16 geändert auf w: wegen VM

echo "kopieren des Source"
w:
cd \heliumhelp\jsp\
c:
cd \jbproject\heliumhelp\jsp\
xcopy w:*.* /v/s/y
pause

echo "Entfernen aller nicht benötigten Dateien und Ordner"
c:
cd \jbproject\heliumhelp\jsp\heliumhelp\
call w:\heliumhelp\DelDir.bat _vti_cnf\
call w:\heliumhelp\DelDir.bat _vti_pvt\
call w:\heliumhelp\DelDir.bat CVS\
call w:\heliumhelp\DelDir.bat _derived\
call w:\heliumhelp\DelDir.bat backup\
del .cvsignore /s
del Thumbs.db /s
del *.bak /s
del *.old /s
del *.cdr /s
del backup_of*.cdr /s
del Sicherungskopie_von_*.cdr /s
del *.dia /s
del *.java /s
del _achtung.txt /s
del javamenu.htm /s

pause

echo "Map ergänzen"
c:
cd \jbproject\heliumhelp\jsp\heliumhelp\
del map.bak
hv_help map.jhm

echo "JHelpbuilder starten und auch die SearchDB erzeugen und mit der Vorschau testen inkl. Suche nach Zeiterfassung, Fertigung"
pause

