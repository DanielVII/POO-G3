package model.DAO;

import model.entity.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDAO extends BaseDAO<Produto>{
	
	public boolean inserir (Produto produto) {
		String sql = "INSERT INTO tb_produto  (cpf,nome,telefone,endereco) VALUES (?,?,?,?);";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, produto.getNome());
			pst.setString(2, produto.getMarca() );
			pst.setString(3, produto.getCodBarras());
			pst.setDouble(4, produto.getPreco());
			pst.execute();
			return true;		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}				
	}
	
    public boolean deletar(Produto produto) {
		String sql = "DELETE FROM tb_produto WHERE codbarras=?;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, produto.getCodBarras());
			pst.execute();
			
			return true;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
    public boolean alterar(Produto produto) {
		String sql = "UPDATE tb_produto SET nome=?,marca=?,codbarras=?,preco=? WHERE codbarras=? ";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, produto.getNome());
			pst.setString(2, produto.getMarca() );
			pst.setString(3, produto.getCodBarras());
			pst.setDouble(4, produto.getPreco());
			pst.setString(5, produto.getCodBarras());
			pst.executeUpdate();
			return true;		
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
		
	}
	
	public Produto findById(Produto produto) {
		String sql = "SELECT * FROM tb_produto WHERE id=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setInt(1, produto.getId());
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				Produto a = new Produto();
				a.setNome(rs.getString("nome"));
				a.setMarca(rs.getString("marca"));
				a.setCodBarras(rs.getString("codbarras"));
				a.setPreco(rs.getDouble("preco"));
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
		String sql = "SELECT * FROM tb_produto;";
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
	public ResultSet findBySpecifiedField(Produto produto, String field) {
		String sql = "SELECT * FROM tb_produto WHERE " + field +"=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			switch (field) {
			case "nome":
				pst.setString(1, produto.getNome());
				break;
				
			case "marca":
				pst.setString(1, produto.getMarca());
				break;
				
			case "codbarras":
				pst.setString(1, produto.getCodBarras());
				break;
				
			case "preco":
				pst.setDouble(1, produto.getPreco());
				break;
			
			default: 
				pst.setInt(1, produto.getId());
			}
			
			ResultSet rs = pst.executeQuery();
			return rs;
		
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public Produto buscar(Produto produto) {
		String sql = "SELECT * FROM tb_produto WHERE codbarras=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, produto.getCodBarras());
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				return produto;
			}
			else return null;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ResultSet buscar() {
		String sql = "SELECT * FROM tb_produto;";
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
