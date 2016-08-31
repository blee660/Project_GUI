package workerThreads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pdfClasses.PDF;

public class TemplateThread extends Service<Void> {

	public TemplateThread() {
		this.start();
	}

	private List<PDF> pdfs = Collections.synchronizedList(new ArrayList<PDF>());
	private ArrayList<TemplateThread> reliantTasks = new ArrayList<TemplateThread>();
	
	public void addPDF(PDF pdf) {
		
		synchronized (pdfs) {
			this.pdfs.add(pdf);
		}
		
		if (!this.isRunning()) {
			this.restart();
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
					temp = pdfs.remove(0);
					taskLogic(temp);

					addToReliant(temp);

				}
				
				return null;
			}
		};
	}

	public void registerReliant(TemplateThread tt){
		reliantTasks.add(tt);
	}
	
	public void taskLogic(PDF pdf) {
		// IMPLEMENT LOGIC HERE
	}

	public void preExecutionWork() {
		// IMPLEMENT ANY WORK REQUIRED BEFORE TASKLOGIC HERE
	}

	private void addToReliant(PDF pdf) {
		Platform.runLater(() -> {
			for (TemplateThread tt : reliantTasks) {
				tt.addPDF(pdf);
			}
        });
	}
}
