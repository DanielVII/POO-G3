package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDAO<entity> implements BaseInterDAO<entity>{
	private Connection con;
	
	synchronized public Connection getConnection() {
		if(con == null) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost/academia","POO","melhormateria");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
		}
		else return con;
	}

	@Override
	public boolean inserir(entity e) {
		// Para implementar
		return false;
	}

	@Override
	public boolean deletar(entity e) {
		// Para implementar
		return false;
	}

	@Override
	public boolean alterar(entity e) {
		// Para implementar
		return false;
	}

	@Override
	public entity findById(entity e) {
		// Para implementar
		return null;
	}

	@Override
	public ResultSet findAll() {
		// Para implementar
		return null;
	}

	@Override
	public ResultSet findBySpecifiedField(entity e, String field) {
		// Para implementar
		return null;
	}

}

