package br.com.multi.web.login;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface LoginDAO 
{
	Map<String,String> getUser(String username, String password);

	List<Map<String, Object>> execStoredProcedure(String storedProcedure, HttpServletRequest request);
	
}




