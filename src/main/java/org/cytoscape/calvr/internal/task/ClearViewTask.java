package org.cytoscape.calvr.internal.task;

import org.cytoscape.calvr.internal.PacketSender;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class ClearViewTask extends AbstractTask {

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		PacketSender sender = new PacketSender();
		sender.send("delete all;");
		sender.close();
	}
}
