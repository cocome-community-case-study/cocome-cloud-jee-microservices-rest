package org.cocome.ordersservice.productquery;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.cocome.productsclient.client.ProductClient;
import org.cocome.productsclient.domain.ProductTO;
import org.cocome.productsclient.exception.ProductsRestException;

/**
 * Separates Product-Queries from toher Queries <br>
 * Interface for Products-REST-Client
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
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
	 * @throws ProductsRestException 
	 */
	@Override
	public Collection<ProductTO> getAllProducts() throws ProductsRestException {
		LOG.debug("QUERY: Rest call to Productsservice to retrieve all Products");
		
		
		Collection<ProductTO> products = client.findAll();
		
		
		return products;
	}

}
