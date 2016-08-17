package pdfClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.PDFListController;

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
	private PDFListController plc;
	public void registerDisplay(PDFListController plc){
		this.plc = plc;
	};
	
	//======================================================================
	//library implementation
	private ArrayList<PDF> PDFList = new ArrayList<PDF>();
	
	public void addPDF(PDF pdf){		
		PDFList.add(pdf);
		plc.addItem(pdf);
	}
	
	public void addPDFs(List<PDF> pdfs){
		for(PDF pdf:pdfs){
			this.addPDF(pdf);
		}
	}
	
	public void addPDFFiles(List<File> pdfFiles){
		for(File f : pdfFiles){
			PDF pdf = new PDF(f);
			this.addPDF(pdf);
			plc.addItem(pdf);
		}
	}
	
	public void removePDF(PDF pdf){
		if(this.contains(pdf)){
			PDFList.remove(pdf);
		}
	}
	
	public void printAllPaths(){
		for(PDF pdf : PDFList){
			System.out.println(pdf.getFileLocation());
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
