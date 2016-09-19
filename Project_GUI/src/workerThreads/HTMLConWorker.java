package workerThreads;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import application.Main;
import pdfClasses.PDF;

/**
 * 
 * Worker thread class for converting PDF documents into HTML.
 * The HTML conversion jar file is run on a new process and the
 * output (HTML file) is stored into the output directory specified
 * 
 * @author Chuan-Yu Wu, Bom Yi Lee
 * 
 * */
public class HTMLConWorker extends TemplateThread{
	// output folder to store output HTML file
	private File outputFolder;
	private String templateCommand = "java -jar ";
	public File htmlJar;
	
	// constructor
	public HTMLConWorker(){
		super();
	}
	
	@Override
	public void taskLogic(PDF pdf) {
		// full command with template command,  input document, and specified output folder
		String shellCommand = templateCommand + pdf.getFileLocation() + " " + outputFolder.getAbsolutePath();
		
		Process p;
		
		try {
			// create new process to execute command
			p = Runtime.getRuntime().exec(shellCommand);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			
			// read the input stream and print to console
			while((line = reader.readLine())!= null){
				System.out.println(line);
			}
			// kill subprocess
			p.destroy();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		// set the output file as HTML file of PDF
		String outputFileName = outputFolder.getAbsolutePath() + File.separator + pdf.getFileName() + ".html";
		pdf.setHTMLFile(new File(outputFileName));
	}	
	
	@Override
	public void preExecutionWork(){
		// create output folder to store output file before main task is executed
		outputFolder =  new File(Main.sessionDir.getPath() +  File.separator + "html");
		outputFolder.mkdir();
		
		htmlJar = generateResource("/resources/HTMLCon.jar", outputFolder.getPath() +File.separator+"HTMLCon.jar");
		
		String resourcePath = htmlJar.getPath();
		if(System.getProperty("os.name").contains("indows") && resourcePath.startsWith("/")){
			resourcePath = resourcePath.substring(1, resourcePath.length());
		}
		// template command for running bibliography extraction	
		templateCommand = templateCommand + resourcePath + " ";
	}
	
	@Override
	public void removeResult(PDF pdf){
		// delete existing HTML file
		String outputFileName = outputFolder.getAbsolutePath() + File.separator + pdf.getFileName() + ".html";
		File f = new File(outputFileName);
		if(f.exists()){
			f.delete();
		}
	}
}
