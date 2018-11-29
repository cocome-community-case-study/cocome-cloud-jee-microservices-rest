package org.cocome.frontendservice.events;

/**
 * Emitted when user navigates between different services/frontends
 */
import org.cocome.frontendservice.navigation.NavigationView;

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
