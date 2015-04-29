package org.cytoscape.calvr.internal.task;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class ClearViewTaskFacgtory extends AbstractTaskFactory {

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new ClearViewTask());
	}
}
