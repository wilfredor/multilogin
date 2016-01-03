package br.com.multi.web.login;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.multi.web.utils.Labels;

@Controller
public class HomeController {

	@RequestMapping("/{name}")
	public ModelAndView test(HttpServletResponse response, HttpServletRequest request, @PathVariable String name)
			throws IOException {

		SessionJson s = new SessionJson();

		// if session is not active
		if (!s.isSessionActive(request)) {

			System.out.println(Labels.INACTIVE_SESSION);
			//If the user is trying do login and the session is inactive
			return new ModelAndView("home");		
		}

		// if session is active go to app
		if ((name == "home") || (name == null)) 												
			response.sendRedirect(LoginController.DASHBOARD);					
		

		return new ModelAndView(name);
	}

	@RequestMapping("/")
	public ModelAndView test(HttpServletResponse response, HttpServletRequest request) throws IOException {

		SessionJson s = new SessionJson();

		// if session is not active
		if (s.isSessionActive(request)) // return new ModelAndView("home");
		{

			System.out.println(Labels.INACTIVE_SESSION);
			// If the user is trying do login and the session is inactive
			response.sendRedirect(LoginController.DASHBOARD);
		}
		
		return new ModelAndView("home");
	}

}
