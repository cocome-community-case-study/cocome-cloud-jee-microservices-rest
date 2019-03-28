#!/bin/bash



frontend_settings="/home/......./cocome-cloud-jee-microservices-rest/frontend/settings.xml"
frontend_pom="/home/......./cocome-cloud-jee-microservices-rest/frontend/pom.xml"

reports_settings="/home/......./cocome-cloud-jee-microservices-rest/reports/reports-service/settings.xml"
 reports_pom="/home/......./cocome-cloud-jee-microservices-rest/reports/reports-service/pom.xml"



 stores_settings="/home/......./cocome-cloud-jee-microservices-rest/stores/stores-service/settings.xml"
 stores_pom="/home/......./cocome-cloud-jee-microservices-rest/stores/stores-service/pom.xml"

 orders_settings="/home/......./cocome-cloud-jee-microservices-rest/orders/orders-service/settings.xml"
orders_pom="/home/......./cocome-cloud-jee-microservices-rest/orders/orders-service/pom.xml"


 products_settings="/......./cocome-cloud-jee-microservices-rest/products/products-service/settings.xml"
 products_pom="/home/......./cocome-cloud-jee-microservices-rest/products/products-service/pom.xml"



 
glassfish_home="/home/......./payara5/bin/"
glassfish_domain="/home/......./payara5/glassfish/domains/"




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


mvn -s $frontend_settings clean install -f $frontend_pom ;

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!install done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

echo "Redeployment successful if mvn build was successfull!!!!!!"

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




