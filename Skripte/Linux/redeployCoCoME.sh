#!/bin/bash



frontend_settings="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/frontend/settings.xml"
frontend_pom="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/frontend/pom.xml"


 orders_settings="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/orders/orders-service/settings.xml"
orders_pom="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/orders/orders-service/pom.xml"

 products_settings="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/products/products-service/settings.xml"
 products_pom="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/products/products-service/pom.xml"

 reports_settings="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/reports/reports-service/settings.xml"
 reports_pom="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/reports/reports-service/pom.xml"

 stores_settings="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/stores/stores-service/settings.xml"
 stores_pom="/home/nikolaus/Schreibtisch/cocome-cloud-jee-microservices-rest/stores/stores-service/pom.xml"

glassfish_home="/home/nikolaus/Schreibtisch/payara5/bin/"
glassfish_domain="/home/nikolaus/Schreibtisch/payara5/glassfish/domains/"




cd $glassfish_home
./asadmin stop-domain frontendmicroservice ;
./asadmin stop-domain reportsmicroservice ;
./asadmin stop-domain ordersmicroservice ;
./asadmin stop-domain storesmicroservice  ;  
./asadmin stop-domain productsmicroservice; 



cd $glassfish_domain

rm -rf frontendmicroservice/applications/* ;
rm -rf frontendmicroservice/generated/* ;
rm -rf frontendmicroservice/osgi-cache/* ;


rm -rf reportsmicroservice/applications/* ;
rm -rf reportsmicroservice/generated/* ;
rm -rf reportsmicroservice/osgi-cache/* ;

rm -rf ordersmicroservice/applications/* ;
rm -rf ordersmicroservice/generated/* ;
rm -rf ordersmicroservice/osgi-cache/* ;


rm -rf storesmicroservice/applications/* ;
rm -rf storesmicroservice/generated/* ;
rm -rf storesmicroservice/osgi-cache/* ;

rm -rf productsmicroservice/applications/* ;
rm -rf productsmicroservice/generated/* ;
rm -rf productsmicroservice/osgi-cache/* ;

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Osgi-cache deleted!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

cd $glassfish_home
./asadmin start-domain frontendmicroservice ;
./asadmin start-domain reportsmicroservice ;
./asadmin start-domain ordersmicroservice ;
./asadmin start-domain storesmicroservice  ;  
./asadmin start-domain productsmicroservice ;

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Domains Started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

 mvn -s $frontend_settings clean post-clean -f $frontend_pom ;
 mvn -s $orders_settings clean post-clean -f $orders_pom ;
 mvn -s $products_settings clean post-clean -f $products_pom ;
 mvn -s $reports_settings clean post-clean -f $reports_pom ;
 mvn -s $stores_settings clean post-clean -f $stores_pom ;

 echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Cleaning done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"


./asadmin undeploy frontend-service-ear --port 8548 ;
./asadmin undeploy reports-microservice-ear --port 8648 ;
./asadmin undeploy orders-microservice-ear --port 8748 ;
./asadmin undeploy stores-microservice-ear --port 8848 ;
./asadmin undeploy products-microservice-ear --port 8948 ;

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Undeployment done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

mvn -s $products_settings install -f $products_pom ;
mvn -s $stores_settings install -f $stores_pom ;
mvn -s $reports_settings install -f $reports_pom ;
mvn -s $orders_settings install -f $orders_pom ;
mvn -s $frontend_settings install -f $frontend_pom ;

echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!install done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

echo "Redeployment successful if mvn build was successfull!!!!!!"
