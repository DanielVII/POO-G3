package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import view.Telas;
import model.Service.ProdutoBO;
import model.Service.TipoBO;
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
	
	private List<Produto> produtosNaComanda = new ArrayList<Produto>();
	
	public static String staticNome;
	private static ProdutoBO produtoBO = new ProdutoBO();
	private static TipoBO tipoBO = new TipoBO();

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
	
	private boolean ProdutoEstahNaComanda(Produto prod) {
		
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
		try {
			this.PaneFuncionario.getChildren().remove(this.PaneFuncionario.lookup("#erro3"));
		}catch (Exception e) {
			//aloha
		}
		
		Produto produto = new Produto();
		produto.setCodBarras(buscar.getText());
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
		
		String QuantidadeString = this.quantidade.getText();
		
		
		if(this.ValidarQuantidade(QuantidadeString, produto)) {
			Double quantEscolhido = Double.parseDouble(QuantidadeString);
			
			produto.setQuantidade(quantEscolhido);
			
			Double quantEmEstoque = produto.getQuantidade();
			
			boolean prodComanda = false;
			
			for (int x=0; x<this.produtosNaComanda.size();x++) {
				Produto prodNaLista = this.produtosNaComanda.get(x);
				if (prodNaLista.getCodBarras().equals(produto.getCodBarras())) {
					Double quantAnterior = this.produtosNaComanda.get(x).getQuantidade();
					Double quantNova = quantAnterior + produto.getQuantidade();
					
					if (this.ValidarQuantidade(String.valueOf(quantNova), produto)) {
						produto.setQuantidade(quantNova);	
						this.produtosNaComanda.set(x, produto);
						prodComanda = true;
					}else {
						return;
					}
				}
			}
			if (!prodComanda) {
				this.produtosNaComanda.add(produto);
			}
			Double valorTotalProd = produto.getPreco() * produto.getQuantidade();
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
				Produto produto = (Produto) this.produtosNaComanda.get(x); 
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
				Double LYBEdit = LY;
				bEdit.setOnAction(event ->{
					TextField TF = TextFieldFabrica(
							"TF" + posicaoLinha,
							40.0, 12.0,
							455.0, LYBEdit
							);
					TF.setOnAction(EventHandler -> {
						if (this.ValidarQuantidade(TF.getText(), this.produtosNaComanda.get(posicaoLinha))){
							Double quant = Double.parseDouble(TF.getText());
							this.produtosNaComanda.get(posicaoLinha).setQuantidade(quant);
							this.ColocarInfoNaComanda();
						}
					});
					
					this.PaneFuncionario.getChildren().add(TF);				});
				LX -= 220.0 + 45;
				LY += distancia;
				
				Label linha2 = LabelFabrica(
						produto.getQuantidade() + " X " + produto.getPreco() + " = " + this.df.format(produto.getQuantidade() * produto.getPreco()),
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
	
	private boolean ValidarQuantidade(String quantidade, Produto produto) {
		Double quantEscolhido;
		try {
			 quantEscolhido = Double.valueOf(quantidade);
		}catch (Exception e){
			Label msgErro = LabelFabrica(
					"Quantidade tem que ser numerico",
					100.0,
					360.0,
					12,
					false
					);
			msgErro.setTextFill(Color.RED);
			msgErro.setId("erro4");
			this.PaneFuncionario.getChildren().add(msgErro);
			return false;
		}
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
			return false;}
		
		produto.setTipo(this.tipoBO.BuscarTodaInfoSoComId(produto.getTipo().getId()));
		if (produto.getTipo().getFormaDeVenda().equals("u")) {
			int quantInt = quantEscolhido.intValue();
			if(quantEscolhido - quantInt > 0) {
				Label msgErro = LabelFabrica(
						"Quantidade Escolhida é decimal, o produto é unitario",
						100.0,
						340.0,
						12,
						false
						);
				msgErro.setTextFill(Color.RED);
				msgErro.setId("erro3");
				this.PaneFuncionario.getChildren().add(msgErro);
				return false;
			}
		}
		
		List<Produto> listProd = this.produtoBO.listarPorCampoEspecifico(produto, "cod_de_barras");//Isso é necessario pois o produto fornecido em alguns contextos terão a quantidade existente na comanda, n no bd
		Produto novoProd = listProd.get(0);
		Double quantEmEstoque = novoProd.getQuantidade();

		if(quantEmEstoque < quantEscolhido) {
			Label msgErro = LabelFabrica(
					"Falta estoque",
					100.0,
					380.0,
					12,
					false
					);
			msgErro.setTextFill(Color.RED);
			msgErro.setId("erro5");
			this.PaneFuncionario.getChildren().add(msgErro);
			return false;
		}
		
		return true;
	}
	
	private void TotalComanda() {
		Double total = 0.0;
		for (int x=0;x<this.produtosNaComanda.size(); x++) {
			Produto prod = (Produto) this.produtosNaComanda.get(x);
			Double semiTotal = prod.getPreco() * prod.getQuantidade();
			
			total += semiTotal;
		}
		this.precoTotal.setText(this.df.format(total));;
	}
	
	public void FinalizarComanda() {
		for(int x = 0;x<this.produtosNaComanda.size();x++) {
			Produto prod = (Produto) this.produtosNaComanda.get(x);
			List<Produto> produtoColetado = produtoBO.listarPorCampoEspecifico(prod, "cod_de_barras");
			produtoColetado.get(0).setQuantidade((produtoColetado.get(0).getQuantidade() - prod.getQuantidade()));
			produtoBO.alterar(produtoColetado.get(0));
		}
		this.produtosNaComanda.removeAll(this.produtosNaComanda);
		this.removeInfo();
	}
	
	public void  pesquisar() {
		ImageView IV = ImageFabrica(
				873.0,
				458.0,
				80.0,
				75.0,
				"view/ve/RectanglePrincipal.png"
				);
		
		Label t = LabelFabrica(
					"Pesquisar Produto", 
					80.0, 
					75.0, 
					18, 
					true,
					800.0
					);
		
		Double LX = 330.0;
		Double LY = 120.0;
		Double mudaLY = 30.0;
		Double mudaLX = 20.0;
		
		TextField pesquisa = TextFieldFabrica(
				"pesquisaProd",
				300.0,
				12.0,
				LX, LY
				);
		ChoiceBox<String> tipoEscolhido = ChoiceBoxFabrica(
				"CBpesquisafunc",
				LX + 310, LY,
				80.0
				);
		
		tipoEscolhido.setItems(FXCollections.observableArrayList(
				"Nome", 
				"Cod. Barras", 
				"Marca"));
		
		LY += mudaLY;
		final Double LYErro = LY;
		pesquisa.setOnAction(event->{
			String textoPesquisar = pesquisa.getText();
			String tipoPesquisar = tipoEscolhido.getValue();
			if(textoPesquisar == null || tipoPesquisar == null) {
				Label msgErro = LabelFabrica(
						"Escolha um tipo ou escreva o que deseja pesquisar",
						LX,
						LYErro,
						12,
						false
						);
				msgErro.setTextFill(Color.RED);
				msgErro.setId("erro1");
				this.PaneFuncionario.getChildren().add(msgErro);
				return;
			}else {
				List<Produto> listProd = new ArrayList<Produto>();;
				Produto prod = new Produto();
				switch (tipoPesquisar){
					case "Nome":
						prod.setNome(textoPesquisar);
						listProd = this.produtoBO.listarPorCampoEspecificoIncompleto(prod, "nome");
						break;
					case "Cod. Barras":
						prod.setCodBarras(textoPesquisar);
						listProd = this.produtoBO.listarPorCampoEspecificoIncompleto(prod, "cod_de_barras");
						break;
					case "Marca":
						prod.setMarca(textoPesquisar);
						listProd = this.produtoBO.listarPorCampoEspecificoIncompleto(prod, "marca");
						break;
				}
				Double LYAloha = LYErro;
				
				LYAloha += mudaLY;
				Label lbb = LabelFabrica(
						"Cod. - Nome - Marca - R$Preço",
						LX,
						LYAloha,
						12,
						true,
						300.0
						);
				this.PaneFuncionario.getChildren().add(lbb);
				LYAloha += mudaLY;
				for (int x=0;x<listProd.size();x++) {
					String valorS = String.valueOf(listProd.get(x).getPreco());
					System.out.println(valorS);
					Label lb = LabelFabrica(
							listProd.get(x).getCodBarras() + " - " + 
									listProd.get(x).getNome() + " - "+
									listProd.get(x).getMarca() + " - "+ 
									"R$" + valorS,
							LX,
							LYAloha,
							12,
							true,
							300.0
							);
					this.PaneFuncionario.getChildren().add(lb);
					LYAloha += mudaLY;
				}
			}
			
			
		});
		this.PaneFuncionario.getChildren().addAll(IV, t,pesquisa, tipoEscolhido);
	}
}
