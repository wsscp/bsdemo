package cc.oit.bsmes.job.base.parent;

import cc.oit.bsmes.job.base.vo.JobParams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseSimpleTask {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private boolean interrupt = false;

	public boolean isInterrupt() {
		return interrupt;
	}

	public void setInterrupt(boolean interrupt) {
		this.interrupt = interrupt;
	}
	/**
	 *  
	 */
	public abstract void process(JobParams parms) throws Exception;

}
