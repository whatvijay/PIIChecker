package com.vijay.piicheckerinlogs.loganalyzer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vijay.piicheckerinlogs.NLP.NLPService;

@Service
public class AnalyzeLogFile {
	
	@Autowired
	NLPService nlp;

	public List<String> findPii(String[] result) {

		return nlp.detectPii(result);
	}

	public List<String> findPos(String result) {

		return nlp.detectPOSPii(result);

	}
}
