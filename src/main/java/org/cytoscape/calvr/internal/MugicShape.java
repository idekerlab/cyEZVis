package org.cytoscape.calvr.internal;

public enum MugicShape {
	Circle("circle");
	
	private final String name;
	
	private MugicShape(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
