Ordering Service
================

## Deployment Setup

**Prerequisites**: Java8, Maven and Glassfish installed and ready to use.

Before the first deployment, the domain for the jee application (which is the orders microservice) has to be created.


```bash
asadmin create-domain --portbase 8700 ordersmicroservice
```

After that, a JBCD resource has to be created.

```bash
asadmin create-jdbc-resource --connectionpoolid H2Pool --host localhost --port 8748  jdbc/CoCoMEOrdersServiceDB
```

## RESTful API

URI Schema: `http://{hostname}:8780/ordersmicroservice/rest` + resource path

### Resource: Product Order

| Path | HTTP Operation | Status Code | Response |
| --- | --- | --- | --- |
| /stores/{id}/product-orders | GET | 200 | XML representations of resource list |
| /stores/{id}/product-orders | POST | 201 | URI to resource in `Location` header |
| /product-orders/{id} | GET | 200 | XML representation of resource |
| /product-orders/{id} | PUT | 204 | Empty |
| /product-orders/{id} | DELETE | 204 | Empty |

### Resource: Order Entry

| Path | HTTP Operation | Status Code | Response |
| --- | --- | --- | --- |
| /product-orders/{id}/order-entries | GET | 200 | XML representations of resource list |
| /product-orders/{id}/order-entries | POST | 201 | URI to resource in `Location` header |
| /order-entries/{id} | GET | 200 | XML representation of resource |
| /order-entries/{id} | PUT | 204 | Empty |
| /order-entries/{id} | DELETE | 204 | Empty |

### Deploying into Glassfish

Start the database and domain first.

```bash
asadmin start-database & asadmin start-domain ordersmicroservice
```

Then use maven to compile the sources, build the deployment package and deploy to glassfish.

```bash
mvn -s settings.xml install
```

Undeploying works using maven as well.

```bash
mvn -s settings.xml clean post-clean
```
