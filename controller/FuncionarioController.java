package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import view.Telas;

public class FuncionarioController {
	@FXML private Label nomeUsuario;
	public static String staticNome;
	public void initialize() {
		nomeUsuario.setText(staticNome);
		
	}
	
	public void LogOut(ActionEvent event) throws Exception{
		Telas.telaLogin();
	}
}
