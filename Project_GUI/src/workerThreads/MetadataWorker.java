package workerThreads;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import application.Main;
import pdfClasses.PDF;

public class MetadataWorker extends TemplateThread{

	private String resourcePath = getClass().getResource("/resources/PDFExtract.jar").getFile();
	private String templateCommand = "java -jar " + resourcePath + " ";
	private File outputFile;
	
	
	public MetadataWorker(){
		super();
	}
	
	@Override
	public void taskLogic(PDF pdf) {
		System.out.println("task");
		String shellCommand = templateCommand + pdf.getFileLocation() + " " + outputFile.getPath();
		
		Process p;
		
		try {
			p = Runtime.getRuntime().exec(templateCommand);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line = "";
			
			while((line = reader.readLine())!= null){
				System.out.println(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void preExecutionWork(){
		outputFile =  new File(Main.homeDir.getPath() +  File.separator + "meta");
		outputFile.mkdir();
	}
	
	@Override
	protected void populateReliant(){}
	
}
