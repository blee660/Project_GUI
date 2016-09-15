package pdfClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.MyListItem;

public class PDF {

	private String fileLocation;
	private String fileName;
	private File file = null;
	private MyListItem listItem;
	private ArrayList<String> keywords = new ArrayList<String>();
	private File HTMLfile = null;
	
	private MetadataStorer mds = new MetadataStorer();

	public PDF(String fileLocation){
		File tempFile = new File(fileLocation);
		if(tempFile.exists()){
			file = tempFile;
			this.fileLocation = file.getAbsolutePath();
			addFileName();
		}
	}
	
	public PDF(File file){
		if(file.exists()){
			this.file = file;
			this.fileLocation = file.getAbsolutePath();
			addFileName();
		}
	}
	
	public void populateMeta(File f){
		ObjectMapper mapper = new ObjectMapper();
		try {
			mds = mapper.readValue(f, MetadataStorer.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateListItem();
	}
	
	public File getHTMLFile(){
		return this.HTMLfile;
	}
	
	public void setHTMLFile(File html){
		this.HTMLfile = html;
	}
	
	public String getFileLocation(){
		return this.fileLocation;
	}
	
	public File getFile(){
		return this.file;
	}
	
	public MetadataStorer getMetadata(){
		return this.mds;
	}
	
	public void setListItem(MyListItem mli){
		this.listItem = mli;
	}
	
	public void updateListItem(){
		if(this.getMetadata().getTitle()!= null && this.getMetadata().getTitle()!=""){
			this.listItem.updateLabel(this.getMetadata().getTitle());
		}
	}
	
	private void addFileName(){
		String[] split = file.getName().split("\\.");
		fileName = split[0];
	}
	public String getFileName(){
		return this.fileName;
	}
	
	public void addKeyWord(String keyword){
		this.keywords.add(keyword);
	}
	
	public ArrayList<String> getKeyWords(){
		return this.keywords;
	}
}
