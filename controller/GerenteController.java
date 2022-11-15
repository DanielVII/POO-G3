package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
	public void initialize() {
		nomeUsuario.setText(staticNome);
		this.PegarTodasAsInfoDeProduto();
		this.ColocarInfoNaTela();
		this.ColocarBotoesPag();
		
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
		
		this.PaneGerente.getChildren().remove(22, this.PaneGerente.getChildren().size());
		this.ColocarInfoNaTela();
		this.ColocarBotoesPag();
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
		// vai atualizar a quantidade de acordo com o cod d barras recebido
	}
	
	public void ProdutoNovo() {
		//vai add produto novo no BD
	}

}
