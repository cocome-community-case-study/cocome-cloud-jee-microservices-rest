#!/bin/bash


REM !!!!WINDOWS ONLY!!!!!
REM !!!!!!!!!Change the SET Locations to your destination folders/files!!!!!!!!

:: Path to payara for domains
SET glassfish_home=C:\........\payara5\bin

:: Path to "old" glassfish for database
SET database_home=C:\.......\glassfish5\bin


:: I use payara which provides H2 Database. Therefore Glassfish's DerbyDB has to be started with glassfish instance
cd %database_home%
call asadmin start-database & 

cd %glassfish_home%
call asadmin start-domain storesmicroservice & 
call asadmin start-domain productsmicroservice & 
call asadmin start-domain ordersmicroservice & 
call asadmin start-domain reportsmicroservice  &  
call asadmin start-domain frontendservice 


echo All domains started!!!
pause