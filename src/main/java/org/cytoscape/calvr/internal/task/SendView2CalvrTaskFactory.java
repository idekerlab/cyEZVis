package org.cytoscape.calvr.internal.task;

import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class SendView2CalvrTaskFactory extends AbstractNetworkViewTaskFactory {

	public TaskIterator createTaskIterator(final CyNetworkView view) {
		return new TaskIterator(new SendView2CalvrTask(view));
	}
}