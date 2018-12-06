Products Service
================

## Deployment Setup

**Prerequisites**: Java8, Maven and Payara(not Glassfish) installed and ready to use.

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

## RESTful API

URI Schema: `http://{hostname}:8980/productsmicroservice/rest` + resource path

### Resource: Product Supplier

| Path | HTTP Operation | Status Code | Response |
| --- | --- | --- | --- |
| /trading-enterprises/{id}/product-suppliers | GET | 200 | XML representations of resource list |
| /trading-enterprises/{id}/product-suppliers | POST | 201 | URI to resource in `Location` header |
| /product-suppliers/{id} | GET | 200 | XML representation of resource |
| /product-suppliers/{id} | PUT | 204 | Empty |
| /product-suppliers/{id} | DELETE | 204 | Empty |

### Resource: Product

| Path | HTTP Operation | Status Code | Response |
| --- | --- | --- | --- |
| /product-suppliers/{id}/products | GET | 200 | XML representations of resource list |
| /product-suppliers/{id}/products | POST | 201 | URI to resource in `Location` header |
| /products/{id} | GET | 200 | XML representation of resource |
| /products/{id} | PUT | 204 | Empty |
| /products/{id} | DELETE | 204 | Empty |

## Working with test data

`fixtures/` contains XML files for the service's domain objects. You can use them as test data. For example, use cURL to save data using the service. The following request presumes that there exists a trading enterprise with ID 1.

```bash
curl -X POST http://localhost:8980/productsmicroservice/rest/trading-enterprises/1/product-suppliers -H "Content-Type: application/xml" -d @fixtures/productsupplier/1.xml -v
```

By using the `-v` flag, you will see that the service answers with a *201 Created* status code and the URI to the newly created resource in the `Location` header.

Assuming the service was running and using the ID 1, fetch it using cURL like that:

```bash
url -X GET http://localhost:8980/productsmicroservice/rest/product-suppliers/1 -H "Accept: application/xml"
```