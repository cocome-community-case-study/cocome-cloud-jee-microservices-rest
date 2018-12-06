#!/bin/bash





orders_settings="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/orders/orders-service/settings.xml"
orders_pom="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/orders/orders-service/pom.xml"

glassfish_home="/home/nikolaus/Schreibtisch/payara5/bin/"
glassfish_domain="/home/nikolaus/Schreibtisch/payara5/glassfish/domains/"




cd $glassfish_home

./asadmin stop-domain ordersmicroservice  ;  




cd $glassfish_domain



rm -rf ordersmicroservice/applications/* ;
rm -rf ordersmicroservice/generated/* ;
rm -rf ordermicroservice/osgi-cache/* ;



echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Osgi-cache deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

cd $glassfish_home

./asadmin start-domain ordersmicroservice  ;  


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Domains Started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

 mvn -s $orders_settings clean post-clean -f $orders_pom ;

 echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Cleaning done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


./asadmin undeploy orders-microservice-ear --port 8748 ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Undeployment done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


mvn -s $orders_settings clean install -f $orders_pom ;


echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!install done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

echo "Redeployment successful if mvn build was successfull!!!!!!"
