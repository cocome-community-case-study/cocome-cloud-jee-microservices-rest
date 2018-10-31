#Progress report

## Implementation state
- The fundamental implementation of CoCoMEs logic is done.
- Implementation for CoCoME UseCases 1-8,  which are part of the first CoCoME design, are implemented and the Documentation can be read in the paper [The CoCoME Platform for Collaborativ Empirical Research on Information System Evolution - Evolution Scenarios in the Second Foundiung Period of SPP 1593](https://github.com/cocome-community-case-study/papers/blob/master/Forschungsbericht/2018-10.pdf)
	- The program for these usecases can be found in section 1-8

## Needed steps
- Frontend
- for connection between the Frontend and the implemented logic, some kind of viewmodel needs to be defined. Therefor, each microservice provides some classes which can serve as connectors to that model.

** These classes are:**
	
 stores-microservice-ejb
> org-cocome.enterpriseservice
>>EnterpriseOrganizer.java*

```
This class can return an enterprise by id, which can in turn return an store by id to navigate to the proper store, cashdesk etc.
```

 reports-microservice-ejb
> reportsservice
>>*Reporter.java*

```
This class return by call with enterpriseId, and storeId either a collection of ordered products or a Collection fo available StockItems
```

 product-microservicce-ejb 
> productservice
>> Suppliermanager.java

```
This class is used top organize the product supplier.
```
>> Productmanager.java

```
This class can be used to organize the in the system overall available Products
```
 orders-microservice-ejb
> org.cocome.ordersservice.microservice
>> OrderManager.java

```
This class can be used to organize Orders 
```
>> OrderEntryManager	
```
This class can be used to organize OrderEntries 
```

## Rest Calls
Right now, the restcalls between the microservices are not tested, as everything else. They are defined on the in the paper [Erweiterung und Wartung einer Cloud-basierten JEE-Architektur](https://github.com/cocome-community-case-study/cocome-cloud-jee-microservices-rest/blob/master/doc/report.pdf) described way and can be found in the following classes:

Order-microservice-ejb
> org.cocome.ordersservice.microservice

>> OrderManager.java

reports-microservice-ejb

>restCommunicator

>>OrderCommunicator.java

>>StoreCommunicator.java

stores-microservice-ejb

>org.cocome.storesservice.storeManager

>>StoreAdminManager.java

Each oif these restcalls ist addressed to the [localhost]() and the int the paper above definded domain. This proably need to be changed if used on a differend way. 

## Differences
Differences to original in the original CoCoME theres only one cashdesk per store provided. Right now, we are managing the cashdesks for each store in an collection, which mean there can be more then one per store. They can be assignd by name as they dont have any sort of key.