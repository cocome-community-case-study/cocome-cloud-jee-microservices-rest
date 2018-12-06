Frontend Service
================

This service acts as the interface between the microservices that implement the domain-oriented functionality and the user by prodiving a web-based user interface.

**Note: This service is not implemented yet. Use this project structure for the implementation.**

## Deployment Setup

**Prerequisites**: Java8, Maven and Payara (not Glassfish) installed and ready to use.

Before the first deployment, the domain for the jee application (which is the frontend service) has to be created.


```bash
asadmin create-domain --portbase 8500 frontendservice
```

### Deploying into Glassfish

Start the domain first.

```bash
asadmin start-domain frontendservice
```

Then use maven to compile the sources, build the deployment package and deploy to glassfish.

```bash
mvn -s settings.xml install
```

Undeploying works using maven as well.

```bash
mvn -s settings.xml clean post-clean
```