package com.dant.business;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.mariadb.jdbc.internal.util.dao.QueryException;

import com.dant.controller.InvalidTokenException;
import com.dant.dao.UserDAO;
import com.dant.entity.Session;
import com.dant.entity.User;
import com.dant.exception.*;
import com.dant.exception.EmptyEmailException;
import com.dant.exception.EmptyEmailExceptionMapper;
import com.dant.exception.EmptyNameException;
import com.dant.exception.EmptyNameExceptionMapper;
import com.dant.exception.EmptyPasswordException;
import com.dant.exception.HexadecimalException;
import com.dant.exception.HexadecimalExceptionMapper;
import com.dant.exception.InvalidUserKeyException;
import com.dant.exception.InvalidUserKeyExceptionMapper;
import com.dant.exception.QueryExceptionMapper;
import com.dant.exception.SQLExceptionMapper;
import com.dant.exception.UserFoundException;
import com.dant.exception.UserNotFoundException;
import com.dant.exception.UserNotFoundExceptionMapper;
import com.dant.util.KeyGeneratorUtil;

public class UserBusiness {

	private UserDAO userDAO = new UserDAO();
	private SessionManager sm = new SessionManager();

	public String createUser(String fname, String lname, String email, String password) throws SQLException, EmailException, EmptyNameException, EmptyEmailException, EmptyPasswordException, InvalidEmailException {

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
		String sessionId = createSession(user);
		return sessionId;
	}

	public String searchUser(String query,int page) throws SQLException{
		int limit = 10;
		if (1 > page) {
			limit = -1;
			page = 0;
		} else {
			page *= limit;
		}
		return userDAO.searchUser(query, page, limit, true);
	}

	public User getUserBySessionId(String sessionId) throws HexadecimalException, SQLException, InvalidUserKeyException, UserNotFoundException{
		boolean isHexa=true;
		if(sessionId.length()==8){
			for(int i=0; i<8;i++){
				if(Character.digit(sessionId.charAt(i),16)==-1)
					isHexa=false;
				break;
				//pas hexa
			}

			if(isHexa){

				return userDAO.getUserById(getUser(sessionId));

			}
			else{
				throw new HexadecimalException();
			}
		}

		else{
			throw new InvalidUserKeyException();
		}
	}
	
	public User getUserById(String id) throws HexadecimalException, SQLException, InvalidUserKeyException, UserNotFoundException{
		boolean isHexa=true;
		if(id.length()==8){
			for(int i=0; i<8;i++){
				if(Character.digit(id.charAt(i),16)==-1)
					isHexa=false;
				break;
				//pas hexa
			}

			if(isHexa){

				return userDAO.getUserById(id);

			}
			else{
				throw new HexadecimalException();
			}
		}

		else{
			throw new InvalidUserKeyException();
		}
	}

	public User listUserMetaData(String id) throws UserNotFoundException, SQLException, HexadecimalException, InvalidUserKeyException, UserFoundException{
		boolean isHexa=true;
		if(id.length()==8){
			for(int i=0; i<8;i++){
				if(Character.digit(id.charAt(i),16)==-1)
					isHexa=false;
				break;
				//pas hexa
			}

			if(isHexa){


				return userDAO.getUserById(id);


			}
			else{
				throw new HexadecimalException();
			}
		}

		else{
			throw new InvalidUserKeyException();
		}
	}

	public String updateUser(String sessionId, String fname, String lname, String email, String password) throws SQLException, EmptyEmailException, InvalidEmailException, EmptyNameException, EmptyPasswordException, HexadecimalException, UserNotFoundException, InvalidUserKeyException, QueryException{



		if(sessionId.length()==8){
			if(getUser(sessionId).length()==0){
				//Session expirée
				throw new InvalidTokenException();
			}
			else{
				//X-token donc update infos
				
				if(email.length()==0){
					throw new EmptyEmailException();
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
				if(sm.getSession(id).length()==0){
							if(userDAO.getUserByCredentials(email, password)){
								Session tmp = new Session(id,"");
								sm.storeSession(tmp);
								sm.storeUserSession(id, tmp.getSessionId());
								return tmp.getSessionId();
							}
						}
						else
						{
							userDAO.updateUser(id, fname, lname, email, password);
						}

					}
					
				}

				else{
					throw new InvalidUserKeyException();
				}
	
			
		
	}
		else
			{
		
		
		
		return "";




	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validateEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
	}

	public String createSession(User user) {
		Session sessionTmp = new Session(user.getKey(),"");
		sm.storeSession(sessionTmp);
		sm.storeUserSession(user.getKey(),sessionTmp.getSessionId());
		return sessionTmp.getSessionId();
		//TODO exception insertion memcache
	}

	public String getSession(String ukey){
		return sm.getSession(ukey);
	}
	
	public String getUser(String sessionId){
		return sm.getUserKey(sessionId);
	}

}
