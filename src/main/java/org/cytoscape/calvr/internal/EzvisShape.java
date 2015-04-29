package org.cytoscape.calvr.internal;

/**
 * Shapes supported in EZVis
 */
public enum EzvisShape {
	Circle("circle"), Rectangle("rectangle");
	
	private final String name;
	
	private EzvisShape(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
