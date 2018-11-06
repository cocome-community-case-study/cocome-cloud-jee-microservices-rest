package org.cocome.storesservice.events;

import org.cocome.storesservice.navigation.NavigationView;

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
