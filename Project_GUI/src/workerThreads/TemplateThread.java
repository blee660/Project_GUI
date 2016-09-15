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
 * 
 * */
public class TemplateThread extends Service<Void> {

	public TemplateThread() {
		this.start();
	}

	private List<PDF> pdfs = Collections.synchronizedList(new ArrayList<PDF>());
	private ArrayList<TemplateThread> reliantTasks = new ArrayList<TemplateThread>();
	private List<PDF> removeList = new ArrayList<PDF>();
	
	public void addPDF(PDF pdf) {
		
		synchronized (pdfs) {
			this.pdfs.add(pdf);
		}
		
		if (!this.isRunning()) {
			this.restart();
		}
	}
	
	public void removePDF(PDF pdf){
		if(pdfs.contains(pdf)){
			removeList.add(pdf);
		}else{
			removeResult(pdf);
		}

	}
	
	@Override
	protected void succeeded(){
		System.out.println(this.getClass().getName());
		if(!pdfs.isEmpty()){
			this.restart();
		}
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				preExecutionWork();
				while (!pdfs.isEmpty()) {
					PDF temp;
					temp = pdfs.get(0);

					taskLogic(temp);

					addToReliant(temp);
					
					if(removeList.contains(temp)){
						removeResult(temp);
					}
					
					pdfs.remove(temp);
				}
				return null;
			}
		};
	}

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

	private void addToReliant(PDF pdf) {
		Platform.runLater(() -> {
			for (TemplateThread tt : reliantTasks) {
				tt.addPDF(pdf);
			}
        });
	}
}
