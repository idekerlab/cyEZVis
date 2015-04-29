package org.cytoscape.calvr.internal.task;

import java.util.Properties;

import org.cytoscape.property.CyProperty;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class ClearViewTaskFacgtory extends AbstractTaskFactory {

	private final CyProperty<Properties> props;

	public ClearViewTaskFacgtory(CyProperty<Properties> props) {
		this.props = props;
	}

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new ClearViewTask(props));
	}
}
