package com.vijay.piicheckerinlogs.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping(value="/")
	public String indexPage()
	{
		return "redirect:Index.html";
	}

}
