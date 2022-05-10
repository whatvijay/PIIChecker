package com.vijay.piicheckerinlogs.NLP;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;

@Service
public class NLPService {

	
	public List<String> detectPii(String[] logText) {
		try {
			InputStream inputStream = new ClassPathResource("nlp/en-ner-person.bin").getInputStream();
			
			TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
			NameFinderME nameFinder = new NameFinderME(model);

			Span nameSpans[] = nameFinder.find(logText);
			System.out.println(nameSpans.length);
			List<String> piiList = new ArrayList<>();
			for (Span s : nameSpans) {
				System.out.println(s.toString());
				piiList.add(s.toString());
			}
			return piiList;

		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return new ArrayList<String>();
		}
	}

	public List<String> detectPOSPii(String descriptorValue) {
		InputStream inputStream;
		try {
			///home/vijay/codeSamples/nlp/en-pos-maxent.bin
			inputStream = new ClassPathResource("nlp/en-pos-maxent.bin").getInputStream();

			POSModel posModel = new POSModel(inputStream);
			POSTaggerME tagger = new POSTaggerME(posModel);
			WhitespaceTokenizer wsTokenizer = WhitespaceTokenizer.INSTANCE;
			String tokens[] = wsTokenizer.tokenize(descriptorValue);
			String tags[] = tagger.tag(tokens);
			List<String> piiList = new ArrayList<>();
			for (int i = 0; i < tokens.length; i++) {
				if (tags[i].equalsIgnoreCase("NN") || tags[i].equalsIgnoreCase("NNP") || tags[i].equalsIgnoreCase("NNS")
						|| tags[i].equalsIgnoreCase("NNPS")) {
					piiList.add(tokens[i]);
				}

			}
			return piiList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}
}
