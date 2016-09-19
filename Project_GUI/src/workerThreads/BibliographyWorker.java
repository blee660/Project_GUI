package workerThreads;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import application.Main;
import pdfClasses.PDF;

/**
 * 
 * Worker thread for executing bibliography extraction of PDF documents in the library
 * Runs the template command with the bibliography extraction jar file
 * Stores output bibliography information into the "bibliography" directory of the session directory
 * 
 * @author Chuan-Yu Wu, Bom Yi Lee
 * 
 * */
public class BibliographyWorker extends TemplateThread{

	private String templateCommand = "java -jar ";
	
	// output folder to store extracted bibliography into 
	private File outputFolder;
	private File bibJar;
	
	// constructor
	public BibliographyWorker(){
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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		// set the output file as bibliography file of PDF
		String outputFileName = outputFolder.getAbsolutePath() + File.separator + pdf.getFileName() + ".xml";
		pdf.setBiblioFile(new File(outputFileName));
	}
	
	@Override
	public void preExecutionWork(){
		// create output folder to store output file before main task is executed
		outputFolder =  new File(Main.sessionDir.getPath() +  File.separator + "bibliography");
		outputFolder.mkdir();
		
		bibJar = generateResource("/resources/BiblioExtract.jar", outputFolder.getPath() +File.separator+"BiblioExtract.jar");
		
		String resourcePath = bibJar.getPath();
		if(System.getProperty("os.name").contains("indows") && resourcePath.startsWith("/")){
			resourcePath = resourcePath.substring(1, resourcePath.length());
		}
		// template command for running bibliography extraction	
		templateCommand = templateCommand + resourcePath + " ";

	}
	
	@Override
	public void removeResult(PDF pdf){
		// delete existing bibliography file
		String outputFileName = outputFolder.getAbsolutePath() + File.separator + pdf.getFileName() + ".xml";
		File f = new File(outputFileName);
		if(f.exists()){
			f.delete();
		}
	}
}
