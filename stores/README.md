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

## RESTful API

URI Schema: `http://{hostname}:8880/storesmicroservice/rest` + resource path

### Resource: Trading Enterprise

| Path | HTTP Operation | Status Code | Response |
| --- | --- | --- | --- |
| /trading-enterprises | GET | 200 | XML representations of resource list |
| /trading-enterprises | POST | 201 | URI to resource in `Location` header |
| /trading-enterprises/{id} | GET | 200 | XML representation of resource |
| /trading-enterprises/{id} | PUT | 204 | Empty |
| /trading-enterprises/{id} | DELETE | 204 | Empty |

### Resource: Store

| Path | HTTP Operation | Status Code | Response |
| --- | --- | --- | --- |
| /trading-enterprises/{id}/stores | GET | 200 | XML representations of resource list |
| /trading-enterprises/{id}/stores | POST | 201 | URI to resource in `Location` header |
| /stores/{id} | GET | 200 | XML representation of resource |
| /stores/{id} | PUT | 204 | Empty |
| /stores/{id} | DELETE | 204 | Empty |

### Resource: Stock Item

| Path | HTTP Operation | Status Code | Response |
| --- | --- | --- | --- |
| /stores/{id}/stock-items | GET | 200 | XML representations of resource list |
| /stores/{id}/stock-items | POST | 201 | URI to resource in `Location` header |
| /stock-items/{id} | GET | 200 | XML representation of resource |
| /stock-items/{id} | PUT | 204 | Empty |
| /stock-items/{id} | DELETE | 204 | Empty |

## Working with test data

`fixtures/` contains XML files for the service's domain objects. You can use them as test data. For example, use cURL to save data using the service.

```bash
curl -X POST http://localhost:8880/storesmicroservice/rest/trading-enterprises -H "Content-Type: application/xml" -d @fixtures/tradingenterprise/1.xml -v
```

By using the `-v` flag, you will see that the service answers with a *201 Created* status code and the URI to the newly created resource in the `Location` header.

Assuming the service was running and using the ID 1, fetch it using cURL like that:

```bash
curl -X GET http://localhost:8880/storesmicroservice/rest/trading-enterprises/1 -H "Accept: application/xml"
```