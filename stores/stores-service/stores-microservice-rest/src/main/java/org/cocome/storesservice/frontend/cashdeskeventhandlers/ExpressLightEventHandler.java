package org.cocome.storesservice.frontend.cashdeskeventhandlers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.events.EnableExpressModeEvent;
import org.cocome.storesservice.frontend.cashdeskcomponents.IExpressLight;

/**
 * Component that controls ExpressLight in case an event is emitted which is determined for ExpressLight
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */

@SessionScoped
@Named
public class ExpressLightEventHandler implements Serializable {

	private static final long serialVersionUID = 5833827131877822516L;
	private static final Logger LOG = Logger.getLogger(ExpressLightEventHandler.class);
	
	@Inject
	IExpressLight expressLight;
	
	public void onEvent(@Observes EnableExpressModeEvent event) {
		LOG.debug("FRONTEND: Enable Express Light");
		this.expressLight.updateExpressLight(true);
	}

}
