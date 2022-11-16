package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.Telas;
import model.Service.ProdutoBO;
import model.entity.Produto;
import java.util.List;

public class FuncionarioController {
	@FXML private Label nomeUsuario;
	@FXML private TextField buscar;
	@FXML private TextField quantidade;
	@FXML private Label preco;
	@FXML private Label precoTotal;
	@FXML private Label mensagemErro;
	double quantAtual,quant,valor;
	public static String staticNome;
	private static ProdutoBO produtoBO = new ProdutoBO();
	public void initialize() {
		nomeUsuario.setText(staticNome);
		
	}
	
	public void LogOut(ActionEvent event) throws Exception{
		Telas.telaLogin();
	}
	public void Buscar(ActionEvent event) throws Exception{
		Produto produto = new Produto();
		produto.setCodBarras(buscar.getText());
		List<Produto> produtoColetado= produtoBO.listarPorCampoEspecifico(produto,"cod_de_barras");
		preco.setText(String.valueOf(produtoColetado.get(0).getPreco()));
		valor = produtoColetado.get(0).getPreco();
		quantAtual = produtoColetado.get(0).getQuantidade();
		
	}
	public void calcularPrecoTotal(ActionEvent event) throws Exception{
		quant = (Integer.valueOf(quantidade.getText()));
		if(quant <= quantAtual) {
			mensagemErro.setVisible(false);
			precoTotal.setText(String.valueOf(valor*quant));
		}
		else {mensagemErro.setVisible(true);}
	}
	public void adicionarProduto(ActionEvent event) throws Exception{
		
	}
}
