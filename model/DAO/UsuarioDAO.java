package model.DAO;

import model.entity.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO extends BaseDAO<Usuario>{
	
	public boolean inserir (Usuario user) {
		String sql = "INSERT INTO usuarios (nome,email,senha,nivel) VALUES (?,?,?,?);";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, user.getNome());
			pst.setString(2, user.getEmail() );
			pst.setString(3, user.getSenha());
			pst.setInt(4, user.getNivel());
			pst.execute();
			return true;		
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}				
	}
	
	public boolean deletar(Usuario user) {
		String sql = "DELETE FROM usuarios WHERE email=?;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, user.getEmail());
			pst.execute();
			
			return true;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean alterar(Usuario user) {
		String sql = "UPDATE usuarios SET nome=?,email=?,senha=?,nivel=? WHERE email=? ";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, user.getNome());
			pst.setString(2, user.getEmail() );
			pst.setString(3, user.getSenha());
			pst.setInt(4, user.getNivel());
			pst.setString(5, user.getEmail());
			pst.executeUpdate();
			return true;		
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
		
	}

	@Override
	public ResultSet encontrarPorCampoEspecifico(Usuario user, String field) {
		String sql = "SELECT * FROM usuarios WHERE " + field +"=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			switch (field) {
			case "nome":
				pst.setString(1, user.getNome());
				break;
				
			case "email":
				pst.setString(1, user.getEmail());
				break;
				
			case "nivel":
				pst.setInt(1, user.getNivel());
				break;

			}
			
			ResultSet rs = pst.executeQuery();
			return rs;
		
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ResultSet encontrarTodos() {
		String sql = "SELECT * FROM usuarios;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			return rs;
		
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
