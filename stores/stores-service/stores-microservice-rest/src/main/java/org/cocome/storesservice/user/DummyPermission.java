package org.cocome.storesservice.user;

import org.apache.log4j.Logger;

public class DummyPermission implements IPermission {

	private static final Logger LOG = Logger.getLogger(DummyPermission.class);
	
	private String name;
	
	/**
	 * Default constructor for JSON Serialization. DO NOT USE!
	 */
	public DummyPermission() {
		
	}
	
	public DummyPermission(String name) {
		this.name = name.toLowerCase();
	}
	
	@Override
	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object e) {
		if (e instanceof IPermission) {
			LOG.debug("Checking permission equals: " + ((IPermission) e).getName());
			return ((IPermission) e).getName().toLowerCase().equals(name);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "[Permission: " + name + "]";
	}
}
