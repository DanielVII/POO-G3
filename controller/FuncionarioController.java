package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import view.Telas;
import model.Service.ProdutoBO;
import model.entity.Produto;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Fabrica.ElementoFxmlFabrica;

public class FuncionarioController extends ElementoFxmlFabrica{
	@FXML private Pane PaneFuncionario;
	
	@FXML private Label nomeUsuario;
	@FXML private TextField buscar;
	@FXML private TextField quantidade;
	@FXML private Label NomeProduto;
	@FXML private Label preco;
	@FXML private Label precoTotal;
	@FXML private Label precoTotalProduto;
	@FXML private Label mensagemErro;
	
	
	private List<List<Object>> produtosNaComanda = new ArrayList<List<Object>>();
	
	public static String staticNome;
	private static ProdutoBO produtoBO = new ProdutoBO();

	
	public void initialize() {
		nomeUsuario.setText(staticNome);

	}
	
	public void LogOut(ActionEvent event) throws Exception{
		Telas.telaLogin();		
	}


	public void adicionarProduto(ActionEvent event) throws Exception{
		
		this.preco.setText("0.00");
		this.NomeProduto.setText("nome Produto");
		this.precoTotalProduto.setText("0.00");
		
		//removendo msg de erro da tela
		try {
			this.PaneFuncionario.getChildren().remove(this.PaneFuncionario.lookup("#erro1"));
		}catch (Exception e) {
			//aloha
		}
		
		try {
			this.PaneFuncionario.getChildren().remove(this.PaneFuncionario.lookup("#erro2"));
		}catch (Exception e) {
			//aloha
		}
		
		Produto produto = new Produto();
		produto.setCodBarras(buscar.getText());
		List<Object> ProdutoEQuantidade = new ArrayList<Object>();
		//vendo se cod é valido
		try {
			List<Produto> produtoColetado= produtoBO.listarPorCampoEspecifico(produto,"cod_de_barras");		
			produto = produtoColetado.get(0);
			
			ProdutoEQuantidade.add(produto);
		}catch (Exception e) {
			Label msgErro = LabelFabrica(
					"Cod. de barras não existe no armazém",
					100.0,
					300.0,
					12,
					false
					);
			msgErro.setTextFill(Color.RED);
			msgErro.setId("erro1");
			this.PaneFuncionario.getChildren().add(msgErro);
			return;
		}
		//colocando info na tela
		this.preco.setText(String.valueOf(produto.getPreco()));
		this.NomeProduto.setText(produto.getNome());
		
		
		Integer quantEscolhido = Integer.valueOf(this.quantidade.getText());
		
		if (quantEscolhido <= 0 || this.quantidade.getText() == null) {
			Label msgErro = LabelFabrica(
					"Quantidade tem que ser maior que 0",
					100.0,
					320.0,
					12,
					false
					);
			msgErro.setTextFill(Color.RED);
			msgErro.setId("erro2");
			this.PaneFuncionario.getChildren().add(msgErro);
			return;
		}else {
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.CEILING);
			
			ProdutoEQuantidade.add(quantEscolhido);
			this.produtosNaComanda.add(ProdutoEQuantidade);
			Double valorTotalProd = produto.getPreco() * quantEscolhido;
			this.precoTotalProduto.setText(df.format(valorTotalProd));
			
			this.ColocarInfoNaComanda();
		}
	}
	
	private void ColocarInfoNaComanda() {
		Double LX = 500.0;
		Double LY = 185.0;
		Double distancia = 20.0;
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		if (this.produtosNaComanda.size() > 0) {
			for(int x=0;x<this.produtosNaComanda.size();x++) {
				Produto produto = (Produto) this.produtosNaComanda.get(x).get(0); //o segundo get pega o produto q está dentro de uma lista [produto, quantida]
				Integer quantVendida = (Integer) this.produtosNaComanda.get(x).get(1);
				Label marcaLinha = LabelFabrica(
						x + "|" ,
						LX, LY,
						12,
						false
						);
				
				Label linha = LabelFabrica(
						produto.getCodBarras() + " - " + produto.getNome(),
						LX, LY,
						12,
						true,
						240.0
						);
				LX += 220;
				Button bDel = ButtonFabrica(
						"Del",
						String.valueOf(x),
						LX, LY,
						11,
						40.0,
						"#cc1515"
						);
				LX += 45;
				
				Button bEdit = ButtonFabrica(
						"Edit",
						String.valueOf(x),
						LX, LY,
						11,
						40.0
						);
				
				LX -= 220.0 + 45;
				LY += distancia;
				
				Label linha2 = LabelFabrica(
						quantVendida + " X " + produto.getPreco() + " = " + df.format(quantVendida * produto.getPreco()),
						LX, LY,
						12,
						true,
						240.0
						);
				
				LY += distancia;
				this.PaneFuncionario.getChildren().addAll(marcaLinha, linha, linha2, bDel, bEdit);
			}
		}
		
	}
}
