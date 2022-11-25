package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Service.UsuarioBO;
import model.entity.Funcionario;
import model.entity.Gerente;
import model.entity.Usuario;
import view.Telas;
import controller.GerenteController;

public class FrontController {
	@FXML private TextField email;
	@FXML private TextField senha;
	@FXML private Label erroAutenticacao;
	@FXML private Label facaLogin;
	private static UsuarioBO userBo = new UsuarioBO();


	
	public void Auntenticar(ActionEvent event) throws Exception{
		Usuario user = new Usuario();
		user.setEmail(email.getText());
		user.setSenha(senha.getText());
		Usuario userColetado = userBo.autenticar(user);
		if (userColetado instanceof Gerente) {
			GerenteController g = new GerenteController();
			g.staticNome = userColetado.getNome();
			Telas.telaGerente();
		}
		else {
			if (userColetado instanceof Funcionario) {
				FuncionarioController f = new FuncionarioController();
				f.staticNome = userColetado.getNome();
				Telas.telaFuncionario();
			}
			else erroAutenticacao.setVisible(true);
		}
	}
	
	
	

}