package org.cocome.productsservice.supplierquery;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.domain.ProductSupplier;
import org.cocome.productsservice.exceptions.CreateException;
import org.cocome.productsservice.exceptions.QueryException;
import org.cocome.productsservice.repository.ProductRepository;
import org.cocome.productsservice.repository.ProductSupplierRepository;

/**
 * This class abstracts the low functional {@link ProductSupplierRepository} and
 * provides some higher functionality for storing and retrieving suppliers. <br>
 * It is Stateless, as no session specific Data is stored.
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
@Stateless
public class SupplierQuery implements ISupplierQuery {

	@EJB
	private ProductRepository productRepo;

	@EJB
	private ProductSupplierRepository supplierRepo;

	private final long COULD_NOT_CREATE_ENTITY = -1;

	private Logger LOG = Logger.getLogger(SupplierQuery.class);

	/**
	 * Create supplier with given name
	 */
	@Override
	public long createSupplier(String name) throws CreateException {
		LOG.debug("QUERY: Try to create Supplier with name " + name);

		// Create
		ProductSupplier entity = new ProductSupplier();
		entity.setName(name);

		// persists
		Long supplierId = supplierRepo.create(entity);

		// error handling
		if (supplierId != COULD_NOT_CREATE_ENTITY) {
			LOG.debug("QUERY: sucessfully create Supplier with name " + entity.getName() + " and id: " + supplierId);
			return supplierId;
		} else {
			LOG.error("QUERY: Could not create Supplier with name " + entity.getName() + " and id: " + supplierId);
			throw new CreateException(
					"QUERY: Could not create Supplier with name " + entity.getName() + " and id: " + supplierId);

		}
	}

	/**
	 * Find supplier with given id
	 */
	@Override
	public ProductSupplier findSupplierById(long supplierId) throws QueryException {
		LOG.debug("QUERY: Retrieving Supplier from Database with Id: " + supplierId);

		// find supplier
		ProductSupplier supplier = supplierRepo.find(supplierId);

		// error handling
		if (supplier == null) {

			LOG.debug("QUERY: Did not find supplier with Id: " + supplierId);
			throw new QueryException("QUERY: Did not find supplier with Id: " + supplierId);
		}

		// Logging: Log all Products for this supplier
		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Successfully found supplier with Id: " + supplierId
				+ " and with Products [id, name, barcode]: ");
		for (Product product : supplier.getProducts()) {
			sb.append("[" + product.getId() + ", " + product.getName() + " , " + product.getBarcode() + "]");
		}

		LOG.debug(sb.toString());

		return supplier;

	}

	/**
	 * Return all suppliers
	 */
	@Override
	public Collection<ProductSupplier> getAllSuppliers() {
		// find all
		Collection<ProductSupplier> suppliers = supplierRepo.all();

		// Logging
		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Retrieving ALL Suppliers from database with following [name, Id]: ");

		for (ProductSupplier supplier : suppliers) {
			sb.append("[ " + supplier.getName() + " ," + supplier.getId() + " ]");
		}
		LOG.debug(sb.toString());
		return suppliers;
	}

	/**
	 * Update existing supplier. <br>
	 * Only updating name is possible. Products are updated when product itself is
	 * updated
	 */
	@Override
	public void updateSupplier(ProductSupplier supplier) throws QueryException {
		LOG.debug("QUERY: Trying to update Supplier with name: " + supplier.getName() + "and Id: " + supplier.getId());

		// find
		ProductSupplier supplierUpdate = supplierRepo.find(supplier.getId());
		if (supplierUpdate == null) {
			LOG.error("QUERY: Could not update Supplier with name: " + supplier.getName() + "and Id: "
					+ supplier.getId() + ". Supplier not found!");
			throw new QueryException("Could not update Supplier with name: " + supplier.getName() + "and Id: "
					+ supplier.getId() + ". Supplier notfound");
		}

		// update
		supplierUpdate.setName(supplier.getName());

		// persist
		if (supplierRepo.update(supplierUpdate) == null) { // database error
			LOG.error("QUERY: Could not update Supplier with name: " + supplier.getName() + "and Id: "
					+ supplier.getId());
			throw new QueryException(
					"Could not update Supplier with name: " + supplier.getName() + "and Id: " + supplier.getId());
		}

		LOG.debug(
				"QUERY: Successfully updated Product with name: " + supplier.getName() + "and Id: " + supplier.getId());

	}

	/**
	 * Delete supplier with given id
	 */
	@Override
	public void deleteSupplier(long id) throws QueryException {
		LOG.debug("QUERY: Deleting Supplier from Database with Id: " + id);

		if (supplierRepo.delete(id)) {
			LOG.debug("QUERY: Successfully deleted supplier with Id: " + id);

		}
		LOG.debug("QUERY: Did not find supplier with Id: " + id);
		throw new QueryException("Did not find supplier with Id: " + id);

	}

}
