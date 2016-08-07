package workerThreads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pdfClasses.PDF;

public class TemplateThread extends Service<Void> {

	public TemplateThread(){
		this.executeTask(createTask());
		populateReliant();
	}
	
	private List<PDF> pdfs = Collections.synchronizedList(new ArrayList<PDF>());
	private ArrayList<TemplateThread> reliantTasks = new ArrayList<TemplateThread>();
	
	
	
	public void addPDF(PDF pdf) {
		synchronized (pdfs) {
			this.pdfs.add(pdf);
			if (!this.isRunning()) {
				this.executeTask(createTask());
			}
		}
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				preExecutionWork();
				
				while (pdfs.size() > 0) {
					PDF temp;
					synchronized (pdfs) {
						temp = pdfs.remove(0);
					}
					taskLogic(temp);
					
					addToReliant(temp);
				}
				return null;
			}
		};
	}

	public void taskLogic(PDF pdf) {
		//IMPLEMENT LOGIC HERE
	}
	
	public void preExecutionWork(){
		//IMPLEMENT ANY WORK REQUIRED BEFORE TASKLOGIC HERE
	}
	
	private void populateReliant(){
		//Add all tasks which rely on this one here
	}
	
	private void addToReliant(PDF pdf){
		for(TemplateThread tt: reliantTasks){
			tt.addPDF(pdf);
		}
	}

}
