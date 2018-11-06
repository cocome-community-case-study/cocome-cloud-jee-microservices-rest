package org.cocome.storesservice.navigation;

/**
 * Interface for resolving the locale-dependent labels 
 * of buttons in the navigation. 
 * 
 * @author Tobias Pöppke
 * @author Robert Heinrich
 *
 */
public interface ILabelResolver {

	String getLabel(String navOutcome);
}