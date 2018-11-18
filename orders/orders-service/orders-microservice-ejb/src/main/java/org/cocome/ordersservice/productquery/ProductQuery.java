package org.cocome.ordersservice.productquery;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.cocome.ordersservice.orderquery.OrderQuery;
import org.cocome.productsclient.client.ProductClient;
import org.cocome.productsclient.domain.ProductTO;

@Local
@Stateless
public class ProductQuery implements IProductQuery, Serializable{
	
	
	private static final long serialVersionUID = -1059453262176900085L;
	private Logger LOG = Logger.getLogger(ProductQuery.class);
	
	/*
	 * Rest Client
	 */
	private ProductClient client = new ProductClient();
    
	/**
	 * Use Rest-Client to retrieve all available Products
	 */
	@Override
	public Collection<ProductTO> getAllProducts() {
		LOG.debug("QUERY: Rest call to Productsservice to retrieve all Products");
		
		//TODO Error Handling
		Collection<ProductTO> products = client.findAll();
		
		
		return products;
	}

}
