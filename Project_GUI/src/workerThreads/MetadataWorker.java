package workerThreads;

import java.io.File;

import application.Main;
import pdfClasses.PDF;

public class MetadataWorker extends TemplateThread{

	public static File metadataFile;
	public static String metadataPath;
	
	@Override
	public void taskLogic(PDF pdf) {
		// IMPLEMENT LOGIC HERE
		
	}
	
	@Override
	public void preExecutionWork() {
		// IMPLEMENT ANY WORK REQUIRED BEFORE TASKLOGIC HERE
		String homePath = Main.homeDir.getAbsolutePath();
		String metadataPath = homePath + File.separator + "Metadata";
		metadataFile = new File(metadataPath);
		metadataFile.mkdir();
		metadataPath = metadataFile.getPath();
	}

	@Override
	protected void populateReliant() {
		// Add all tasks which rely on this one here
	}
}
