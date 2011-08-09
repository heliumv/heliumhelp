@echo off
set filename=ihvz_index
title %filename% - Compile~Run~Delete
%JAVA_HOME%\bin\javac %filename%.java
java %filename%
pause
del %filename%.class
