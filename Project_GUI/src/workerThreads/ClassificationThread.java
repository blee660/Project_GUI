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

public class ClassificationThread extends TemplateThread{
	
	private File outputFile;
	private String templateCommand = "java -jar ";
	
	public ClassificationThread(){
		super();
		
		String resourcePath = getClass().getResource("/resources/PDFClassify.jar").getPath();
		if(System.getProperty("os.name").contains("indows") && resourcePath.startsWith("/")){
			resourcePath = resourcePath.substring(1, resourcePath.length());
		}
		
		templateCommand = templateCommand + resourcePath + " ";
		
	}
	
	@Override
	public void taskLogic(PDF pdf) {
		String abstractText = pdf.getMetadata().getAbstractx();

		String outputFileName = outputFile.getPath() + File.separator + pdf.getFileName() + ".txt";
		
		try {

			File abstractFile = new File(outputFileName);
			if (!abstractFile.exists()) {
				abstractFile.createNewFile();
				BufferedWriter out = new BufferedWriter
					    (new OutputStreamWriter(new FileOutputStream(abstractFile.getAbsoluteFile()),"UTF-8"));
				out.write(abstractText);
				out.close();
			} 
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		String shellCommand = templateCommand + outputFileName;
		Process p;
		
		try {
			p = Runtime.getRuntime().exec(shellCommand);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String keyword = "";
			
			while((keyword = reader.readLine())!= null){
				pdf.addKeyWord(keyword);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void preExecutionWork(){
		outputFile =  new File(Main.homeDir.getPath() +  File.separator + "keywords");
		outputFile.mkdir();
	}
	
}
