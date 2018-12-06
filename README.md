RESTful Microservices for CoCoME
================================

This repository contains JEE-based microservice implementations for the CoCoME application.

In the microservice architecture design, four sub domains have been identified to be implemented as seperate services.

* **Orders**: A service to make and track orders of customers.
* **Products**: A service to manage products.
* **Reports**: A service to generate reports.
* **Stores**: A service to manage enterprises and their stores.
* **Frontend**: A service that works as proxy and handles the user Login and the navigation to the other microservices.

The services are designed to use their own relational database each and follow a RESTful architecture style, exposed as an interface via HTTP. The implementations live in the corresponding subdirs of this repository which also contain instructions on how to setup and deploy each service.

## General Structure of the Services

* Each service is built out of three maven projects. (1) `<name>-service-ejb` contains the domain models, the business logic and the persistence layer of the service. (2) `<name>-service-rest` contains the RESTful Web-API of the service, the Micro-Frontend for each Service and the frontend logic. (3) `<name>service-ear` packages the service into a deployable archive.
* Each service has a parent maven project that holds the `*-ejb`, `*-rest` and `*-ear` projects.
* These parent maven projects offer deploying and undeploying of the service via maven.
* Each service contains a subdirectory `fixtures/` with example data XML files and instructions on how to use the service using cURL described in the corresponding `README.md`.
* Each service contains a separate Eclipse project containing contract tests in a subdirectory `<name>-api-tests`. These projects use the corresponding client library for the service to test.



## Development Setup

To import this project into Eclipse, follow these steps.

* *File* -> *Import* -> *Maven* -> *Exising Maven Projects* -> *Click "Next"*
* *Root Directory* -> *Click "Browse"* -> Select the directory of this project
* *Click "Finish"*
* *Repeat this for each project*

## Installation in a nutshell

Each microservice includes a README file with detailed deployment instructions. If you want to set up all of them, follow these steps:

**0. Prerequisites**

* Install Java 8 JDK
* Install the newest Payara (Do not use Glassfish, as it is only tested with Payara)
* Create a PATH variable for payara (to use asadmin commands from each location): Needs to point to ${pathToPayara}/payara5/bin


**1. Create the domains**


```bash
asadmin create-domain --portbase 8800 storesmicroservice
asadmin create-domain --portbase 8900 productsmicroservice
asadmin create-domain --portbase 8700 ordersmicroservice
asadmin create-domain --portbase 8600 reportsmicroservice
asadmin create-domain --portbase 8500 frontendservice
```
Please use the given portbases. If you change them, you also need to change the various settings.xml files. 

**2. Start the domains**


```bash
asadmin start-domain storesmicroservice
asadmin start-domain productsmicroservice
asadmin start-domain ordersmicroservice
asadmin start-domain reportsmicroservice
asadmin start-domain frontendservice
```

**3. Create the databases**

```bash
asadmin create-jdbc-resource --connectionpoolid H2Pool --host localhost --port 8848  jdbc/CoCoMEStoresServiceDB
asadmin create-jdbc-resource --connectionpoolid H2Pool --host localhost --port 8948  jdbc/CoCoMEProductsServiceDB
asadmin create-jdbc-resource --connectionpoolid H2Pool --host localhost --port 8748  jdbc/CoCoMEOrdersServiceDB
```

**4. Start the databases**

```bash
asadmin start-database
```

**5. Build the client libraries**

The client libraries need to be built first, because some of the services depend on them.

```bash
cd stores/stores-client     & mvn install
cd products/products-client & mvn install
cd orders/orders-client     & mvn install
cd reporst/reports-client   & mvn install

```

**6. Build and deploy the services**

The maven commands not only builds deployable packages but also deploys them to their corresponding glassfish/payara domains.
Important: If you used different portbases (as mentioned in `1.Create the domains`),  please adapt the specific settings.xml according to the portbases you used.

```bash
cd frontend  & mvn -s settings.xml install
cd stores/stores-service     & mvn -s settings.xml install
cd products/products-service & mvn -s settings.xml install
cd orders/orders-service     & mvn -s settings.xml install
cd reports/reports-service   & mvn -s settings.xml install
```

**7. Access CoCoME UI and Login**

* Use your browser (tested on Firefox an Chrome):  http://localhost:8580/frontendservice/




| **Role Name**	| **User ID** 	|     **Password**	|  
|---			|---			|---				|	
| Store Manager  	    	|   storemanager	| store   	|   
|     	    Enterprise Manager	| enterprisemanager  	| enterprise  	|   	
|     	    Stock Manager	| stockmanager  	|stock   	|   	
|     	    Cashier	| cashier  	| cashier  	|   
|     	    Admin		|   admin	|   admin	|   
  

* CoCoME has a User-Role-Concept. Each role has a different UI and is allowed to execute different tasks.
* Some roles require a Store ID. You need to know the id of the store you want to manage. It is displayed at the store overview.
* The admin role has all permissions of the roles mentioned.

** 8. Troubleshooting**
* Grey Browser/Localhost denied access: Try if services are up and running via executing the tests: Switch to `<name>-api-tests` and execute `mvn test`. Do not use Eclipse for running tests, as this may cause some troubles.
