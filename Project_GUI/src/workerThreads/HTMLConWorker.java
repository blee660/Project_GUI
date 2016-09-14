package workerThreads;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import application.Main;
import pdfClasses.PDF;

public class HTMLConWorker extends TemplateThread{

	private File outputFolder;
	private String templateCommand = "java -jar ";
	
	public HTMLConWorker(){
		super();
		
		String resourcePath = getClass().getResource("/resources/HTMLCon.jar").getPath();
		if(System.getProperty("os.name").contains("indows") && resourcePath.startsWith("/")){
			resourcePath = resourcePath.substring(1, resourcePath.length());
		}

		templateCommand = templateCommand + resourcePath + " ";
	}
	
	@Override
	public void taskLogic(PDF pdf) {
		System.out.println("task");
		String shellCommand = templateCommand + pdf.getFileLocation() + " " + outputFolder.getAbsolutePath();
		
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
	}
	
	
	@Override
	public void preExecutionWork(){
		outputFolder =  new File(Main.homeDir.getPath() +  File.separator + "html");
		outputFolder.mkdir();
	}
}
