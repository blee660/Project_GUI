package workerThreads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pdfClasses.PDF;

/**
 * 
 * Template class to be extended by all worker threads
 * 
 * @author Chuan-Yu Wu, Bom Yi Lee
 * 
 * */
public class TemplateThread extends Service<Void> {

	// constructor
	public TemplateThread() {
		// start the service
		this.start();
	}

	// list of PDFs to execute
	private List<PDF> pdfs = Collections.synchronizedList(new ArrayList<PDF>());
	// list of tasks that are dependent on another
	private ArrayList<TemplateThread> reliantTasks = new ArrayList<TemplateThread>();
	// list of PDFs to be removed
	private List<PDF> removeList = new ArrayList<PDF>();
	
	// restart service when a PDF is added
	public void addPDF(PDF pdf) {
		
		synchronized (pdfs) {
			this.pdfs.add(pdf);
		}
		
		if (!this.isRunning()) {
			this.restart();
		}
	}
	
	// remove PDFs from list
	public void removePDF(PDF pdf){
		if(pdfs.contains(pdf)){
			removeList.add(pdf);
		}else{
			removeResult(pdf);
		}

	}
	
	// print completed class names
	@Override
	protected void succeeded(){
		System.out.println(this.getClass().getName());
		// restart service if PDF list is not empty
		if(!pdfs.isEmpty()){
			this.restart();
		}
	}

	@Override
	protected Task<Void> createTask() {
		// create a new task on every PDF in the list
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				preExecutionWork();
				while (!pdfs.isEmpty()) {
					PDF temp;
					temp = pdfs.get(0);
					// execute main task
					taskLogic(temp);
					// add dependent tasks
					addToReliant(temp);
					
					if(removeList.contains(temp)){
						// remove result file
						removeResult(temp);
					}
					// remove from PDF list once all tasks have been completed
					pdfs.remove(temp);
				}
				return null;
			}
		};
	}

	// add reliant tasks to list
	public void registerReliant(TemplateThread tt){
		reliantTasks.add(tt);
	}
	
	// Main task logic
	public void taskLogic(PDF pdf) {
		// IMPLEMENT LOGIC HERE
	}

	// Implementation of any work required before execution of taskLogic
	public void preExecutionWork() {
		// IMPLEMENT PRE EXECUTION WORK HERE
	}
	
	public void removeResult(PDF pdf){
		
	}

	// run dependent tasks on PDFs after pre execution tasks have completed
	private void addToReliant(PDF pdf) {
		Platform.runLater(() -> {
			for (TemplateThread tt : reliantTasks) {
				tt.addPDF(pdf);
			}
        });
	}
}
