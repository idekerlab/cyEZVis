package org.cytoscape.calvr.internal.task;

import java.util.Properties;

import org.cytoscape.calvr.internal.PacketSender;
import org.cytoscape.property.CyProperty;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class ClearViewTask extends AbstractTask {

	private final CyProperty<Properties> props;
	
	public ClearViewTask(final CyProperty<Properties> props) {
		this.props = props;
	}
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		PacketSender sender = new PacketSender(props);
		sender.send("delete all;");
		sender.close();
	}
}
