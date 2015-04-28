package org.cytoscape.calvr.internal;

import java.util.Properties;

import org.cytoscape.calvr.internal.task.SendView2CalvrTaskFactory;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		SendView2CalvrTaskFactory taskFactory = new SendView2CalvrTaskFactory();
		
		Properties properties = new Properties();
		properties.put("title", "Send current view to CalVR System");
		properties.put("preferredMenu", "Apps");
		properties.put("enableFor", "network");

		registerService(context, taskFactory, NetworkViewTaskFactory.class, properties);
	}
}