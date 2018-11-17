package org.cocome.productsservice.productquery;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.domain.ProductSupplier;
import org.cocome.productsservice.repository.ProductRepository;
import org.cocome.productsservice.repository.ProductSupplierRepository;

/**
 * This class abstracts the low functional {@link ProductRepository} and
 * provides some higher functionality for storing and retrieving products. <br>
 * It is Stateless, as no session specific Data is stored.
 * 
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
@Stateless
public class ProductQuery implements IProductQuery {

	@EJB
	private ProductRepository productRepo;

	@EJB
	private ProductSupplierRepository supplierRepo;

	private Logger LOG = Logger.getLogger(ProductQuery.class);
	private final long COULD_NOT_CREATE_ENTITY = -1;

	@Override
	public Product findProductByid(long id) {
		LOG.debug("QUERY: Retrieving Product from Database with Id: " + id);
		Product product = productRepo.find(id);
		if (product != null) {
			LOG.debug("QUERY: Successfully found product with Id: " + id);

			return product;
		}
		LOG.debug("QUERY: Did not find product with Id: " + id);
		return null;
	}

	@Override
	public Product findProductByBarcode(long barcode) {
		// TODO: Further improvement --> Do SQL statement in Repo
		LOG.debug("QUERY: Retrieving Product from Database with Barcode: " + barcode);
		Collection<Product> products = productRepo.all();

		for (Product product : products) {

			if (product.getBarcode() == barcode) {
				LOG.debug("QUERY: Successfully found product with barcode: " + barcode);
				return product;
			}
		}

		LOG.debug("QUERY: Did not find Product with barcode: " + barcode);
		return null;
	}

	@Override
	public Collection<Product> getAllProducts() {
		Collection<Product> products = productRepo.all();

		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Retrieving ALL Products from database with following [name, Id, supplierName]: ");

		for (Product product : products) {
			sb.append("[ " + product.getName() + " ," + product.getId() +  " ," + product.getSupplier().getName() + " ]");
		}
		LOG.debug(sb.toString());
		return products;
	}

	@Override
	public Collection<Product> getProductsBySupplier(long supplierId) {
		Collection<Product> products = productRepo.all();

		// remove all Products that do not belong to this supplier
		products.removeIf(product -> product.getSupplier().getId() != supplierId);

		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Retrieving following Products from supplier with id:  " + supplierId
				+ "from database [name, Id]: ");

		for (Product product : products) {
			sb.append("[ " + product.getName() + " ," + product.getId() + " ]");
		}
		LOG.debug(sb.toString());
		return products;
	}

	@Override
	public long createProduct(String name, long barcode, double purchasePrice, long supplierId) {
		/*
		 * find corresponding supplier. If no supplier found, product cannot be
		 * created as it belongs to exactly one enterprise
		 */
		ProductSupplier supplier = supplierRepo.find(supplierId);
		if (supplier == null) {
			LOG.error("QUERY: Could not create Product with name:  " + name + ", barcode: " + barcode
					+ " from supplier with Id:  " + supplierId + ".  Supplier not found!");
			return COULD_NOT_CREATE_ENTITY;

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
		Long productId = productRepo.create(product);
		if (productId == COULD_NOT_CREATE_ENTITY) {
			LOG.error("QUERY: Error while creating Product with id: "+ productId + " name: " + name + ", barcode: " + barcode
					+ " and supplier with Id:  " + supplierId);
			return COULD_NOT_CREATE_ENTITY;
		}
		LOG.debug("QUERY: Successfully created Product with id: " + productId + " name: " + name + ", barcode: " + barcode
				+ " and supplier with Id:  " + supplierId);

		/*
		 * Updating enterprise automatically done by database but still doing it for
		 * security reason
		 * 
		 * @see AutomatiChangeTracking
		 */
		supplier.addProduct(product);
		supplierRepo.update(supplier);
        
        
		return productId;

	}

	@Override
	public boolean updateProduct(@NotNull long id, @NotNull String name, @NotNull double purchasePrice, @NotNull long barcode) {
		LOG.debug("QUERY: Trying to update Product with name: " + name + "and Id: " + id);
		
		Product product = productRepo.find(id);
		if(product== null) {
			LOG.debug("QUERY: Could not update Product with id: " + id+ ". Product not found");
			return false;
		}

		product.setName(name);
		product.setBarcode(barcode);
		product.setPurchasePrice(purchasePrice);
		
		if(productRepo.update(product) == null) {
			LOG.error("QUERY: Could not update Product with name: " + product.getName() + "and Id: " + product.getId());
			return false;
		}
		LOG.debug("QUERY: Successfully updated Product with name: " + product.getName() + "and Id: " + product.getId());
		return true;
	}
	
	@Override
	public boolean deleteProduct(@NotNull long id) {
		LOG.debug("QUERY: Deleting Product from Database with Id: " + id);
		
		if (productRepo.delete(id)) {
			LOG.debug("QUERY: Successfully deleted Product with Id: " + id);
			return true;
		}
		LOG.debug("QUERY: Did not find Product with Id: " + id);
		return false;
	}

}
