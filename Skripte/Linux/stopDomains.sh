#!/bin/bash




glassfish_home="/home/......./payara5/bin"

cd $glassfish_home
./asadmin stop-domain frontendmicroservice ;
./asadmin stop-domain reportsmicroservice ;
./asadmin stop-domain ordersmicroservice ;
./asadmin stop-domain storesmicroservice  ;  
./asadmin stop-domain productsmicroservice 

echo All domains stopped!
pause