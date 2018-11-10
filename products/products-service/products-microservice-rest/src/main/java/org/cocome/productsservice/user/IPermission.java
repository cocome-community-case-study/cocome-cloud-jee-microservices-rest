package org.cocome.productsservice.user;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = DummyPermission.class)
public interface IPermission {
	public void setName(String name);
	
	public String getName();
}
