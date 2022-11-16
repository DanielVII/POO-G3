package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import view.Telas;
import model.Service.ProdutoBO;
import model.entity.Produto;

public class GerenteController{
	@FXML private Pane PaneGerente;
	@FXML private Label nomeUsuario;
	@FXML private Button b1;
	public static String staticNome;
	
	private List<Produto> ListaProdutos = new ArrayList<Produto>();
	private int PaginaAtual = 1;
	
	private int quantItensPagInicial;
	private int quantItensListados;
	public void initialize() {
		nomeUsuario.setText(staticNome);
		this.quantItensPagInicial = PaneGerente.getChildren().size();
		this.PegarTodasAsInfoDeProduto();
		this.ColocarInfoNaTela();
		this.ColocarBotoesPag();
		this.quantItensListados = PaneGerente.getChildren().size();;
		
		
	}
 	
	private void PegarTodasAsInfoDeProduto() {
		ProdutoBO prodBO = new ProdutoBO();
		this.ListaProdutos = prodBO.listarTodos();
	}
	
	
	private void ColocarInfoNaTela() {
		
		int tamanhoList = this.ListaProdutos.size();
		
		Font font = new Font("Arial", 12);
		
		
		Double LayX;
		Double LayY = 182.0;
		int posicaoFinalListaPag = this.PaginaAtual * 5;
		
		int start;
		int end;
		if (tamanhoList - posicaoFinalListaPag < 0) {
			//Esse caso vai ocorrer quando for a ultima pagina e o total d eitens da mesma for - de 5
			int x = posicaoFinalListaPag - tamanhoList;
			end = tamanhoList;
			start = end - (5 - x);
		}else {
			//Vai acontecer quando tiver 5 itens pra cobrir a pagina inteira
			end = posicaoFinalListaPag;
			start = end - 5;
		}
		
		
		Produto prod = new Produto();
		for (int i =start ; i < end; i++) {
			Label nome = new Label();
			nome = this.InfoBaseLabel(nome, font);
			Label cod = new Label();
			cod = this.InfoBaseLabel(cod, font);
			Label marca = new Label();
			marca = this.InfoBaseLabel(marca, font);
			Label quant = new Label();
			quant = this.InfoBaseLabel(quant, font);
			Label tipo = new Label();
			tipo = this.InfoBaseLabel(tipo, font);
			Label preco = new Label();
			preco = this.InfoBaseLabel(preco, font);
			
			Button dele = new Button("Del");
			dele.setStyle("-fx-background-color: #cc1515;");
			
			Button edit = new Button("Edit");
			
			prod = this.ListaProdutos.get(i);
			
			LayX = 80.0;
			
			nome.setText(prod.getNome());
			nome.setLayoutX(LayX);
			nome.setLayoutY(LayY);
			
			LayX = LayX + 80;
			
			cod.setText(prod.getCodBarras());
			cod.setLayoutX(LayX);
			cod.setLayoutY(LayY);
			
			LayX = LayX + 80;
			
			marca.setText(prod.getMarca());
			marca.setLayoutX(LayX);
			marca.setLayoutY(LayY);
			
			LayX = LayX + 80;
			
			quant.setText(String.valueOf(prod.getQuantidade()));
			quant.setLayoutX(LayX);
			quant.setLayoutY(LayY);
			
			LayX = LayX + 80;
			
			tipo.setText(prod.getTipo().getNome());
			tipo.setLayoutX(LayX);
			tipo.setLayoutY(LayY);
			
			LayX = LayX + 80;
			
			preco.setText(String.valueOf(prod.getPreco()));
			preco.setLayoutX(LayX);
			preco.setLayoutY(LayY);
			
			LayX += 70;
			
			dele = this.InfoBaseButton(dele, font);
			dele.setId(prod.getCodBarras());
			dele.setLayoutX(LayX);
			dele.setLayoutY(LayY);
			dele.setOnAction(action -> Deletar(action));
			
			LayX += 38;
			
			edit = this.InfoBaseButton(edit, font);
			edit.setId(prod.getCodBarras());
			edit.setLayoutX(LayX);
			edit.setLayoutY(LayY);
			edit.setOnAction(action -> Editar(action));
			
			
			this.PaneGerente.getChildren().addAll(nome,cod,marca,quant,tipo,preco,dele,edit);
			
			LayY += 45;
		}
		
		
	}
	
	
	private void ColocarBotoesPag(){
		int tamanhoList = this.ListaProdutos.size();
		Double totalBotoes = tamanhoList/5.0;
		int totalBotoesInt = totalBotoes.intValue();
		if (totalBotoes <= 1) return; // esse caso significa q só existe uma pagina, então a função para por aqui
		else{
			if (totalBotoes == totalBotoesInt) {
				totalBotoesInt -= 1; // como já existe um botão na pagina inicial, é preciso tirar ele
			}
		}
		Double LX = 102.0;
		Double LY = 392.0;
		for (Integer i = 0; i < totalBotoesInt; i++) {
			Button b = new Button(Integer.toString(i+2));
			b.setPrefHeight(17.0);
			b.setPrefWidth(14.0);
			b.setAlignment(Pos.CENTER);
			b.setFont(new Font("Arial", 7));
			b.setLayoutX(LX);
			b.setLayoutY(LY);
			b.setStyle("-fx-background-color: #ffffff;");
			b.setOnAction(action -> {
				try {
					this.MudarPagina(action);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			PaneGerente.getChildren().add(b);
			
			LX += 16;
		}
	}
	
	
	private Button InfoBaseButton(Button button, Font font) {
		button.setPrefHeight(25.0);
		button.setPrefWidth(37.0);
		button.setAlignment(Pos.CENTER);
		button.setFont(font);
		
		return button;
	}
	
	
	private Label InfoBaseLabel(Label label, Font font) {
		label.setPrefHeight(17.0);
		label.setPrefWidth(60.0);
		label.maxWidth(60.0);
		label.setAlignment(Pos.CENTER);
		label.setFont(font);
		
		return label;
	}	
	
	public void MudarPagina(ActionEvent e) throws Exception {
		Button b = (Button) e.getSource();
		this.PaginaAtual = Integer.parseInt(b.getText()); 
		this.PaneGerente.getChildren().remove(this.quantItensPagInicial,  this.quantItensListados);;
		
		this.ColocarInfoNaTela();
		this.ColocarBotoesPag();
		this.quantItensListados = PaneGerente.getChildren().size();;
	}
	
	public void LogOut(ActionEvent event) throws Exception{
		Telas.telaLogin();
	}

	
	public void Deletar(ActionEvent e) {
		Button b = (Button) e.getSource();
		Produto prod = new Produto();
		
		prod.setCodBarras(b.getId());
		
	}
	
	public void Editar(ActionEvent e) {
		Button b = (Button) e.getSource();
		Produto prod = new Produto();
		
		prod.setCodBarras(b.getId());
	}
	
	public void RemersaNova() {
		ImageView img = new ImageView();
		img.setFitHeight(283.0);
		img.setFitWidth(556.0);
		img.setLayoutX(77.0);
		img.setLayoutY(133.0);
		img.setPickOnBounds(true);
		img.setPreserveRatio(true);
		img.setImage(new Image("view/ve/RectangleSecundario.png"));
		
		Label titulo = new Label("Nova Remessa");
		titulo.setLayoutX(298.0);
		titulo.setLayoutY(147.0);
		titulo.setFont(new Font("Arial", 18.0));
		
		this.PaneGerente.getChildren().addAll(img, titulo);
		
		List<String> idTextF = new ArrayList<String>();
		idTextF.add("cod");
		idTextF.add("quant");
		
		List<String> textLabel = new ArrayList<String>();
		textLabel.add("Cod de barras");
		textLabel.add("quantidade");
		
		Double LY = 194.0;
		Font font = new Font("Arial", 12);
		for (int n = 0; n<2;n++) {
			Label l = new Label(textLabel.get(n));
			l.setLayoutX(230.0);
			l.setLayoutY(LY);
			l.setFont(font);
			l.setPrefHeight(17.0);
			l.setPrefWidth(90.0);
			
			TextField tf = new TextField();
			tf.setId(idTextF.get(n));
			tf.setLayoutX(312);
			tf.setLayoutY(LY);
			tf.setPrefHeight(17.0);
			tf.setPrefWidth(150);
			
			this.PaneGerente.getChildren().addAll(l, tf);
			LY += 34;
		}
		
		Button bV = new Button("Voltar");
		bV.setPrefWidth(50.0);
		bV.setLayoutX(273);
		bV.setLayoutY(291);
		bV.setOnAction(event -> {
			this.PaneGerente.getChildren().remove(this.quantItensListados, this.PaneGerente.getChildren().size());
			
		});
		
		Button bMudar = new Button("Atualizar");
		bMudar.setPrefWidth(50.0);
		bMudar.setLayoutX(327);
		bMudar.setLayoutY(291);
		bMudar.setOnAction(event -> {
			TextField tFCode = (TextField) this.PaneGerente.getChildren().get(this.quantItensListados + 3);
			Produto prod = new Produto();
			prod.setCodBarras(tFCode.getText());
			
			
			ProdutoBO bo = new ProdutoBO();
			if (bo.ExisteNoBD(prod)) {
				List<Produto> lProd = bo.listarPorCampoEspecifico(prod, "cod_de_barras");
				
				prod = lProd.get(0);
				TextField tFQuanti = (TextField) this.PaneGerente.getChildren().get(this.quantItensListados + 5);
				Integer quantidade = Integer.parseInt(tFQuanti.getText());
				System.out.println(quantidade);
				prod.setQuantidade(prod.getQuantidade()+quantidade);
				
				bo.alterar(prod);
				this.PaneGerente.getChildren().remove(this.quantItensPagInicial, this.PaneGerente.getChildren().size());
				this.PegarTodasAsInfoDeProduto();
				this.ColocarInfoNaTela();
				this.ColocarBotoesPag();
			}else {
				Label msgErro = new Label();
				msgErro.setLayoutX(300.0);
				msgErro.setLayoutY(260.0);
				msgErro.setFont(font);
				
				this.PaneGerente.getChildren().add(msgErro);
			}
			
			
		});
		this.PaneGerente.getChildren().addAll( bV, bMudar);
	}

	
	
	public void ProdutoNovo() {
		//vai add produto novo no BD
	}

}
