package org.cocome.storesservice.frontend.cashdeskcomponents;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class ExpressLight implements IExpressLight, Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7513310308419516839L;
	private ExpressLightStates expressLight;
	
	public ExpressLight() {
		
	}

	
	@PostConstruct
	private void init() {
		expressLight = ExpressLightStates.Express_LIGHT_BLACK;
	}
	
	@Override
	public void updateExpressLight(boolean value) {
		if(value) {
			expressLight = ExpressLightStates.Express_LIGHT_GREEN;
		} else {
			expressLight = ExpressLightStates.Express_LIGHT_BLACK;
		}
	}
    
	@Override
	public ExpressLightStates getExpressLight() {
		return expressLight;
	}
	
	@Override
	public void resetExpressLight() {
		expressLight = ExpressLightStates.Express_LIGHT_BLACK;
	}
	
	@Override
	public boolean isInExpressMode() {
		return expressLight == ExpressLightStates.Express_LIGHT_GREEN;
	}

	
	
	
	
	
}
