package model.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Funcionario;
import model.entity.Gerente;
import model.entity.Usuario;
import model.DAO.BaseInterDAO;
import model.DAO.UsuarioDAO;

public class UsuarioBO {
	BaseInterDAO<Usuario> dao = new UsuarioDAO();
	
	
	private boolean ExisteNoBD(Usuario usuario) {
		ResultSet existe = dao.encontrarPorCampoEspecifico(usuario, "email");
		try { 
			return existe != null && existe.next();
			
			}catch(SQLException e){
				e.printStackTrace();
				return false;
			}
	}
	
	private boolean NaoExisteNoBD(Usuario usuario) {
		ResultSet existe = dao.encontrarPorCampoEspecifico(usuario, "email");
		try { 
			return existe == null || !(existe.next());
			
			}catch(SQLException e){
				e.printStackTrace();
				return false;
			}
	}
	
	public Usuario autenticar(Usuario usuario) {
		if (this.ExisteNoBD(usuario)) {
			ResultSet rs = dao.encontrarPorCampoEspecifico(usuario, "email");
			Usuario user = new Usuario();
			try {
				while(rs.next()) {
					user.setEmail(rs.getString("email"));
					user.setSenha(rs.getString("senha"));
					user.setNome(rs.getString("nome"));
					user.setNivel(rs.getInt("nivel"));
				}
				if (user.getSenha().equals(usuario.getSenha())) {
					//nivel 1 é gerente. nivel 2 é funcionario.
					if (user.getNivel() == 1) {
						Usuario gerente = new Gerente();
						gerente.setEmail(user.getEmail());
						gerente.setNivel(user.getNivel());
						gerente.setNome(user.getNome());
						gerente.setSenha(user.getSenha());
						return gerente;
					}else {
						if (user.getNivel() == 2) {
							Usuario funcionario = new Funcionario();
							funcionario.setEmail(user.getEmail());
							funcionario.setNivel(user.getNivel());
							funcionario.setNome(user.getNome());
							funcionario.setSenha(user.getSenha());
							return funcionario;
						}
					}
				 	
				};
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public boolean inserir (Usuario usuario){
		if (this.NaoExisteNoBD(usuario)) {
			if (dao.inserir(usuario) == true) return true;
			else return false;
		}else return false;
	}
	
	public boolean deletar (Usuario usuario) {
		if(this.ExisteNoBD(usuario)) {
			if (dao.deletar(usuario) == true)return true;
			else return false;
		}else return false;
		
	}
	
	public boolean alterar (Usuario usuario){
		if (this.ExisteNoBD(usuario)) {
			if (dao.alterar(usuario) == true)return true;
			else return false;
		}else return false;
	}

	public List<Usuario> listarPorCampoEspecifico(Usuario usuario, String campo){
		//Não será usado na implementação
		List<Usuario> Usuarios = new ArrayList<Usuario>();
		return Usuarios;
		
	}

	public List<Usuario> listarTodos(){
		//Não será usado na implementação
		List<Usuario> Usuarios = new ArrayList<Usuario>();
		return Usuarios;
	
	}

}