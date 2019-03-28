#!/bin/bash



glassfish_home="/home/......./payara5/bin"


cd $glassfish_home
./asadmin start-database

cd $glassfish_home
./asadmin start-domain frontendmicroservice ;
./asadmin start-domain reportsmicroservice ;
./asadmin start-domain ordersmicroservice ;
./asadmin start-domain storesmicroservice  ;  
./asadmin start-domain productsmicroservice 


echo "All domains started!!!"
