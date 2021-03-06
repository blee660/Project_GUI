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
public class KeyphraseExtractionWorker extends TemplateThread{
	
	private File outputFolder;
	private File abstractFolder;
	private String templateCommand = "java -jar ";
	public File keyphraseJar;
	
	// constructor
	public KeyphraseExtractionWorker(){
		super();
	}
	
	public void taskLogic(PDF pdf) {
		// get abstract content of PDF
		String abstractText = pdf.getMetadata().getAbstractx();
		File abstractFile = new File(abstractFolder.getAbsolutePath() + File.separator + pdf.getFileName() + ".txt");
		
		try {
			// create a new file to store the abstract content
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
		String shellCommand = templateCommand + abstractFile.getAbsolutePath() + " " + outputFolder.getAbsolutePath();
		
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
		
		abstractFile.deleteOnExit();

	}
	
	public void preExecutionWork(){
		// create output folder to store output file before main task is executed
		outputFolder =  new File(Main.sessionDir.getPath() +  File.separator + "keywords");
		outputFolder.mkdir();
		
		abstractFolder = new File(Main.sessionDir.getPath() + File.separator + "abstracts");
		abstractFolder.mkdir();
		
		abstractFolder.deleteOnExit();
		
		keyphraseJar = generateResource("/resources/KeyphraseExtract.jar", outputFolder.getPath() +File.separator+"KeyphraseExtract.jar");
		
		String resourcePath = keyphraseJar.getPath();
		if(System.getProperty("os.name").contains("indows") && resourcePath.startsWith("/")){
			resourcePath = resourcePath.substring(1, resourcePath.length());
		}
		// template command for running bibliography extraction	
		templateCommand = templateCommand + resourcePath + " ";
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
