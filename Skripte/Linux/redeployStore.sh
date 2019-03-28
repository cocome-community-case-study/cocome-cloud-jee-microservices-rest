#!/bin/bash





 stores_settings="/home/......./cocome-cloud-jee-microservices-rest/stores/stores-service/settings.xml"
 stores_pom="/home/......./cocome-cloud-jee-microservices-rest/stores/stores-service/pom.xml"

glassfish_home="/home/......./payara5/bin/"
glassfish_domain="/home/......./payara5/glassfish/domains/"




cd $glassfish_home

./asadmin stop-domain storesmicroservice  ;  




cd $glassfish_domain



rm -rf storesmicroservice/applications/* ;
rm -rf storesmicroservice/generated/* ;
rm -rf storesmicroservice/osgi-cache/* ;



echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Osgi-cache deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

cd $glassfish_home

./asadmin start-domain storesmicroservice  ;  


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Domains Started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

 mvn -s $stores_settings clean post-clean -f $stores_pom ;

 echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Cleaning done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


./asadmin undeploy stores-microservice-ear --port 8848 ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Undeployment done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


mvn -s $stores_settings clean install -f $stores_pom ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!install done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

echo "Redeployment successful if mvn build was successfull!!!!!!"
