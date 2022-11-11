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
				con = DriverManager.getConnection("jdbc:postgresql://localhost/Mercado","postgres","Elefanterosa");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
		}
		else return con;
	}

	@Override
	public boolean inserir(entity e) {
		return false;
	}

	@Override
	public boolean deletar(entity e) {
		return false;
	}

	@Override
	public boolean alterar(entity e) {
		return false;
	}

	@Override
	public ResultSet encontrarPorCampoEspecifico(entity e, String field) {
		return null;
	}
	
	@Override
	public ResultSet encontrarTodos() {
		return null;
	}

}

