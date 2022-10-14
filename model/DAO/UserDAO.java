package model.DAO;

import model.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends BaseDAO<User>{
	
	public boolean inserir (User user) {
		String sql = "INSERT INTO tb_user  (nome,user,senha,nivel) VALUES (?,?,?,?);";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, user.getNome());
			pst.setString(2, user.getUser() );
			pst.setString(3, user.getSenha());
			pst.setInt(4, user.getNivel());
			pst.execute();
			return true;		
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}				
	}
	
	public boolean deletar(User user) {
		String sql = "DELETE FROM tb_user WHERE nome=?;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, user.getNome());
			pst.execute();
			
			return true;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public boolean alterar(User user) {
		String sql = "UPDATE tb_user SET nome=?,user=?,senha=?,nivel=? WHERE nome=? ";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, user.getNome());
			pst.setString(2, user.getUser() );
			pst.setString(3, user.getSenha());
			pst.setInt(4, user.getNivel());
			pst.setString(5, user.getNome());
			pst.executeUpdate();
			return true;		
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
		
	}
	
	public User findById(User user) {
		String sql = "SELECT * FROM tb_user WHERE id=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setInt(1, user.getId());
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				User a = new User();
				a.setNome(rs.getString("nome"));
				a.setSenha(rs.getString("senha"));
				a.setUser(rs.getString("user"));
				a.setNivel(rs.getInt("nivel"));
				a.setId(user.getId());
				return a;
			}
			else return null;
		
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ResultSet findAll() {
		String sql = "SELECT * FROM tb_user;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			return rs;
		
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ResultSet findBySpecifiedField(User user, String field) {
		String sql = "SELECT * FROM tb_user WHERE " + field +"=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			switch (field) {
			case "nome":
				pst.setString(1, user.getNome());
				break;
				
			case "user":
				pst.setString(1, user.getUser());
				break;
				
			case "senha":
				pst.setString(1, user.getSenha());
				break;
				
			case "nivel":
				pst.setInt(1, user.getNivel());
				break;
			
			default: 
				pst.setInt(1, user.getId());
			}
			
			ResultSet rs = pst.executeQuery();
			return rs;
		
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public User buscar(User user) {
		String sql = "SELECT * FROM tb_user WHERE nome=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, user.getNome());
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				return user;
			}
			else return null;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ResultSet buscar() {
		String sql = "SELECT * FROM tb_user;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			return rs;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}
