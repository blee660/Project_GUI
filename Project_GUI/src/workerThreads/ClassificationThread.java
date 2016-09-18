package workerThreads;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import application.Main;
import pdfClasses.PDF;

/**
 * 
 * Worker thread class for extracting keyphrases from pdf documents.
 * The PDFClassify jar file is run on a new process and the
 * output (extracted keyphrases) is stored into the keyword field 
 * of the document, and also into an output file in the specified
 * directory.
 * 
 * @author Chuan-Yu Wu, Bom Yi Lee
 * 
 * */
public class ClassificationThread extends TemplateThread{
	
	private File outputFolder;
	private String templateCommand = "java -jar ";
	
	// constructor
	public ClassificationThread(){
		super();
		
		// Get path of jar file to be executed
		String resourcePath = getClass().getResource("/resources/PDFClassify.jar").getPath();
		if(System.getProperty("os.name").contains("indows") && resourcePath.startsWith("/")){
			resourcePath = resourcePath.substring(1, resourcePath.length());
		}
		
		// Template command specifying the jar file to be executed
		templateCommand = templateCommand + resourcePath + " ";
		
	}
	
	public void taskLogic(PDF pdf) {
		// get abstract content of PDF
		String abstractText = pdf.getMetadata().getAbstractx();
		
		// folder to store output and filename to store output file as
		String outputFileName = outputFolder.getPath() + File.separator + pdf.getFileName() + ".txt";
		
		try {
			// create a new file to store the abstract content
			File abstractFile = new File(outputFileName);
			if (!abstractFile.exists()) {
				abstractFile.createNewFile();
				// write abstract into file
				BufferedWriter out = new BufferedWriter
					    (new OutputStreamWriter(new FileOutputStream(abstractFile.getAbsoluteFile()),"UTF-8"));
				out.write(abstractText);
				out.close();
			} 
		}catch (IOException e) {
			e.printStackTrace();
		}
		// full command with template command,  input document, and specified output folder
		String shellCommand = templateCommand + outputFileName;
		
		Process p;
		
		try {
			// create new process to execute command
			p = Runtime.getRuntime().exec(shellCommand);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String keyword = "";
			
			// read the extracted keywords from the input stream and store into keyword list of PDF
			while((keyword = reader.readLine())!= null){
				pdf.addKeyWord(keyword);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void preExecutionWork(){
		// create output folder to store output file before main task is executed
		outputFolder =  new File(Main.sessionDir.getPath() +  File.separator + "keywords");
		outputFolder.mkdir();
	}
	
	public void removeResult(PDF pdf){
		// delete existing file
		String outputFileName = outputFolder.getAbsolutePath() + File.separator + pdf.getFileName() + ".json";
		File f = new File(outputFileName);
		if(f.exists()){
			f.delete();
		}
	}
	
}
