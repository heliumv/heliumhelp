rem dieser Batch kopiert die Daten von d: auf c: und richtet sie so her, dass anschlie�end
rem das hv_help.prg gestartet werden kann und daran anschlie�end das jHelpBuilder
rem Daran denken, dass der hv_help nur hinzuf�gt, das bedeutet, dass wenn Dateien entfernt werden, diese im map.jhm per Hand entfernt werden m�ssen.

rem die .# Dateien sollten vorher aus dem Source entfernt werden

rem >>>>>>>>>>>>> Es m�ssen alle Dateien lesbar sein, sonst geht der XCOPY nicht <<<<<<<<<<<<<<<<<<<<
rem >>>>>>>>>>>>> Dateiattribute mit Totalcommander setzen <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

rem >>> ACHTUNG: die Roadmap muss auf die Original Verzeichnisse zeigen <<<

rem die Pfade sind fix festgelegt
rem 2013-11-16 ge�ndert auf w: wegen VM
rem 2014-12-27wh

echo "kopieren des Source"
w:
cd \heliumhelp\jsp\
c:
cd \jbproject\heliumhelp\jsp\
rem da anscheinend von Win7 weg der xcopy nicht mehr richtig geht, die Inhalte auf c: ab heliumhelp l�schen
rmdir heliumhelp /S /Q
mkdir heliumhelp
xcopy w:*.* /V/S/Y/D
pause

echo "Entfernen aller nicht ben�tigten Dateien und Ordner"
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

echo "Map erg�nzen"
c:
cd \jbproject\heliumhelp\jsp\heliumhelp\
del map.bak
hv_help map.jhm

echo "JHelpbuilder starten und auch die SearchDB erzeugen und mit der Vorschau testen inkl. Suche nach Zeiterfassung, Fertigung"
pause

