Reports Service
===============

## Deployment Setup

**Prerequisites**: Java8, Maven and Glassfish installed and ready to use.

Before the first deployment, the domain for the jee application (which is the reports microservice) has to be created.


```bash
asadmin create-domain --portbase 8600 reportsmicroservice
```

### Deploying into Glassfish

Start the database and domain first.

```bash
asadmin start-domain reportsmicroservice
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

URI Schema: `http://{hostname}:8680/storesmicroservice/rest` + resource path

### Resource: Report

| Path | HTTP Operation | Status Code | Response | Parameters |
| --- | --- | --- | --- | --- |
| /reports | POST | 200 | XML representation of report | `type=(enterprise-delivery\|store-stock\|enterprise-stock)`, `id` |