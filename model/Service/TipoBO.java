package model.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DAO.BaseInterDAO;
import model.DAO.TipoDAO;
import model.entity.Tipo;

public class TipoBO implements BaseInterBO<Tipo>{
	BaseInterDAO<Tipo> dao = new TipoDAO();
	
	private boolean ExisteNoBD(Tipo tipo) {
		ResultSet existe = dao.encontrarPorCampoEspecifico(tipo, "nome");
		try { 
			return existe != null && existe.next();
			
			}catch(SQLException e){
				e.printStackTrace();
				return false;
			}
	}
	 
	private boolean NaoExisteNoBD(Tipo tipo) {
		ResultSet existe = dao.encontrarPorCampoEspecifico(tipo, "nome");
		try { 
			return existe == null || !(existe.next());
			
			}catch(SQLException e){
				e.printStackTrace();
				return false;
			}
	}
	
	public boolean inserir (Tipo tipo){
		if (this.NaoExisteNoBD(tipo)) {
			if (dao.inserir(tipo) == true) return true;
			else return false;
		}else return false;
	}
	
	public boolean deletar (Tipo tipo) {
		if(this.ExisteNoBD(tipo)) {
			if (dao.deletar(tipo) == true)return true;
			else return false;
		}else return false;
		
	}
	
	public boolean alterar (Tipo tipo){
		if (dao.alterar(tipo) == true)return true;
		else return false;
	}

	public List<Tipo> listarPorCampoEspecifico(Tipo tipo, String campo){
		List<Tipo> Tipos = new ArrayList<Tipo>();
		ResultSet rs = dao.encontrarPorCampoEspecifico(tipo, campo);
		try {
			while(rs.next()) {
				Tipo tipoLista = new Tipo();
				tipoLista.setId(rs.getInt("id_tipo"));
				tipoLista.setNome(rs.getString("nome"));
				tipoLista.setFormaDeVenda(rs.getString("forma_de_venda"));
				Tipos.add(tipoLista);
			}
			return Tipos;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Tipo> listarTodos(){
		List<Tipo> Tipos = new ArrayList<Tipo>();
		ResultSet rs = dao.encontrarTodos();
		try {
			while(rs.next()) {
				Tipo tipoLista = new Tipo();
				tipoLista.setId(rs.getInt("id_tipo"));
				tipoLista.setNome(rs.getString("nome"));
				tipoLista.setFormaDeVenda(rs.getString("forma_de_venda"));
				
				Tipos.add(tipoLista);
			}
			return Tipos;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Tipo BuscarTodaInfoSoComId(int id) {
		//Feito para usar quando o produtoBO pegar as infos do BD, nesse situação ele vai ter só o id do tipo para trabalhar.
		TipoDAO daoTipo = new TipoDAO();
		if (id>0) return daoTipo.encontrarPorId(id);
		else return null;
		
	}
}
