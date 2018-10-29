#!/bin/bash



frontend_settings="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/frontend/settings.xml"
frontend_pom="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/frontend/pom.xml"


 
glassfish_home="/home/nikolaus/Schreibtisch/payara5/bin/"
glassfish_domain="/home/nikolaus/Schreibtisch/payara5/glassfish/domains/"




cd $glassfish_home
./asadmin stop-domain frontendmicroservice ;




cd $glassfish_domain

rm -rf frontendmicroservice/applications/* ;
rm -rf frontendmicroservice/generated/* ;
rm -rf frontendmicroservice/osgi-cache/* ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Osgi-cache deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

cd $glassfish_home
./asadmin start-domain frontendmicroservice ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Domains Started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

 mvn -s $frontend_settings clean post-clean -f $frontend_pom ;


 echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Cleaning done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


./asadmin undeploy frontend-service-ear --port 8548 ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Undeployment done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


mvn -s $frontend_settings install -f $frontend_pom ;

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!install done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

echo "Redeployment successful if mvn build was successfull!!!!!!"
