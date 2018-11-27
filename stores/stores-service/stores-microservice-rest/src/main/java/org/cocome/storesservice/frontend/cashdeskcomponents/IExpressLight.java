package org.cocome.storesservice.frontend.cashdeskcomponents;

public interface IExpressLight {
	
	
	public void updateExpressLight(boolean value);
	public ExpressLightStates getExpressLight();
	public void resetExpressLight();
	public boolean isInExpressMode();
}
