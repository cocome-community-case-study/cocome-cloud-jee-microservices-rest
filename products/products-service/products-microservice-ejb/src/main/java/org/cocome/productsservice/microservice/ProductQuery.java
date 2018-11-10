package org.cocome.productsservice.microservice;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.domain.ProductSupplier;
import org.cocome.productsservice.repository.ProductRepository;
import org.cocome.productsservice.repository.ProductSupplierRepository;


@Local
@Stateless
public class ProductQuery implements IProductQuery{

	@EJB
	private ProductRepository productRepo;
	
	@EJB
	private ProductSupplierRepository supplierRepo;
	
	private Logger LOG = Logger.getLogger(ProductQuery.class);
	private final long COULD_NOT_CREATE_ENTITY = -1;

	@Override
	public Product findProductByid(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findProductByBarcode(long barcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Product> getProductsBySupplier(long supplierId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createProduct(String name, long barcode, double purchasePrice, long supplierId) {
		/*
		 * find corresponding enterprise. If not enterprise found, store cannot be
		 * created as it belongs to exactly one enterprise
		 */
		ProductSupplier	 supplier = supplierRepo.find(supplierId);
		if (supplier == null) {
			LOG.error("QUERY: Could not create Product with name:  " + name + ", barcode: " + barcode
					+ " from supplier with Id:  " + supplierId + ".  Supplier not found!");
			return false;

		}
		LOG.debug("QUERY: Try to create Product with name: " + name + ", barcode: " + barcode
				+ " from supplier with Id:  " + supplierId);

		// create new Store with enterprise
		Product product = new Product();
		product.setName(name);
		product.setBarcode(barcode);
		product.setPurchasePrice(purchasePrice);
		product.setSupplier(supplier);

		// persist store
		if (productRepo.create(product) == COULD_NOT_CREATE_ENTITY) {
			LOG.error("QUERY: Error while creating Product with name: " + name + ", barcode: " + barcode
					+ " and supplier with Id:  " + supplierId);
			return false;
		}
		LOG.debug("QUERY: Successfully created Product with " + name + ", barcode: " + barcode
				+ " and suppliere with Id:  " + supplierId);

		/*
		 * Updating enterprise automatically done by database
		 * @see AutomatiChangeTracking
		 */
		supplier.addProduct(product);
		// enterpriseRepo.update(enterprise);

		return true;
		
		
	}
	
	
	

	

}
