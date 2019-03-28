#!/bin/bash





 reports_settings="/home/......./cocome-cloud-jee-microservices-rest/reports/reports-service/settings.xml"
 reports_pom="/home/......./cocome-cloud-jee-microservices-rest/reports/reports-service/pom.xml"

glassfish_home="/home/......./payara5/bin/"
glassfish_domain="/home/......./payara5/glassfish/domains/"




cd $glassfish_home

./asadmin stop-domain reportsmicroservice  ;  




cd $glassfish_domain



rm -rf reportsmicroservice/applications/* ;
rm -rf reportsmicroservice/generated/* ;
rm -rf reportsmicroservice/osgi-cache/* ;



echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Osgi-cache deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

cd $glassfish_home

./asadmin start-domain reportsmicroservice  ;  


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Domains Started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

 mvn -s $reports_settings clean post-clean -f $reports_pom ;

 echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Cleaning done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


./asadmin undeploy reports-microservice-ear --port 8648 ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Undeployment done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


mvn -s $reports_settings clean install -f $reports_pom ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!install done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

echo "Redeployment successful if mvn build was successfull!!!!!!"
