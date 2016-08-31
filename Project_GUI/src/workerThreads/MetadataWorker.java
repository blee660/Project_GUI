package workerThreads;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


import application.Main;
import pdfClasses.PDF;

public class MetadataWorker extends TemplateThread{

	private String templateCommand = "java -jar ";
	private File outputFile;
	
	public MetadataWorker(){
		super();
		String resourcePath = getClass().getResource("/resources/PDFExtract.jar").getPath();
		if(System.getProperty("os.name").contains("indows") && resourcePath.startsWith("/")){
			resourcePath = resourcePath.substring(1, resourcePath.length());
		}
		
		templateCommand = templateCommand + resourcePath + " ";
		
		outputFile =  new File(Main.homeDir.getPath() +  File.separator + "meta");
		outputFile.mkdir();
	}
	
	@Override
	public void taskLogic(PDF pdf) {
		System.out.println("task");
		String shellCommand = templateCommand + pdf.getFileLocation() + " " + outputFile.getAbsolutePath();
		
		Process p;
		
		try {
			p = Runtime.getRuntime().exec(shellCommand);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			
			while((line = reader.readLine())!= null){
				System.out.println(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String outputFileName = outputFile.getAbsolutePath() + File.separator + pdf.getFileName() + ".txt";
		pdf.populateMeta(new File(outputFileName));
	}
}
