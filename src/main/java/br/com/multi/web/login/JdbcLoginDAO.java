package br.com.multi.web.login;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import br.com.multi.web.utils.Encript;
import br.com.multi.web.utils.StringHelper;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;
import java.sql.Timestamp;


public class JdbcLoginDAO extends SimpleJdbcDaoSupport implements LoginDAO
{
	public String prefije = "EXEC dbo.";
	private String TABLE_USERS = "Users";
	
	final static Logger logger = Logger.getLogger(JdbcLoginDAO.class);	 	
	
	@Override
	public List<Map<String, Object>> execStoredProcedure(String storedProcedure, HttpServletRequest request) {
		
		String stringParameters =this.getStringParameters(request);
		
		String sql = this.prefije + storedProcedure + stringParameters;
		
		System.out.println(sql);
		
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

		return rows;
	}
	
	public Map getUser(String username,String password) {
		
		String pass = new String(Base64.encodeBase64((username+":"+password).getBytes()));
		//String pass = Encript.generateSHA256(username+":"+password);
		
		String sql = this.prefije + this.TABLE_USERS +" @Login = '"+StringHelper.escapeSQL(username)+"', @Password = '"+pass+"'";
		
		System.out.println(sql);
		
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		
		for (Map row : rows) {
			return row;
		}
		 
		return null;								
	}		
	
	
	//Get Post params from request
	private String getStringParameters(HttpServletRequest request) {
				
		String result =" ";
		
		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {

			String paramName = StringHelper.escapeSQL(parameterNames.nextElement());
			String paramValue = StringHelper.escapeSQL(request.getParameter(paramName));
			
			result +=(paramName+"='"+paramValue+"',");			

		}
	
		//removing the last ',' char
		return result.substring(0,result.length()-1);
		
		
	}
	
}
