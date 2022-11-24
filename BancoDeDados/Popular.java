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

	public static void main(String[] args) {
		Popular p = new Popular();
		//Criar usuarios
		p.CriarGerente("Pedrinho", "pedro", "12345678");
		p.CriarFuncionario("Ze", "ze", "12345678");
		

	}
}
