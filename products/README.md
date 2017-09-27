Products Service
================

## Deployment Setup

**Prerequisites**: Java8, Maven and Glassfish installed and ready to use.

Before the first deployment, the domain for the jee application (which is the products microservice) has to be created.


```bash
asadmin create-domain --portbase 8900 productsmicroservice
```

After that, a JBCD resource has to be created.

```bash
asadmin create-jdbc-resource --connectionpoolid DerbyPool --host localhost --port 8948  jdbc/CoCoMEProductsServiceDB
```

### Deploying into Glassfish

Start the database and domain first.

```bash
asadmin start-database & asadmin start-domain productsmicroservice
```

Then use maven to compile the sources, build the deployment package and deploy to glassfish.

```bash
mvn -s settings.xml install
```

Undeploying works using maven as well.

```bash
mvn -s settings.xml clean post-clean
```
