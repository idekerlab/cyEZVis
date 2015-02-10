package org.cytoscape.calvr.internal;


import java.io.IOException;

import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

public class SampleTask extends AbstractNetworkViewTask {

	@Tunable(description="Message to Mugic:")
	public String numNodesToSelect;
	
	private final NetworkViewConverter converter;


	public SampleTask(final CyNetworkView view) {
		super(view);
		try {
			this.converter = new NetworkViewConverter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException("Could not create converter.", e);
		}
	}

	public void run(final TaskMonitor taskMonitor) throws Exception {
		// Give the task a title.
		taskMonitor.setTitle("Sending data to CalVR server...");
		
		converter.send(view);
	}
}
