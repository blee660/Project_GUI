package workerThreads;

import pdfClasses.PDF;

public class MetadataWorker extends TemplateThread{

	
	@Override
	public void taskLogic(PDF pdf) {
		// IMPLEMENT LOGIC HERE
	}
	
	@Override
	public void preExecutionWork() {
		// IMPLEMENT ANY WORK REQUIRED BEFORE TASKLOGIC HERE
	}

	@Override
	protected void populateReliant() {
		// Add all tasks which rely on this one here
	}
}
