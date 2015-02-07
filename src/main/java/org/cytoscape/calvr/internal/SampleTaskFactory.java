package org.cytoscape.calvr.internal;

import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

/**
 * The TaskFactory is a Singleton object whose sole purpose is to create new
 * instances of a specific kind of Task. TaskFactory objects are meant to be
 * registered as OSGi services for the specific TaskFactory interface they
 * implement (in this case NetworkTaskFactory). This object is defined as a
 * service in the Spring XML configuration file called bundle-context-osgi.xml
 * in the src/main/resources/META-INF/spring directory of this project. <br/>
 * 
 */
public class SampleTaskFactory extends AbstractNetworkViewTaskFactory {

	public TaskIterator createTaskIterator(CyNetworkView view) {
		return new TaskIterator(new SampleTask(view));
	}
}