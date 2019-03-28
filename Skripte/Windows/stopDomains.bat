#!/bin/bash


REM !!!!WINDOWS ONLY!!!!!
REM !!!!!!!!!Change the SET Locations to your destination folders/files!!!!!!!!

SET glassfish_home=C:\.....\payara5\bin

cd %glassfish_home%

call asadmin stop-domain storesmicroservice & 
call asadmin stop-domain productsmicroservice & 
call asadmin stop-domain ordersmicroservice & 
call asadmin stop-domain reportsmicroservice  &  
call asadmin stop-domain frontendservice 

echo All domains stopped!
pause