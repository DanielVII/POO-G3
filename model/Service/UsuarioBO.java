package model.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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