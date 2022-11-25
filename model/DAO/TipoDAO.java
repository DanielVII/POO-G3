package model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entity.Tipo;

public class TipoDAO extends BaseDAO<Tipo>{
	public boolean inserir (Tipo tipo) {
		String sql = "INSERT INTO tipos (nome,forma_de_venda) VALUES (?,?);";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, tipo.getNome());
			pst.setString(2, tipo.getFormaDeVenda() );
			pst.execute();
			return true;		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}				
	}
	
    public boolean deletar(Tipo tipo) {
		String sql = "DELETE FROM tipos WHERE nome=?;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, tipo.getNome());
			pst.execute();
			
			return true;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
    
    public boolean alterar(Tipo tipo) {
		String sql = "UPDATE tipos SET nome=?,forma_de_venda=? WHERE id_tipo=? ";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, tipo.getNome());
			pst.setString(2, tipo.getFormaDeVenda() );
			pst.setInt(3, tipo.getId());
			pst.executeUpdate();
			return true;		
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
		
	}

	@Override
	public ResultSet encontrarPorCampoEspecifico(Tipo tipo, String field) {
		String sql = "SELECT * FROM tipos WHERE " + field +"=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			switch (field) {
			
			case "nome":
				pst.setString(1, tipo.getNome());
				break;
				
			case "forma_de_venda":
				pst.setString(1, tipo.getFormaDeVenda());
				break;

			}
			
			ResultSet rs = pst.executeQuery();
			return rs;
		
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public ResultSet encontrarTodos() {
		String sql = "SELECT * FROM tipos;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			return rs;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public Tipo encontrarPorId(int id_tipo) {
		String sql = "SELECT * FROM tipos WHERE id_tipo=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setInt(1, id_tipo);
			ResultSet rs = pst.executeQuery();
			Tipo tipo = new Tipo();
			while(rs.next()) {
				tipo.setId(rs.getInt("id_tipo"));
				tipo.setNome(rs.getString("nome"));
				tipo.setFormaDeVenda(rs.getString("forma_de_venda"));
			}
			
		
			return tipo;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
