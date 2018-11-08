package org.cocome.storesservice.frontend.enterprise;

import org.cocome.storesservice.domain.TradingEnterprise;

public class EnterpriseViewData {
	
	private long id;
	private String name;
	
	public EnterpriseViewData() {
		id = 0;
		name = "N/A";
	}
	
	public EnterpriseViewData(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static EnterpriseViewData fromTradingEnterprise(TradingEnterprise enterprise) {
		return new EnterpriseViewData(enterprise.getId(), enterprise.getName());
	}

}
