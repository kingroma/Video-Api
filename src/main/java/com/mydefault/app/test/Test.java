package com.mydefault.app.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.mydefault.app.ingest.service.IngestVO;

public class Test {

	public static void main(String[] args) {
		try {
			copyFileTest ( null , null );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static IngestVO copyFileTest(IngestVO entity, HttpServletRequest request) throws Exception {
		if ( entity == null ){
			String videoStorage = "C:\\Users\\kingrome\\git-video\\WebDefault\\storage";
			String ingestNm = "test123123";
			
			
			File copyFile = new File("C:\\Users\\kingrome\\git-video\\WebDefault\\sample\\sample1.mp4");
			
			if (copyFile.exists()){
				System.out.println("5");
				fileCopy(copyFile,videoStorage,ingestNm);
			}
			
		}
		return entity;
	}
	
	private static void fileCopy(File copyFile, String storagePath , String fileName){
		FileInputStream fis = null ; 
		FileOutputStream fos = null ; 
		
		try {
			if ( storagePath != null && !( storagePath.endsWith("\\") || storagePath.endsWith("/") ) ){
				storagePath += "/";
				System.out.println(storagePath);
			}
			
			File storageDirectory = new File ( storagePath );
			if ( !storageDirectory.isDirectory() ){
				storageDirectory.mkdir();
			}
			
			
			File targetFile = new File(storagePath + fileName);
			
			if ( targetFile.exists() ){
				File tempFile = new File ( targetFile.getAbsolutePath() + ".")  ;
				targetFile.renameTo(tempFile);
			}
			
			
			fis = new FileInputStream(copyFile);
			fos = new FileOutputStream(targetFile) ;
			byte[] b = new byte[4096];
			int cnt = 0;
			System.out.println(targetFile.getAbsolutePath());
			while((cnt=fis.read(b)) != -1){
				fos.write(b, 0, cnt);
			}
			
			int pos = copyFile.getName().lastIndexOf( "." );
			String ext = copyFile.getName().substring( pos + 1 );
			System.out.println(ext);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static Date firstDoDate(long minute){
		Date current = new Date();
		
		SimpleDateFormat sdfyyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmm");
		SimpleDateFormat sdfHHmm = new SimpleDateFormat("HHmm");
		SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		
		long hour = minute / 3600 ;
		long min = minute % 3600 / 60 ; 
		
		String h = ( hour > 10 ? "" + hour : "0" + hour ); 
		String m = ( min > 10 ? "" + min : "0" + min );  
		
		String format = "";
		Date ret = null ; 
		try {
			if ( Integer.parseInt(h+m) > Integer.parseInt(sdfHHmm.format(current)) ){
				format = sdfyyyyMMdd.format(current) + h + m ;
			}else { 
				Date nextDay = new Date(current.getTime() + 24 * 60 * 60 * 1000);
				
				format = sdfyyyyMMdd.format(nextDay) + h + m ;
			}
			
			ret = sdfyyyyMMddHHmmss.parse(format) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return ret ; 
	}
	
	public static long nextJobTime(long minute){
		SimpleDateFormat sdf1 = new SimpleDateFormat("HHmm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Date current = new Date();
		
		long ret = 0 ; 
		
		long hour = minute / 3600 ;
		long min = minute % 3600 / 60 ;
		
		String h = ( hour > 10 ? "" + hour : "0" + hour ); 
		
		String m = ( min > 10 ? "" + min : "0" + min );  
		
		return ret ; 
	}

}
