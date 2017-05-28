package com.dant.business;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import com.dant.dao.UserDAO;
import com.dant.entity.Session;
import com.dant.entity.User;
import com.dant.exception.EmailException;
import com.dant.exception.EmptyEmailException;
import com.dant.exception.EmptyNameException;
import com.dant.exception.EmptyPasswordException;
import com.dant.exception.HexadecimalException;
import com.dant.exception.InvalidEmailException;
import com.dant.exception.InvalidUserKeyException;
import com.dant.exception.UserFoundException;
import com.dant.exception.UserNotFoundException;
import com.dant.exception.UserNotFoundExceptionMapper;
import com.dant.util.KeyGeneratorUtil;
import com.dant.util.UserFoundExceptionMapper;

public class UserBusiness {

	private UserDAO userDAO = new UserDAO();

	public User createUser(String fname, String lname, String email, String password) throws SQLException, EmailException, EmptyNameException, EmptyEmailException, EmptyPasswordException, InvalidEmailException {

		if(email.length()==0){
			throw new EmptyEmailException();
		}

		if (userDAO.emailAlreadyExists(email)) {
			throw new EmailException();
		}

		if(!validateEmail(email)){
			throw new InvalidEmailException();
		}

		if(fname.length()==0 || lname.length()==0){
			throw new EmptyNameException();
		}

		if(password.length()==0){
			throw new EmptyPasswordException();
		}

		String key = null;
		while (true) {
			key = KeyGeneratorUtil.generateKey(4);
			if (!userDAO.keyAlreadyExists(key))
				break;
		}
		User user = new User(fname,lname,email,password);
		user.setKey(key);
		userDAO.createUser(user);
		return user;
	}
	
	public void searchUser(String query,int page) throws SQLException, UserFoundException{
		int limit = 10;
		if (1 > page) {
			limit = -1;
			page = 0;
		} else {
			page *= limit;
		}

		userDAO.searchUser(query, page, limit, false);
	}

	public void getUserById(String id) throws HexadecimalException, SQLException, InvalidUserKeyException, UserNotFoundException, UserFoundException{
		boolean isHexa=true;
		if(id.length()==8){
			for(int i=0; i<8;i++){
				if(Character.digit(id.charAt(i),16)==-1)
					isHexa=false;
				break;
				//pas hexa
			}
			
			if(isHexa){
				
					userDAO.getUserById(id);
				
			}
			else{
				throw new HexadecimalException();
			}
		}

		else{
			throw new InvalidUserKeyException();
		}
	}

	public void listUserMetaData(String id) throws UserNotFoundException, SQLException, HexadecimalException, InvalidUserKeyException, UserFoundException{
		boolean isHexa=true;
		if(id.length()==8){
			for(int i=0; i<8;i++){
				if(Character.digit(id.charAt(i),16)==-1)
					isHexa=false;
				break;
				//pas hexa
			}
			
			if(isHexa){
				try{
					userDAO.getUserById(id);
				}
				catch(UserFoundException e){
					throw new UserFoundException();
					//Passer les infos utilisateurs ici
				}
				
			}
			else{
				throw new HexadecimalException();
			}
		}

		else{
			throw new InvalidUserKeyException();
		}
	}

	public void updateUser(String id, String fname, String lname, String email, String password) throws SQLException{

		// Si connecté, alors on met à jour ses données.
		userDAO.updateUser(id, fname, lname, email, password);
		/*
		 * @DefaultValue("Null") @FormParam("fname") String fname,
					@DefaultValue("Null") @FormParam("lname") String lname,
					@DefaultValue("Null") @FormParam("email") String email,
					@DefaultValue("Null") @FormParam("password") String password
		 * */

		// Sinon, on le connecte.
		userDAO.getUserByCredentials(email, password);
		/*
					@DefaultValue("Null") @FormParam("email") String email,
					@DefaultValue("Null") @FormParam("password") String password
		 * */

	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validateEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
	}

	public String createSession(User user) {
		Session sessionTmp = new Session(user.getKey(),"");
		
		//TODO exception insertion memcache
		//TODO insertion sessionTMP dans memecach
		
		return sessionTmp.getSessionId();
		
	}

}
