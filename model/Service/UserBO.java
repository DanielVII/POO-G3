package model.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.User;
import model.DAO.BaseInterDAO;
import model.DAO.UserDAO;

public class UserBO {
	BaseInterDAO<User> dao = new UserDAO();
	
	
	public boolean inserir (User user){
		ResultSet result = dao.findBySpecifiedField(user, "nome");
		if (result == null || !(result.next())) {
			if (dao.inserir(user) == true)
				return true;
			  else return false;
		}
		else return false;
	}

	public boolean deletar (User user) {
		ResultSet result = dao.findBySpecifiedField(user, "nome");
		if(result != null && result.next()) {
			if (dao.deletar(user) == true) {
				return true;
			}
			else return false;
		}
		else return false;
	}
	
	public boolean alterar (User user){
		ResultSet result = dao.findBySpecifiedField(user, "nome");
		if (result == null || !(result.next())) {
			if (dao.alterar(user) == true)
				return true;
			  else return false;
		}
		else return false;
	}

	
	public boolean buscar (User user){
		ResultSet result = dao.findBySpecifiedField(user, "nome");
		if (result == null || !(result.next())) {
			if (dao.alterar(user) == true)
				return true;
			  else return false;
		}
		else return false;
	}
}