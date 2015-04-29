package org.cytoscape.calvr.internal;

import java.util.Properties;

import org.cytoscape.calvr.internal.task.ClearViewTaskFacgtory;
import org.cytoscape.calvr.internal.task.SendView2CalvrTaskFactory;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.work.TaskFactory;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		SendView2CalvrTaskFactory taskFactory = new SendView2CalvrTaskFactory();
		ClearViewTaskFacgtory clearViewTaskFactory = new ClearViewTaskFacgtory();
		
		Properties properties = new Properties();
		properties.put("title", "Send current network view");
		properties.put("preferredMenu", "Apps.CalVR");
		properties.put("enableFor", "network");
		
		Properties clearProperties = new Properties();
		clearProperties.put("title", "Clear current CalVR display");
		clearProperties.put("preferredMenu", "Apps.CalVR");
		clearProperties.put("enableFor", "network");

		registerService(context, taskFactory, NetworkViewTaskFactory.class, properties);
		registerService(context, clearViewTaskFactory, TaskFactory.class, clearProperties);
	}
}