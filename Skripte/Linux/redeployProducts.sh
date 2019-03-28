#!/bin/bash





 products_settings="/home/......./cocome-cloud-jee-microservices-rest/products/products-service/settings.xml"
 products_pom="/home/......./cocome-cloud-jee-microservices-rest/products/products-service/pom.xml"

glassfish_home="/home/......./payara5/bin/"
glassfish_domain="/home/......./payara5/glassfish/domains/"




cd $glassfish_home

./asadmin stop-domain productsmicroservice  ;  




cd $glassfish_domain



rm -rf productsmicroservice/applications/* ;
rm -rf productsmicroservice/generated/* ;
rm -rf productsmicroservice/osgi-cache/* ;



echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Osgi-cache deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

cd $glassfish_home

./asadmin start-domain productsmicroservice  ;  


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Domains Started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

 mvn -s $products_settings clean post-clean -f $products_pom ;

 echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Cleaning done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


./asadmin undeploy products-microservice-ear --port 8948 ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Undeployment done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


mvn -s $products_settings clean install -f $products_pom ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!install done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

echo "Redeployment successful if mvn build was successfull!!!!!!"
