package workerThreads;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.Main;
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
	private boolean preExDone = false;
	
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
				if(!preExDone){
					preExecutionWork();
					preExDone = true;
				}
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
	
	public File generateResource(String input, String output) {
		File f = new File(output);

		// check to see if file already exists
		if (f.exists()) {
			return f;
		}

		try {
			f.createNewFile();

			// Copying the resource to the new file
			InputStream is = Main.class.getResourceAsStream(input);
			OutputStream os = new FileOutputStream(f);

			int read = 0;

			byte[] bytes = new byte[1024];

			while ((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}

			os.close();
			is.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		f.deleteOnExit();
		return f;
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
