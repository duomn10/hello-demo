package sh.duomn.spring_hibernate.example.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sh.duomn.spring_hibernate.pub.LogType;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/")
public class HomeController {

	private static Logger logger = Logger.getLogger(LogType.AR);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping()
	public String home() {
	    	logger.info("requesting home");
		return "home";
	}

}

