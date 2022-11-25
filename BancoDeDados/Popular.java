package BancoDeDados;

import model.entity.*;

import java.util.ArrayList;
import java.util.List;

import model.Service.*;

public class Popular {
	private ProdutoBO prodBO = new ProdutoBO();
	private TipoBO tipBO = new TipoBO();
	private UsuarioBO userBO = new UsuarioBO();

	public void CriarGerente(String nome, String email, String senha) {
		Usuario user = new Usuario();
		user.setEmail(email);
		user.setNome(nome);
		user.setSenha(senha);
		user.setNivel(1);

		this.userBO.inserir(user);
	}

	public void CriarFuncionario(String nome, String email, String senha) {
		Usuario user = new Usuario();
		user.setEmail(email);
		user.setNome(nome);
		user.setSenha(senha);
		user.setNivel(2);

		this.userBO.inserir(user);
	}
	
	public void CriarTipos(String nome, String forma_de_venda) {
		Tipo tipo = new Tipo();
		tipo.setNome(nome);
		tipo.setFormaDeVenda(forma_de_venda);
		
		this.tipBO.inserir(tipo);
	}

	public void CriarProduto(String nome, String marca, String cod_de_barras, double quantidade, double preco, int tip) {
		Produto prod = new Produto();
		prod.setNome(nome);
		prod.setMarca(marca);
		prod.setCodBarras(cod_de_barras);
		prod.setQuantidade(quantidade);
		prod.setPreco(preco);
		
		int idTipo = 1;
		Tipo tipo = new Tipo();
		TipoBO bo = new TipoBO();
		
		tipo = bo.BuscarTodaInfoSoComId(idTipo);
		
		prod.setTipo(tipo);
		
		ProdutoBO produto = new ProdutoBO();
		this.prodBO.inserir(prod);
	}
	public static void main(String[] args) {
		Popular p = new Popular();
		//Criar usuarios
		p.CriarGerente("Pedrinho", "pedro", "12345678");
		p.CriarFuncionario("Ze", "ze", "12345678");
		
		p.CriarTipos("comida", "u");
		p.CriarTipos("bebida", "u");
		
		p.CriarProduto("bolacha", "original", "111", 8, 10.0, 1);
		p.CriarProduto("biscoito", "falsificada", "222", 11, 20, 1);
		p.CriarProduto("refri", "coca", "333", 20, 15, 2);

	}
}
