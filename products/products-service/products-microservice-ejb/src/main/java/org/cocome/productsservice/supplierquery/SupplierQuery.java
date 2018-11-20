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

	@Override
	public long createSupplier(String name) throws CreateException {
		ProductSupplier entity = new ProductSupplier();
		entity.setName(name);

		LOG.debug("QUERY: Try to create Supplier with name " + entity.getName());
		Long supplierId = supplierRepo.create(entity);
		if (supplierId != COULD_NOT_CREATE_ENTITY) {
			LOG.debug("QUERY: sucessfully create Supplier with name " + entity.getName() + " and id: " + supplierId);
			return supplierId;
		} else {
			LOG.error("QUERY: Could not create Supplier with name " + entity.getName() + " and id: " + supplierId);
			throw new CreateException(
					"QUERY: Could not create Supplier with name " + entity.getName() + " and id: " + supplierId);

		}
	}

	@Override
	public ProductSupplier findSupplierById(long supplierId) throws QueryException {
		LOG.debug("QUERY: Retrieving Supplier from Database with Id: " + supplierId);
		ProductSupplier supplier = supplierRepo.find(supplierId);
		if (supplier == null) {

			LOG.debug("QUERY: Did not find supplier with Id: " + supplierId);
			throw new QueryException("QUERY: Did not find supplier with Id: " + supplierId);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Successfully found supplier with Id: " + supplierId
				+ " and with Products [id, name, barcode]: ");
		for (Product product : supplier.getProducts()) {
			sb.append("[" + product.getId() + ", " + product.getName() + " , " + product.getBarcode() + "]");
		}

		LOG.debug(sb.toString());

		return supplier;

	}

	@Override
	public Collection<ProductSupplier> getAllSuppliers() {
		Collection<ProductSupplier> suppliers = supplierRepo.all();

		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Retrieving ALL Suppliers from database with following [name, Id]: ");

		for (ProductSupplier supplier : suppliers) {
			sb.append("[ " + supplier.getName() + " ," + supplier.getId() + " ]");
		}
		LOG.debug(sb.toString());
		return suppliers;
	}

	@Override
	public void updateSupplier(ProductSupplier supplier) throws QueryException {
		LOG.debug("QUERY: Trying to update Supplier with name: " + supplier.getName() + "and Id: " + supplier.getId());

		ProductSupplier supplierUpdate = supplierRepo.update(supplier);

		if (supplierUpdate == null) {
			LOG.error("QUERY: Could not update Supplier with name: " + supplier.getName() + "and Id: "
					+ supplier.getId());
			throw new QueryException(
					"Could not update Supplier with name: " + supplier.getName() + "and Id: " + supplier.getId());
		}
		LOG.debug(
				"QUERY: Successfully updated Product with name: " + supplier.getName() + "and Id: " + supplier.getId());

	}

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
