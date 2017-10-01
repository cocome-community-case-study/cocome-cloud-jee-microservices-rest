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

## Deployment

For deployment instructions please refer to the README files of the corresponding services (in the corresponding sub directories).