package org.cytoscape.calvr.internal.task;

import java.util.Properties;

import org.cytoscape.property.CyProperty;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class SendView2CalvrTaskFactory extends AbstractNetworkViewTaskFactory {

	private final CyProperty<Properties> props;
	
	public SendView2CalvrTaskFactory(final CyProperty<Properties> props) {
		this.props = props;
	}
	
	public TaskIterator createTaskIterator(final CyNetworkView view) {
		return new TaskIterator(new SendView2CalvrTask(view, props));
	}
}