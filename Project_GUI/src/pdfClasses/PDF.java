package pdfClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.MetadataTab;
import application.MyListItem;
import javafx.scene.control.Tab;

public class PDF {

	private MyListItem listItem;
	private MetadataTab MT = new MetadataTab(this);
	
	private String fileLocation;
	private String fileName;
	private File file = null;
	private ArrayList<String> keywords = new ArrayList<String>();
	
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
	
	public Tab getTab(){
		return this.MT;
	}
}
