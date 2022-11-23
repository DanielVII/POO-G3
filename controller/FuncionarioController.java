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
	
	
	private DecimalFormat df = new DecimalFormat("#.##");
	
	private List<List<Object>> produtosNaComanda = new ArrayList<List<Object>>();// Lista que vai ter uma lista com produto e quantida
																				//dentro [[Produto, int], ...]
	
	public static String staticNome;
	private static ProdutoBO produtoBO = new ProdutoBO();

	private int scroll = 0; // sempre será menor igual a zero
	private int tamanhoAntesDeItensNaComanda;
	
	public void initialize() {
		nomeUsuario.setText(staticNome);
		this.tamanhoAntesDeItensNaComanda = this.PaneFuncionario.getChildren().size();
	}
	
	public void LogOut(ActionEvent event) throws Exception{
		Telas.telaLogin();		
	}


	public void ScrollCima() {
		if(this.produtosNaComanda.size()>5) {
			if(this.produtosNaComanda.size() + this.scroll > 5) { //scroll sempre será menor igual a zero. Quando ele diminuir do size e der 5 quer dizer q ta no limite
				this.scroll -= 1;
				this.ColocarInfoNaComanda();
			}
		}
	}
	
	public void ScrollBaixo() {
		if(this.scroll<0) {//scroll sempre será menor igual a zero.
			this.scroll += 1;
			this.ColocarInfoNaComanda();
		}
	}
	
	private void removeInfo() {
		this.PaneFuncionario.getChildren().remove(
				this.tamanhoAntesDeItensNaComanda, 
				this.PaneFuncionario.getChildren().size()
				);
	}
	
	private boolean ProdutoEstahNaComanda(Produto prod, int quantidade) {
		for (int x=0; x<this.produtosNaComanda.size();x++) {
			Produto prodNaLista = (Produto) this.produtosNaComanda.get(x).get(0);
			if (prodNaLista.getCodBarras().equals(prod.getCodBarras())) {
				int quantAnterior = (int) this.produtosNaComanda.get(x).get(1);
				int quantNova = quantAnterior + quantidade;
				
				List<Object> produtoEQuantidade = new ArrayList<Object>();
				produtoEQuantidade.add(prodNaLista);
				produtoEQuantidade.add(quantNova);
				
				this.produtosNaComanda.set(x, produtoEQuantidade);
				return true;
			}
		}
		return false;
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
			if (!(this.ProdutoEstahNaComanda(produto, quantEscolhido))) {
				ProdutoEQuantidade.add(produto);
				ProdutoEQuantidade.add(quantEscolhido);
				this.produtosNaComanda.add(ProdutoEQuantidade);
			}
			Double valorTotalProd = produto.getPreco() * quantEscolhido;
			this.precoTotalProduto.setText(this.df.format(valorTotalProd));
			
			this.ColocarInfoNaComanda();
		}
	}
	
	private void ColocarInfoNaComanda() {
		this.removeInfo();//limpando para colocar info
		
		Double LX = 500.0;
		Double LY = 185.0;
		Double distancia = 20.0;

		if (this.produtosNaComanda.size() > 0) {
			this.TotalComanda();
			int end, start;
			
			if (this.produtosNaComanda.size()>5) {
				end = this.produtosNaComanda.size() + this.scroll;
				start = end - 5;
			}else {
				end = this.produtosNaComanda.size();
				start = 0;
			}
			for(int x=start;x<end;x++) {
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
				final int posicaoLinha = x;
				bDel.setOnAction(event->{
					this.produtosNaComanda.remove(posicaoLinha);
					this.ColocarInfoNaComanda();
				});
				LX += 45;
				
				Button bEdit = ButtonFabrica(
						"Edit",
						String.valueOf(x),
						LX, LY,
						11,
						40.0
						);
				bEdit.setOnAction(event ->{
					//vai usar posicaoLinha igual ao bDel
				});
				LX -= 220.0 + 45;
				LY += distancia;
				
				Label linha2 = LabelFabrica(
						quantVendida + " X " + produto.getPreco() + " = " + this.df.format(quantVendida * produto.getPreco()),
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
	private void TotalComanda() {
		Double total = 0.0;
		for (int x=0;x<this.produtosNaComanda.size(); x++) {
			Produto prod = (Produto) this.produtosNaComanda.get(x).get(0);
			int quant = (int) this.produtosNaComanda.get(x).get(1);
			Double semiTotal = prod.getPreco() * quant;
			
			total += semiTotal;
		}
		this.precoTotal.setText(this.df.format(total));;
	}
	
	public void FinalizarComanda() {
		//implementar
	}
}
