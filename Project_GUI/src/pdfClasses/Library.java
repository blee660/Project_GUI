package pdfClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Library {

	
	/*
	 * singleton implementation of a library
	 */
	protected static Library instance;
	
	protected Library(){}
	
	public static Library getInstance(){
		if (instance == null){
			instance = new Library();
		}
		
		return instance;
	}
	
	//======================================================================
	//library implementation
	private ArrayList<PDF> PDFList = new ArrayList<PDF>();
	
	public void addPDF(PDF pdf){
		PDFList.add(pdf);
	}
	
	public void removePDF(PDF pdf){
		if(this.contains(pdf)){
			PDFList.remove(pdf);
		}
	}
	
	public int size(){
		return PDFList.size();
	}
	
	
	public boolean contains(PDF pdf){
		if(PDFList.contains(pdf)){
			return true;
		}
		
		return false;
	}
	
	public boolean contains(String path){
		File input = new File(path);
		
		for(PDF pdf : PDFList){
			try {
				if(pdf.getFileLocation().equals(input.getCanonicalPath())){
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
}
