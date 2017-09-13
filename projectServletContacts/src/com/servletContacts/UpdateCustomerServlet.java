package com.servletContacts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateCustomerServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		HttpSession session = req.getSession();
		List<String> todolist = new ArrayList<String>();
		System.out.println("inside udpatecustomerservlet");
		
		String data = req.getParameter("data");
		
		JSONObject json =null;
		
		try {
			
			json = new JSONObject(data);
			String custName = json.get("cusEmail").toString();
			JSONArray jarray = json.getJSONArray("todo");
			if(jarray!=null){
				for(int i=0;i<jarray.length();i++){
				todolist.add(jarray.getString(i));
				}
			}
			
			Query q= pm.newQuery(CustomerJDO.class);
			q.setFilter("adminEmail =='"+session.getAttribute("sessionname").toString()+"' && email =='"+custName+"'");
			
			List<CustomerJDO> result1 = (List<CustomerJDO>) q.execute();
			CustomerJDO customer = pm.getObjectById(CustomerJDO.class,result1.get(0).getKey());
			customer.setTodoList(todolist);
			pm.makePersistent(customer);
			System.out.println("list :"+todolist);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			pm.close();
		}
		
		
	}
}
