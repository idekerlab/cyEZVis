package org.cytoscape.calvr.internal;


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
		this.converter = new NetworkViewConverter();
	}

	public void run(final TaskMonitor taskMonitor) throws Exception {
		// Give the task a title.
		taskMonitor.setTitle("Sending data to CalVR server...");
		
		converter.send();
	}
}
