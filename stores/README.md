Stores Service
==============

## Deployment Setup

**Prerequisites**: Java8, Maven and Glassfish installed and ready to use.

Before the first deployment, the domain for the jee application (which is the products microservice) has to be created.


```bash
asadmin create-domain --portbase 8800 storesmicroservice
```

After that, a JBCD resource has to be created.

```bash
asadmin create-jdbc-resource --connectionpoolid DerbyPool --host localhost --port 8848  jdbc/CoCoMEStoresServiceDB
```

### Deploying into Glassfish

Start the database and domain first.

```bash
asadmin start-database & asadmin start-domain storesmicroservice
```

Then use maven to compile the sources, build the deployment package and deploy to glassfish.

```bash
mvn -s settings.xml install
```

Undeploying works using maven as well.

```bash
mvn -s settings.xml clean post-clean
```

## Working with test data

`fixtures/` contains XML files for the service's domain objects. You can use them as test data. For example, use cURL to save data using the service.

```bash
curl -X POST http://localhost:8880/storesmicroservice/rest/trading-enterprises -H "Content-Type: application/xml" -d @fixtures/tradingenterprise/1.xml
```

Assuming the service was running and using the ID 1, fetch it using cURL like that:

```bash
curl -X GET http://localhost:8880/storesmicroservice/rest/trading-enterprises/1 -H "Accept: application/xml"
```