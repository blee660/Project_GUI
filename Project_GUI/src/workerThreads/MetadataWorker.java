package workerThreads;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import application.Main;
import pdfClasses.PDF;

/**
 * 
 * Worker thread class for extracting entities from PDF documents and 
 * creating a metadata file in json format. The metadata extraction 
 * jar file is run on a new process and the output (metadata file) is
 * stored into the output directory specified
 * 
 * @author Chuan-Yu Wu, Bom Yi Lee
 * 
 * */
public class MetadataWorker extends TemplateThread{

	private String templateCommand = "java -jar ";
	// output folder to store output HTML file
	private File outputFolder;
	
	// constructor
	public MetadataWorker(){
		super();
		String resourcePath = getClass().getResource("/resources/PDFExtract.jar").getPath();
		if(System.getProperty("os.name").contains("indows") && resourcePath.startsWith("/")){
			resourcePath = resourcePath.substring(1, resourcePath.length());
		}
		// template command for running HTML conversion	
		templateCommand = templateCommand + resourcePath + " ";
	}
	
	@Override
	public void taskLogic(PDF pdf) {
		System.out.println("task");
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
		// set the output file as metadata of PDF
		String outputFileName = outputFolder.getAbsolutePath() + File.separator + pdf.getFileName() + ".json";
		pdf.populateMeta(new File(outputFileName));
	}
	
	@Override
	public void preExecutionWork(){
		// create output folder to store output file before main task is executed
		outputFolder =  new File(Main.sessionDir.getPath() +  File.separator + "meta");
		outputFolder.mkdir();
	}
	
	@Override
	public void removeResult(PDF pdf){
		// delete existing metadata file
		String outputFileName = outputFolder.getAbsolutePath() + File.separator + pdf.getFileName() + ".json";
		File f = new File(outputFileName);
		if(f.exists()){
			f.delete();
		}
	}
}
