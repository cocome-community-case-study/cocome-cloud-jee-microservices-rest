#!/bin/bash





 products_settings="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/products/products-service/settings.xml"
 products_pom="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/products/products-service/pom.xml"

glassfish_home="/home/nikolaus/Schreibtisch/payara5/bin/"
glassfish_domain="/home/nikolaus/Schreibtisch/payara5/glassfish/domains/"




cd $glassfish_home

./asadmin stop-domain productsmicroservice  ;  




cd $glassfish_domain



rm -rf storesmicroservice/applications/* ;
rm -rf storesmicroservice/generated/* ;
rm -rf storesmicroservice/osgi-cache/* ;



echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Osgi-cache deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

cd $glassfish_home

./asadmin start-domain productsmicroservice  ;  


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Domains Started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

 mvn -s $products_settings clean post-clean -f $products_pom ;

 echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Cleaning done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


./asadmin undeploy products-microservice-ear --port 8948 ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Undeployment done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


mvn -s $products_settings install -f $products_pom ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!install done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

echo "Redeployment successful if mvn build was successfull!!!!!!"
