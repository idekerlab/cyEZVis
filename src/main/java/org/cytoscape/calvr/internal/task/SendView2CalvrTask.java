package org.cytoscape.calvr.internal.task;


import java.io.IOException;

import org.cytoscape.calvr.internal.NetworkViewConverter;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

public class SendView2CalvrTask extends AbstractNetworkViewTask {

	private final NetworkViewConverter converter;


	public SendView2CalvrTask(final CyNetworkView view) {
		super(view);
		try {
			this.converter = new NetworkViewConverter();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Could not create converter.", e);
		}
	}

	public void run(final TaskMonitor taskMonitor) throws Exception {
		// Give the task a title.
		taskMonitor.setTitle("Sending data to CalVR server...");
		
		converter.send(view);
		
		taskMonitor.setProgress(1.0);
	}
}