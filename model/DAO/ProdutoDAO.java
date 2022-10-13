package model.DAO;

import model.entity.Produto;
import model.DAO.BaseDAO;

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
			pst.setDouble(4, produto.getTipo());
			pst.execute();
			return true;		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}				
	}
	
	public boolean deletar(Aluno aluno) {
		String sql = "DELETE FROM tb_aluno WHERE cpf=?;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, aluno.getCpf());
			pst.execute();
			
			return true;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	public boolean alterar(Aluno aluno) {
		String sql = "UPDATE tb_aluno SET cpf=?,nome=?,telefone=?,endereco=? WHERE cpf=? ";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, aluno.getCpf());
			pst.setString(2, aluno.getNome() );
			pst.setString(3, aluno.getTelefone());
			pst.setString(4, aluno.getEndereco());
			pst.setString(5, aluno.getCpf());
			pst.executeUpdate();
			return true;		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
		
	}
	
	public Aluno findById(Aluno e) {
		String sql = "SELECT * FROM tb_aluno WHERE id=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setInt(1, e.getId());
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				Aluno a = new Aluno();
				a.setCpf(rs.getString("cpf"));
				a.setEndereco(rs.getString("endereco"));
				a.setNome(rs.getString("nome"));
				a.setTelefone(rs.getString("telefone"));
				a.setId(e.getId());
				return a;
			}
			else return null;
		
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ResultSet findAll() {
		String sql = "SELECT * FROM tb_aluno;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			return rs;
		
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public ResultSet findBySpecifiedField(Aluno e, String field) {
		String sql = "SELECT * FROM tb_aluno WHERE " + field +"=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			switch (field) {
			case "cpf":
				pst.setString(1, e.getCpf());
				break;
				
			case "nome":
				pst.setString(1, e.getNome());
				break;
				
			case "telefone":
				pst.setString(1, e.getTelefone());
				break;
				
			case "endereco":
				pst.setString(1, e.getEndereco());
				break;
			
			default: 
				pst.setInt(1, e.getId());
			}
			
			ResultSet rs = pst.executeQuery();
			return rs;
		
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}
	}
	
	public Aluno buscar(Aluno aluno) {
		String sql = "SELECT * FROM tb_aluno WHERE cpf=? ;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			pst.setString(1, aluno.getCpf());
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				return aluno;
			}
			else return null;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ResultSet buscar() {
		String sql = "SELECT * FROM tb_aluno;";
		try {
			PreparedStatement pst = getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			return rs;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	

}
