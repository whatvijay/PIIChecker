package com.vijay.piicheckerinlogs.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vijay.piicheckerinlogs.loganalyzer.AnalyzeLogFile;



@RestController
public class CheckPIIApi {
	
	@Autowired
	AnalyzeLogFile analyzeLogFile ;
	
	@GetMapping(value="/ping")
	public String ping()
	{
		return "pong";
	}
	
	
	
	@GetMapping(value="/submitFile")
	public String submitFile()
	{
		return "<html><form method='POST' action='/analyzeFile' enctype='multipart/form-data'><input type='file'/><button type='submit'>Submit</button></form></html>";
	}

	@PostMapping(value="/analyzeFile")
	public String analyzeFile(@RequestParam("file")MultipartFile file)
	{
		//AnalyzeLogFile analyzeLogFile = new  AnalyzeLogFile();
		List<String> piiList = new ArrayList<>();
	
		//analyzeLogFile.analyzeFiles();
		//file.getOriginalFilename();
		//file.
		try{
			 //  file.getBytes();
			InputStream initialStream = file.getInputStream();
			String result= new BufferedReader(new InputStreamReader(initialStream)).lines().collect(Collectors.joining("\n"));
			List<String> stList = new ArrayList<>();
			StringTokenizer stringToken = new StringTokenizer(result," ");
			while(stringToken.hasMoreTokens())
			{
				stList.add(stringToken.nextToken());
			}
			 piiList=analyzeLogFile.findPii((String[]) stList.stream().toArray(String[]::new));
			
			 piiList.addAll(analyzeLogFile.findPos(result)); 
			
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return file.getOriginalFilename() + " fileAnalyzed " + String.join("::", piiList);
	}

}
