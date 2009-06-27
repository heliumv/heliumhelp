rem muss aus dem Verzeichnis des jhelbuilder gestartet werden
@echo on;
rem old cd C:\jbproject\heliumhelp\jhelpbuilder
rem old java -jar C:\jbproject\heliumhelp\jhelpbuilder\lib\cjh.jar 1>stdout.log 2>stderr.log
java -jar lib\cjh.jar 1>stdout.log 2>stderr.log
