package com.dant.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mariadb.jdbc.Driver;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)

public class API {

	@POST 
	@Path("/u")
	public String createUser(@DefaultValue("Null") @FormParam("fname") String fname,
			@DefaultValue("Null") @FormParam("lname") String lname,
			@DefaultValue("Null") @FormParam("email") String email,
			@DefaultValue("Null") @FormParam("password") String password)
	{
		System.out.println("Input :");
		System.out.println(fname);
		System.out.println(lname);
		System.out.println(email);
		System.out.println(password);
		return fname.toString();
	}
	

	@POST 
	@Path("/u")
	public String createUser(@DefaultValue("Null") @FormParam("fname") String fname)
	{
		System.out.println("Input :");
		System.out.println("fname : " +fname);
		return fname.toString();
	}
	

	@POST 
	@Path("/u")
	public String createUser2(@DefaultValue("Null") @FormParam("lname") String lname)
	{
		System.out.println("Input :");
		System.out.println("lname : " +lname);
		return lname.toString();
	}
	
	

	@GET
	@Path("/u")
	public void getUsers(){
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3336/imap_contacts", "root","root");
			System.out.println("Connexion effectuee");
			Statement stmt = conn.createStatement();
			ResultSet req = stmt.executeQuery("Select * from users");
			while(req.next()){
				//				int id = req.getInt(1); 
				//				String nom = req.getString(2); 
				//				double prix = req.getDouble(3); 
				//				java.sql.Date date = req.getDate(4); 
			}

		} catch (SQLException e) {
			System.out.println("erreur de connexion");
			e.printStackTrace();
		}

	}




}


