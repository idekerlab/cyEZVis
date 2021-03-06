package org.cytoscape.calvr.internal.task;


import java.util.Properties;

import org.cytoscape.calvr.internal.NetworkViewConverter;
import org.cytoscape.property.CyProperty;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class SendView2CalvrTask extends AbstractNetworkViewTask {

	private final NetworkViewConverter converter;

	public SendView2CalvrTask(final CyNetworkView view, final CyProperty<Properties> props) {
		super(view);
		this.converter = new NetworkViewConverter(props);
	}

	public void run(final TaskMonitor taskMonitor) throws Exception {
		// Give the task a title.
		taskMonitor.setTitle("Sending data to CalVR server...");
		converter.convert(view);
		taskMonitor.setTitle("View sent to CalVR.");
		taskMonitor.setProgress(1.0);
	}
}