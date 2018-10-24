#!/bin/bash



#--------------------------------------------------------------------------------------------------------------------------------------

cocome_adapter_settings="/home/cocome/Desktop/CoCoMEXPPU/cocome-cloud-jee-service-adapter-xppu_integration/settings.xml"
cocome_adapter_pom="/home/cocome/Desktop/CoCoMEXPPU/cocome-cloud-jee-service-adapter-xppu_integration/pom.xml"

cocome_settings="/home/cocome/Desktop/CoCoMEXPPU/cocome-cloud-jee-platform-migration-xppu_integration/cocome-maven-project/settings.xml"
cocome_pom="/home/cocome/Desktop/CoCoMEXPPU/cocome-cloud-jee-platform-migration-xppu_integration/cocome-maven-project/pom.xml"

glassfish_home="/opt/payara/bin"
glassfish_domain="/opt/payara/glassfish/domains"



cocome_cli="/home/cocome/Desktop/CoCoMEXPPU/cocome-cloud-jee-platform-migration-xppu_integration/cocome-maven-project/cloud-cli-frontend/"


#--------------------------------------------------------------------------------------------------------------------------------------


#------------------------------------(re-)deploy adapter------------------------------------------------------------------------------
cd $glassfish_home

#start to execute clean --> this will wipe out the database
./asadmin start-domain adapter ; 

mvn -s $cocome_adapter_settings clean post-clean -f $cocome_adapter_pom ;

./asadmin stop-domain adapter ; 

cd $glassfish_domain

#wipe out old data
rm -rf adapter\applications\* ;
rm -rf adapter\generated\* ;
rm -rf adapter\osgi-cache\* ;

cd $glassfish_home
 
./asadmin start-domain adapter ; 
#undeploy if adapter is still in cache
./asadmin undeploy service-adapter-ear --port 8248 ;
#deploy 
mvn -s $cocome_adapter_settings install -f $cocome_adapter_pom -DskipTests

echo "(re-)deployment adapter finished"



#------------------------------------(re-deploy cocome)------------------------------------------------------------------------------
#stop domains if there are still running
cd $glassfish_home
./asadmin stop-domain registry ; 
./asadmin stop-domain web ; 
./asadmin stop-domain store ; 
./asadmin stop-domain enterprise ; 
./asadmin stop-domain plant ; 



cd $glassfish_domain
#wipe out old data
rm -rf enterprise\applications\* ;
rm -rf enterprise\generated\* ;
rm -rf enterprise\osgi-cache\* ;

rm -rf registry\applications\* ;
rm -rf registry\generated\* ;
rm -rf registry\osgi-cache\* ;

rm -rf store\applications\* ;
rm -rf store\generated\* ;
rm -rf store\osgi-cache\* ;

rm -rf web\applications\* ;
rm -rf web\generated\* ;
rm -rf web\osgi-cache\* ;

rm -rf plant\applications\* ;
rm -rf plant\generated\* ;
rm -rf plant\osgi-cache\* ;


cd $glassfish_home
./asadmin start-domain registry ; 
./asadmin start-domain web ; 
./asadmin start-domain store ; 
./asadmin start-domain enterprise ; 
./asadmin start-domain plant ; 

#undeploy if jar-files are still in glassfish cache
./asadmin undeploy store-logic-ear --port 8148 ;
./asadmin undeploy cloud-registry-service --port 8448 ;
./asadmin undeploy enterprise-logic-ear --port 8348 ;
./asadmin undeploy plant-logic-ear --port 8548 ;
./asadmin undeploy cloud-web-frontend --port 8048 ;

#build and deploy cocome
mvn -s $cocome_settings install -f $cocome_pom -DskipTests

::------------------------------------fill database------------------------------------------------------------------------------
cd $cocome_cli
mvn clean install
mvn exec:java -f $cocome_cli
echo "database filled"

echo Redeployment successful if mvn build was successfull!!!!!!
pause
