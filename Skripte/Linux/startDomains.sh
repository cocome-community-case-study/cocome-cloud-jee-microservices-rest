#!/bin/bash



glassfish_home="/home/nikolaus/Schreibtisch/payara5/bin"

database_home="/home/nikolaus/Schreibtisch/glassfish5/bin"

cd $database_home
./asadmin start-database

cd $glassfish_home
./asadmin start-domain frontendmicroservice ;
./asadmin start-domain reportsmicroservice ;
./asadmin start-domain ordersmicroservice ;
./asadmin start-domain storesmicroservice  ;  
./asadmin start-domain productsmicroservice 


echo "All domains started!!!"
