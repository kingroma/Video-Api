package com.mydefault.app.image.web;

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mydefault.app.common.util.MyDefaultSource;

@Controller
@RequestMapping("/images/*")
public class ImageController {
	@Resource
    protected MyDefaultSource myDefaultSource;
	
	private final String[] imgExtension = {
			".png" , ".jpg" , ".jpeg"
	};
	
	private final String defalutImage = "default.png";
	
	// http://localhost:8010/app/images/get/map
	@RequestMapping(value = "/get/{name}", method = RequestMethod.GET , produces = "image/png" )
	public void getImage(
			@PathVariable("name") String name , 
			HttpServletResponse response ){
		String path = myDefaultSource.getMessage("image.storage");
		
		System.out.println("img >> " + name);
		
		ServletOutputStream sos = null ; 
		FileInputStream fis = null;
		
		File file = null;
		
		for ( String e : imgExtension ){
			file = new File(path + name + e);
			
			if ( file.exists() ){
				switch(e){
				case ".png":
					response.setContentType("image/png");
					break;
				case ".jpg":
					response.setContentType("image/jpeg");
					break;
				case ".jpeg":
					response.setContentType("image/jpeg");
					break;
				}
				break;
			}
			else 
				file = null ;
		}
		
		try {
			if ( file == null ){
				file = new File ( path + defalutImage );
				response.setContentType("image/jpeg");
			}
			
			sos = response.getOutputStream();
			
			fis = new FileInputStream(file);
			int length ; 
			
			byte[] buffer = new byte[128];
			while ( (length = fis.read(buffer)) != -1 ){
				sos.write(buffer,0,length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if ( sos != null ) 
					sos.close();
				if ( fis != null )
					fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
}
