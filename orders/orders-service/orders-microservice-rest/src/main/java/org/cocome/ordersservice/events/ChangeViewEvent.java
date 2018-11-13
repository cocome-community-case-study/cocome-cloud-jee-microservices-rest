package org.cocome.ordersservice.events;

import org.cocome.ordersservice.navigation.NavigationView;

/**
 * Represents a requested change of view
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class ChangeViewEvent {
	private NavigationView newViewState;

	public ChangeViewEvent(NavigationView newViewState) {
		this.newViewState = newViewState;
	}
	
	public NavigationView getNewViewState() {
		return newViewState;
	}

	public void setNewViewState(NavigationView newViewState) {
		this.newViewState = newViewState;
	}

}
