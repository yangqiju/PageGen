package com.joyveb.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUtil {
	static public boolean deleteDirectory(File path) {
		if(!path.exists())return false;
		if(!path.isDirectory()){
			path.delete();
		}
		else {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
	
	static public void saveFile(CommonsMultipartFile src,File dst) throws IOException{
		try {
			FileOutputStream fout = new FileOutputStream(dst);
			fout.write(src.getBytes());
			fout.flush();
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

	}
	public static String replaceName(String name,String orgfilename){
		
		int ext=orgfilename.lastIndexOf(".");
		if(ext<0){
			ext=0;
		}
		return  name+orgfilename.substring(ext,orgfilename.length());
	}
	public static void main(String[] args) {
		System.out.println(replaceName("up","fjslfjda.dat"));
	}
}
