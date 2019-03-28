#!/bin/bash


REM !!!!WINDOWS ONLY!!!!!
REM !!!!!!!!!Change the SET Locations to your destination folders/files!!!!!!!!
SET frontend_settings=C:\.....\cocome-cloud-jee-microservices-rest\frontend\settings.xml
SET frontend_pom=C:\.....\cocome-cloud-jee-microservices-rest\frontend\pom.xml

SET orders_settings=C:\.....\cocome-cloud-jee-microservices-rest\orders\orders-service\settings.xml
SET orders_pom=C:\.....\cocome-cloud-jee-microservices-rest\orders\orders-service\pom.xml

SET products_settings=C:\.....\cocome-cloud-jee-microservices-rest\products\products-service\settings.xml
SET products_pom=C:\.....\cocome-cloud-jee-microservices-rest\products\products-service\pom.xml

SET reports_settings=C:\.....\cocome-cloud-jee-microservices-rest\reports\reports-service\settings.xml
SET reports_pom=C:\.....\cocome-cloud-jee-microservices-rest\reports\reports-service\pom.xml

SET stores_settings=C:\.....\cocome-cloud-jee-microservices-rest\stores\stores-service\settings.xml
SET stores_pom=C:\.....\cocome-cloud-jee-microservices-rest\stores\stores-service\pom.xml

SET glassfish_home=C:\.....\payara5\bin
SET glassfish_domain=C:\.....\payara5\glassfish\domains



cd %glassfish_home%
call asadmin stop-domain storesmicroservice & 
call asadmin stop-domain productsmicroservice & 
call asadmin stop-domain ordersmicroservice & 
call asadmin stop-domain reportsmicroservice  &  
call asadmin stop-domain frontendservice &

cd %glassfish_domain%

del /S /Q .\frontendservice\applications\* &
FOR /D %%p IN (".\frontendservice\applications\*.*") DO rmdir "%%p" /s /q &
del /S /Q .\frontendservice\generated\* &
FOR /D %%p IN (".\frontendservice\generated\*.*") DO rmdir "%%p" /s /q &
del /S /Q .\frontendservice\osgi-cache\* &
FOR /D %%p IN (".\frontendservice\osgi-cache\*.*") DO rmdir "%%p" /s /q &

del /S /Q .\ordersmicroservice\applications\* &
FOR /D %%p IN (".\ordersmicroservice\applications\*.*") DO rmdir "%%p" /s /q &
del /S /Q .\ordersmicroservice\generated\* &
FOR /D %%p IN (".\ordersmicroservice\generated\*.*") DO rmdir "%%p" /s /q &
del /S /Q .\ordersmicroservice\osgi-cache\* &
FOR /D %%p IN (".\ordersmicroservice\osgi-cache\*.*") DO rmdir "%%p" /s /q &

del /S /Q .\storemicroservice\applications\* &
FOR /D %%p IN (".\storemicroservice\applications\*.*") DO rmdir "%%p" /s /q &
del /S /Q .\storemicroservice\generated\* &
FOR /D %%p IN (".\storemicroservice\generated\*.*") DO rmdir "%%p" /s /q &
del /S /Q .\storemicroservice\osgi-cache\* &
FOR /D %%p IN (".\storemicroservice\osgi-cache\*.*") DO rmdir "%%p" /s /q &

del /S /Q .\reportsmicroservice\applications\* &
FOR /D %%p IN (".\reportsmicroservice\applications\*.*") DO rmdir "%%p" /s /q &
del /S /Q .\reportsmicroservice\generated\* &
FOR /D %%p IN (".\reportsmicroservice\generated\*.*") DO rmdir "%%p" /s /q &
del /S /Q .\reportsmicroservice\osgi-cache\* &
FOR /D %%p IN (".\reportsmicroservice\osgi-cache\*.*") DO rmdir "%%p" /s /q &

del /S /Q .\productsmicroservice\applications\* &
FOR /D %%p IN (".\productsmicroservice\applications\*.*") DO rmdir "%%p" /s /q &
del /S /Q .\productsmicroservice\generated\* &
FOR /D %%p IN (".\productsmicroservice\generated\*.*") DO rmdir "%%p" /s /q &
del /S /Q .\productsmicroservice\osgi-cache\* &
FOR /D %%p IN (".\productsmicroservice\osgi-cache\*.*") DO rmdir "%%p" /s /q &

cd %glassfish_home%
call asadmin start-domain storesmicroservice & 
call asadmin start-domain productsmicroservice & 
call asadmin start-domain ordersmicroservice & 
call asadmin start-domain reportsmicroservice  &  
call asadmin start-domain frontendservice &

call mvn -s %frontend_settings% clean post-clean -f %frontend_pom% &
call mvn -s %orders_settings% clean post-clean -f %orders_pom% &
call mvn -s %products_settings% clean post-clean -f %products_pom% &
call mvn -s %reports_settings% clean post-clean -f %reports_pom% &
call mvn -s %stores_settings% clean post-clean -f %stores_pom% &

call asadmin undeploy frontend-service-ear --port 8548 &
call asadmin undeploy reports-microservice-ear --port 8648 &
call asadmin undeploy orders-microservice-ear --port 8748 &
call asadmin undeploy stores-microservice-ear --port 8848 &
call asadmin undeploy products-microservice-ear --port 8948 &

call mvn -s %products_settings% install -f %products_pom% &
call mvn -s %stores_settings% install -f %stores_pom% &
call mvn -s %reports_settings% install -f %reports_pom% &
call mvn -s %orders_settings% install -f %orders_pom% & 
call mvn -s %frontend_settings% install -f %frontend_pom% &





echo Redeployment successful if mvn build was successfull!!!!!!
pause
