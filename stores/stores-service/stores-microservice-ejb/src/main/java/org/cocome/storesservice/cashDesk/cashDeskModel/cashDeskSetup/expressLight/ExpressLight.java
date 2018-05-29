package org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.expressLight;

public class ExpressLight implements IExpressLight{
	private ExpressLightStates expressLight;
	
	public ExpressLight() {
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

	public ExpressLightStates getExpressLight() {
		return expressLight;
	}

	
	
	
	
	
}
