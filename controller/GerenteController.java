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
import Fabrica.ElementoFxmlFabrica;

public class GerenteController extends ElementoFxmlFabrica{
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
		int TamanhoFont = 12;
		Double LarguraMax = 60.0; 
		Double Largura = 60.0;
		
		for (int i =start ; i < end; i++) {
				
			Button dele = new Button("Del");
			dele.setStyle("-fx-background-color: #cc1515;");
			
			Button edit = new Button("Edit");
			
			prod = this.ListaProdutos.get(i);
			
			LayX = 80.0;
			
			Label nome = LabelFabrica(prod.getNome(), LayX, LayY, TamanhoFont, true, LarguraMax, Largura);
			
			LayX += 80;
			
			Label cod = LabelFabrica(prod.getCodBarras(), LayX, LayY, TamanhoFont, true, LarguraMax, Largura);
			
			LayX += 80;
			
			Label marca = LabelFabrica(prod.getMarca(), LayX, LayY, TamanhoFont, true, LarguraMax, Largura);
			
			LayX += 80;
			
			Label quant = LabelFabrica(String.valueOf(prod.getQuantidade()), LayX, LayY, TamanhoFont, true, LarguraMax, Largura);
			
			LayX += 80;
			
			Label tipo = LabelFabrica(prod.getTipo().getNome(), LayX, LayY, TamanhoFont, true, LarguraMax, Largura);
			
			LayX += 80;
			
			Label preco = LabelFabrica(String.valueOf(prod.getPreco()), LayX, LayY, TamanhoFont, true, LarguraMax, Largura);
			
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
		
		Label titulo = LabelFabrica("Nova Remessa", 298.0, 147.0, 18, false);
		
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
			Label l = LabelFabrica(textLabel.get(n), 230.0, LY, 12, false, 90.0, 90.0);
			
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
