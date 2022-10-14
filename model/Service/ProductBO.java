package POO-G3.model.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import POO-G3.model.entity.Produto;
import POO-G3.model.DAO.ProdutoDAO;
import POO-G3.model.DAO.ProdutoDAO;

public class ProductBO {
	ProdutoDAO<Produto> dao = new ProdutoDAO();
	
	
	public boolean inserir (Produto produto){
		ResultSet result = dao.findById(produto);
		if (result == null || !(result.next())) {
			if (dao.inserir(produto) == true)
				return true;
			  else return false;
		}
		else return false;
	}
}
	public boolean deletar (Produto produto) {
		ResultSet result = dao.findById(produto);
		if(result != null && result.next()) {
			if (dao.deletar(produto) == true) {
				return true;
				else return false;
			}
			else return false;
		}
	}
	
	public boolean alterar (Produto produto){
		ResultSet result = dao.findById(produto);
		if (result == null || !(result.next())) {
			if (dao.buscar(produto) == true)
				return true;
			  else return false;
		}
		else return false;
	}
}
	
	public boolean buscar (Produto produto){
		ResultSet result = dao.findById(produto);
		if (result == null || !(result.next())) {
			if (dao.alterar(produto) == true)
				return true;
			  else return false;
		}
		else return false;
	}
}