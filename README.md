RESTful Microservices for CoCoME
================================

This repository contains JEE-based microservice implementations for the CoCoME application.

In the microservice architecture design, four sub domains have been identified to be implemented as seperate services.

* **Orders**: A service to make and track orders of customers.
* **Products**: A service to manage products.
* **Reports**: A service to generate reports.
* **Stores**: A service to manage enterprises and their stores.

The services are designed to use their own relational database each and follow a RESTful architecture style, exposed as an interface via HTTP. The implementations live in the corresponding subdirs of this repository which also contain instructions on how to setup and deploy each service.

## General Structure of the Services

* Each service is built out of three maven projects. (1) `<name>-service-ejb` contains the domain models, the business logic and the persistence layer of the service. (2) `<name>-service-rest` contains the RESTful Web-API of the service. (3) `<name>service-ear` packages the service into a deployable archive.
* Each service has a parent maven project that holds the `*-ejb`, `*-rest` and `*-ear` projects.
* These parent maven projects offer deploying and undeploying of the service via maven.
* Each service contains a subdirectory `fixtures/` with example data XML files and instructions on how to use the service using cURL described in the corresponding `README.md`.

## Development Setup

To import this project into Eclipse, follow these steps.

* *File* -> *Import* -> *Maven* -> *Exising Maven Projects* -> *Click "Next"*
* *Root Directory* -> *Click "Browse"* -> Select the directory of this project
* *Click "Finish"*

## Installation in a nutshell

Each microservice includes a README file with detailed deployment instructions. If you want to set up all of them, follow these steps:

**1. Create the domains**

```bash
asadmin create-domain --portbase 8800 storesmicroservice
asadmin create-domain --portbase 8900 productsmicroservice
asadmin create-domain --portbase 8700 ordersmicroservice
asadmin create-domain --portbase 8600 reportsmicroservice
```

**2. Start the domains**


```bash
asadmin start-domain storesmicroservice
asadmin start-domain productsmicroservice
asadmin start-domain ordersmicroservice
asadmin start-domain reportsmicroservice
```

**3. Create the databases**

```bash
asadmin create-jdbc-resource --connectionpoolid DerbyPool --host localhost --port 8848  jdbc/CoCoMEStoresServiceDB
asadmin create-jdbc-resource --connectionpoolid DerbyPool --host localhost --port 8948  jdbc/CoCoMEProductsServiceDB
asadmin create-jdbc-resource --connectionpoolid DerbyPool --host localhost --port 8748  jdbc/CoCoMEOrdersServiceDB
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

The maven commands not only builds deployable packages but also deploys them to their corresponding glassfish domains.

```bash
cd stores/stores-service     & mvn -s settings.xml install
cd products/products-service & mvn -s settings.xml install
cd orders/orders-service     & mvn -s settings.xml install
cd reports/reports-service   & mvn -s settings.xml install
```