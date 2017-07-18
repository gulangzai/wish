package icom.yh.wish.controller;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import icom.yh.wish.service.ChicmeService;

@Controller
@RequestMapping("/chicme")
public class ChicmeController {

	@Resource
	private ChicmeService chicmeService;
	
	@RequestMapping(value="/chicmeCate", method=RequestMethod.GET)
	public String chicmeCate(Model model){
		model = chicmeService.chicmeCate(model);
		return "chicmecate";
	}
	
	@RequestMapping(value="/chicmeList", method=RequestMethod.GET)
	public  ResponseEntity<byte[]> chicmeList(String category) throws IOException{
		String path= chicmeService.chicmeList(category);
        File file=new File(path);  
        HttpHeaders headers = new HttpHeaders();    
        headers.setContentDispositionFormData("attachment", "wish.zip");   
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
                                          headers, HttpStatus.CREATED);    
	}
}
