/*
 * <h1>LoginController</h1>
 * The LoginControler is a mapping class
 * to redirect each page in a standart output 
 * in client side
 * 
 * @author Wilfredo Rodriguez
 * @version 1.0
 * @since 2014-09-20
 */
package br.com.multi.web.login;

import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.simple.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.multi.web.utils.Labels;
import br.com.multi.web.utils.StringHelper;

@Controller
public class LoginController extends AbstractController {
	public static String DASHBOARD = "apps";
	
	/*
	 * Return a database access object conection
	 * 
	 * @param database String with the database name
	 */
	public static LoginDAO getDAO(String database) {
		// set datasource from current database from session var
		System.out.println(Labels.DATABASE_SELECTED+database);
		return (LoginDAO) (new ClassPathXmlApplicationContext("Spring-Module.xml"))
				.getBean(database + "SimpleDAO");
	}
	
	
	/*
	 * Return a view sending all params from a request
	 * 
	 * @param request HttpServletRequest to send to the view
	 * @param viewname String with the view name to load
	 * @return ModelAndView return a view with viewname
	 */
	public static ModelAndView buildViewParams(HttpServletRequest request, String viewname) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		ModelAndView mav = new ModelAndView(viewname, map);
				
		Enumeration<String> parameterNames = request.getParameterNames();
		
		while( parameterNames.hasMoreElements() )
		  {				   
		    String paramName = parameterNames.nextElement();
		    
		    //if the request var is a Array 
		    if ((request.getParameterValues(paramName).getClass().isArray())
		    //if Array size is more than 1
		       &&(request.getParameterValues(paramName).length>1))
		    {
		    	map.put(paramName, Arrays.toString(request.getParameterValues(paramName)));		    	
		    }
		    else
		    	//if the request var is a simple var
		    	map.put(paramName, request.getParameter(paramName));
		  }
				
		mav.addAllObjects(map);
		
		//return view
		return mav;
	}	

	@RequestMapping(value = "/getSession", method = { RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public @ResponseBody String getSession(HttpServletRequest request) {

		SessionJson sessionJson = new SessionJson();

		return sessionJson.getJsonfromSession(request);

	}
	
	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// invalidate session
		request.getSession().invalidate();

		// redirect to login
		response.sendRedirect("home");
		
	}
	
	private void mapToSession(Map<String,String> m,HttpSession s)
	{	
		for (Map.Entry<String, String> entry : m.entrySet())	
			
		    s.setAttribute(entry.getKey(), entry.getValue());		
	}

	// Get session json
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public void login(
			 @RequestParam(value = "database", required = true) String database,
			 @RequestParam(value = "username", required = true) String username,
			 @RequestParam(value = "password", required = true) String password,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get profissional
		LoginDAO loginDAO = this.getDAO(database);

		Map<String,String> user = loginDAO.getUser(username, password);
		
		
		//Check if user exist
		if (user == null) {
			// return to login page
			System.out.println(Labels.USER_NOT_FOUND);
			response.sendRedirect("home");
			return;
		}
		
		user.put("username", username);
		user.put("database", database);
		
		HttpSession s = request.getSession();
		
		//Write values to session
		this.mapToSession(user, s);
		
		response.sendRedirect(LoginController.DASHBOARD);
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}