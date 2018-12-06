package org.cocome.reportservice.user;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = DummyPermission.class)
public interface IPermission {
	
	
	public String getName();
}
