package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import view.Telas;
import model.Service.ProdutoBO;
import model.Service.TipoBO;
import model.entity.Produto;
import model.entity.Tipo;
import Fabrica.ElementoFxmlFabrica;

public class GerenteController extends ElementoFxmlFabrica{
	@FXML private Pane PaneGerente;
	@FXML private Label nomeUsuario;
	@FXML private TextField Pesquisa;
	@FXML private ChoiceBox<String> EscolhaPesquisa;
	@FXML private Button pagina1;
	
	public static String staticNome;
	
	private List<Produto> ListaProdutos = new ArrayList<Produto>();
	private int PaginaAtual = 1;
	
	private int quantItensPagInicial;
	private int quantItensListados;
	
	private ProdutoBO prodBO = new ProdutoBO();
	private TipoBO BOTipo = new TipoBO();
	
	public void initialize() {
		nomeUsuario.setText(staticNome);
		this.quantItensPagInicial = PaneGerente.getChildren().size();
		this.GerarTela(true);
		
		this.pagina1.setStyle("-fx-background-color:#d3d3d3 ;");
		
		this.EscolhaPesquisa.setItems(FXCollections.observableArrayList(
				"Nome", 
				"Cod. Barras", 
				"Marca"));
		
	}
	
	private void RemoveInfo(boolean tudo) {
		if (tudo) this.PaneGerente.getChildren().remove(this.quantItensPagInicial,  this.PaneGerente.getChildren().size());
		else this.PaneGerente.getChildren().remove(this.quantItensListados,  this.PaneGerente.getChildren().size());
	}
	
 	private void ColocarInfoNaTela() {
		
		int tamanhoList = this.ListaProdutos.size();
		
		Double LayX;
		Double LayY = 205.0;
		
		int posicaoFinalListaPag = this.PaginaAtual * 5;
		
		int start;
		int end;
		if (tamanhoList - posicaoFinalListaPag < 0) {
			//Esse caso vai ocorrer quando for a ultima pagina e o total de itens da mesma for - de 5
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
		Double LarguraLabel = 80.0;
		boolean Centralizar = true;
		
		Double distanciaEntreElementos = 90.0;
		
		Double LarguraButton = 37.0;
		
		for (int i =start ; i < end; i++) {
		
			prod = this.ListaProdutos.get(i);
			
			LayX = 170.0;
			
			Label nome = LabelFabrica(
					prod.getNome(), 
					LayX, 
					LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel
					);
			
			LayX += distanciaEntreElementos;
			
			Label cod = LabelFabrica(
					prod.getCodBarras(), 
					LayX, LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel
					);
			
			LayX += distanciaEntreElementos;
			
			Label marca = LabelFabrica(
					prod.getMarca(), 
					LayX, 
					LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel
					);
			
			LayX += distanciaEntreElementos;
			
			Label quant = LabelFabrica(
					String.valueOf(prod.getQuantidade()), 
					LayX, 
					LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel);
			
			LayX += distanciaEntreElementos;
			
			Label tipo = LabelFabrica(
					prod.getTipo().getNome(), 
					LayX, 
					LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel
					);
			
			LayX += distanciaEntreElementos;
			
			Label preco = LabelFabrica(
					String.valueOf(prod.getPreco()), 
					LayX, 
					LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel
					);
			
			LayX += 90;
			
			Button dele = ButtonFabrica(
					"Del", 
					prod.getCodBarras(), 
					LayX, 
					LayY, 
					TamanhoFont, 
					LarguraButton, 
					"#cc1515"
					);
			dele.setOnAction(action -> DeletarProduto(action));
			
			LayX += 40;
			
			Button edit = ButtonFabrica(
					"Edit", 
					prod.getCodBarras(), 
					LayX, 
					LayY, 
					TamanhoFont, 
					LarguraButton
					);
			edit.setOnAction(action -> EditarProduto(action));
			
			this.PaneGerente.getChildren().addAll(
					nome,
					cod,
					marca,
					quant,
					tipo,
					preco,
					dele,
					edit
					);
			
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
		Double LX = 179.0;
		Double LY = 421.0;
		
		for (Integer i = 0; i < totalBotoesInt; i++) {
			
			Button b = ButtonFabrica(
					Integer.toString(i+2),
					"pagina"+Integer.toString(i+2),
					LX,
					LY,
					7,
					14.0,
					"#ffffff"
					);
			b.setPrefHeight(17.0);
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
	
	private void GerarTela(boolean ColetarInfoNova) {
		if (ColetarInfoNova) this.ListaProdutos = this.prodBO.listarTodos();
		this.ColocarInfoNaTela();
		this.ColocarBotoesPag();
		this.quantItensListados = PaneGerente.getChildren().size();;
	}
	
	public void MudarPagina(ActionEvent e) throws Exception {
		Button botaoAnterior = (Button) this.PaneGerente.lookup("#pagina"+this.PaginaAtual);
		botaoAnterior.setStyle("-fx-background-color:#ffffff ;");
		
		Button b = (Button) e.getSource();
		this.PaginaAtual = Integer.parseInt(b.getText());
		
		this.RemoveInfo(true);
		
		this.GerarTela(false);
		
		Button botaoFocado = (Button) this.PaneGerente.lookup("#pagina"+this.PaginaAtual);
		botaoFocado.setStyle("-fx-background-color:#d3d3d3 ;");
	}
	
	public void LogOut(ActionEvent event) throws Exception{
		Telas.telaLogin();
	}

	public void DeletarProduto(ActionEvent e) {
		Button b = (Button) e.getSource();
		Produto prod = new Produto();
		
		prod.setCodBarras(b.getId());//id do botão é o cod de barras
		
		List<Produto> prodBD = this.prodBO.listarPorCampoEspecifico(prod, "cod_de_barras");
		
		prod = prodBD.get(0);
		
		this.BaseParaNovaPagina("Voce quer mesmo deletar " + prod.getNome() + "?");
		
		Double LX = 380.0;
		Double LY = 200.0;
		Double TamanhoButton = 80.0;
		
		Button voltar = ButtonFabrica(
				"Voltar",
				"VoltarProduto",
				LX,
				LY,
				12,
				TamanhoButton
				);
		voltar.setOnAction(event ->{
			this.RemoveInfo(false);
		});
		
		LX += 90;
		final Produto prodDel = prod;
		Button deletar = ButtonFabrica(
				"Deletar",
				"DeletarProduto",
				LX,
				LY,
				12,
				TamanhoButton,
				"#cc1515"
				);
		deletar.setOnAction(event->{
			if (this.prodBO.deletar(prodDel)) {
				this.PaginaAtual = 1;
				this.RemoveInfo(true);
				this.GerarTela(true);
			}else {
				Label msgErro = LabelFabrica(
						"Erro no bd",
						360.0,
						180.0,
						12,
						false
						);
				msgErro.setTextFill(Color.RED);
				this.PaneGerente.getChildren().add(msgErro);
			}
		});
		
		this.PaneGerente.getChildren().addAll(deletar, voltar);
		
	}
	
	public void RemersaNova() {
		this.BaseParaNovaPagina("Nova Remessa");;
		
		List<String> idTextF = new ArrayList<String>();
		idTextF.add("cod");
		idTextF.add("quant");
		
		List<String> textLabel = new ArrayList<String>();
		textLabel.add("Cod de barras");
		textLabel.add("Quantidade");
		
		Double LX = 360.0;
		Double LY = 194.0;
		Double distanciaElementos = 100.0;
		
		for (int n = 0; n<2;n++) {
			Label l = LabelFabrica(
					textLabel.get(n), 
					LX, 
					LY,
					12, 
					false, 
					90.0
					);
			
			LX +=distanciaElementos;
			
			TextField tf = TextFieldFabrica(
					idTextF.get(n),
					150.0,
					17.0,
					LX,
					LY
					);
			
			this.PaneGerente.getChildren().addAll(l, tf);
			LX -= distanciaElementos;
			LY += 34;
		}
		
		Double tamanhoBotao = 80.0;
		
		LX += 50;
		Button bV = ButtonFabrica(
				"Voltar",
				"Voltar",
				LX,
				291.0,
				12,
				tamanhoBotao
				);
		bV.setOnAction(event -> {
			this.RemoveInfo(false);
		});
		
		LX += distanciaElementos;
		
		Button bMudar = ButtonFabrica(
				"Atualizar",
				"Atualizar",
				LX,
				291.0,
				12,
				tamanhoBotao,
				"#06FF6A"
				);
		bMudar.setOnAction(event -> {
			TextField tFCode = (TextField) this.PaneGerente.lookup("#cod"); 
			Produto prod = new Produto();
			prod.setCodBarras(tFCode.getText());
			
			List<Produto> lProd = this.prodBO.listarPorCampoEspecifico(prod, "cod_de_barras");
			
			TextField tFQuanti = (TextField) this.PaneGerente.lookup("#quant");
			Double quantidade = Double.parseDouble(tFQuanti.getText());
			int quantInt = quantidade.intValue();
			
			if (lProd.size()>0 && quantidade - quantInt == 0) {
				prod = lProd.get(0);
				prod.setQuantidade(prod.getQuantidade()+quantidade);
				
				this.prodBO.alterar(prod);
				this.RemoveInfo(true);
				this.GerarTela(true);
			}else {
				Label msgErro = LabelFabrica(
						"O cod de barras não existe no armazém ou a quantidade é decimal para produto unitario",
						300.0,
						260.0,
						12,
						false);
				msgErro.setTextFill(Color.RED);
				this.PaneGerente.getChildren().add(msgErro);
			}
		});
		this.PaneGerente.getChildren().addAll( bV, bMudar);
	}
 
	public void EditarProduto(ActionEvent e) {
		Button b = (Button) e.getSource();
		Produto prod = new Produto();
		
		prod.setCodBarras(b.getId());//o id do botão é o cod de barras do produto
		
		List<Produto> prodBD = this.prodBO.listarPorCampoEspecifico(prod, "cod_de_barras");
		
		prod = prodBD.get(0);
		
		List<Double> LXLY = this.BaseTelaNovoEEditarProduto("Editar " + prod.getNome());
		Double LX = LXLY.get(0);
		Double LY = LXLY.get(1);
		
		LX += 100;
		
		//Colocando info do prod a ser editado nos fields
		TextField nomeTF = (TextField) this.PaneGerente.lookup("#FieldNomeProduto");
		nomeTF.setText(prod.getNome());
		
		TextField marcaTF = (TextField) this.PaneGerente.lookup("#FieldMarcaProduto");
		marcaTF.setText(prod.getMarca());
		
		TextField codTF = (TextField) this.PaneGerente.lookup("#FieldCodProduto");
		Double LXCod = codTF.getLayoutX();
		Double LYCod = codTF.getLayoutY() +5;
		this.PaneGerente.getChildren().remove(codTF);
		Label codLabel = LabelFabrica(
				prod.getCodBarras(),
				LXCod, LYCod,
				12,
				false
				);
		this.PaneGerente.getChildren().add(codLabel);
		
		TextField precoTF = (TextField) this.PaneGerente.lookup("#FieldPrecoProduto");
		precoTF.setText(String.valueOf(prod.getPreco()));
		
		TextField quantidadeTF = (TextField) this.PaneGerente.lookup("#FieldQuantidadeProduto");
		quantidadeTF.setText(String.valueOf(prod.getQuantidade()));
		
		ChoiceBox tipoCB = (ChoiceBox) this.PaneGerente.lookup("#ChoiceNomeTipoProduto");
		tipoCB.setValue(prod.getTipo().getNome());
		
		Button Editar = ButtonFabrica(
				"Editar",
				"Editar",
				LX,LY,
				13,
				90.0,
				"#06FF6A"
				);
		Produto prodEditado = new Produto();
		
		prodEditado.setCodBarras(prod.getCodBarras());
		
		Editar.setOnAction(event->{
			//Tratando Nome do produto
			String nomeProd = nomeTF.getText();
			if (nomeProd.isBlank()) {
				this.ErroEmNovoEEditarProd(LY);
				return;
			}
			
			prodEditado.setNome(nomeProd);
			
			//Tratando Marca do Produto
			String marcaProd = marcaTF.getText();
			if (marcaProd.isBlank()) {
				this.ErroEmNovoEEditarProd(LY);
				return;
			}
			
			prodEditado.setMarca(marcaProd);
			
			//Tratando Preço
			String stringPrecoProd = precoTF.getText();
			if (stringPrecoProd.isBlank()) {
				this.ErroEmNovoEEditarProd(LY);
				return;
			}else {
				stringPrecoProd = stringPrecoProd.replaceAll(",", ".");
				try {
					Double doublePrecoProd = Double.parseDouble(stringPrecoProd);
					prodEditado.setPreco(doublePrecoProd);
				}catch (Exception i){
					this.ErroEmNovoEEditarProd(LY);
					return;
				}
			}
			
			//Tratando quantidade
			String stringQuantidadeProd = quantidadeTF.getText();
			if (stringQuantidadeProd.isBlank()) {
				this.ErroEmNovoEEditarProd(LY);
				return;
			}else {
				try {
					Double doubleQuantidadeProd = Double.parseDouble(stringQuantidadeProd);
					prodEditado.setQuantidade(doubleQuantidadeProd);
				}catch (Exception i){
					this.ErroEmNovoEEditarProd(LY);
					return;
				}
			}
			
			//Tratando Tipo
			String nomeTipoProd = (String) tipoCB.getValue();
			if (nomeTipoProd == null) {
				this.ErroEmNovoEEditarProd(LY);
				return;
			}else {
				Tipo tipoEscolhido = new Tipo();
				tipoEscolhido.setNome(nomeTipoProd);
				
				List<Tipo> tipoBD = this.BOTipo.listarPorCampoEspecifico(tipoEscolhido, "nome");
				tipoEscolhido = tipoBD.get(0);
				prodEditado.setTipo(tipoEscolhido);
			}
			
			if (this.prodBO.alterar(prodEditado)) {
				this.RemoveInfo(true);
				this.GerarTela(true);
			}else {
				Double LYErro = LY;
				Double LXErro = 300.0;
				
				LYErro -= 30;
				
				Label msgError = LabelFabrica(
						"Produto já existe no armazém ou a quantidade é decimal para produto unitario",
						LXErro,
						LYErro,
						12,
						false
						);
				msgError.setTextFill(Color.RED);
				this.PaneGerente.getChildren().add(msgError);
			}
		});
		this.PaneGerente.getChildren().add(Editar);
	}
	
	public void ProdutoNovo() {
		
		List<Double> LXLY = this.BaseTelaNovoEEditarProduto("Produto Novo");
		Double LX = LXLY.get(0);
		Double LY = LXLY.get(1);
		
		LX += 100;
		
		Button Adicionar = ButtonFabrica(
				"Adicionar",
				"AdicionarProduto",
				LX,LY,
				13,
				90.0,
				"#06FF6A"
				);
		
		Adicionar.setOnAction(event->{
			Produto prod = new Produto();
			//Tratando Nome do produto
			TextField nomeTF = (TextField) this.PaneGerente.lookup("#FieldNomeProduto");
			String nomeProd = nomeTF.getText();
			if (nomeProd.isBlank()) {
				this.ErroEmNovoEEditarProd(LY);
				return;
			}
			
			prod.setNome(nomeProd);
			
			//Tratando Marca do Produto
			TextField marcaTF = (TextField) this.PaneGerente.lookup("#FieldMarcaProduto");
			String marcaProd = marcaTF.getText();
			if (marcaProd.isBlank()) {
				this.ErroEmNovoEEditarProd(LY);
				return;
			}
			
			prod.setMarca(marcaProd);
			
			//Tratando cod de barras
			TextField codTF = (TextField) this.PaneGerente.lookup("#FieldCodProduto");
			String codProd = codTF.getText();
			if (codProd.isBlank()) {
				this.ErroEmNovoEEditarProd(LY);
				return;
			}
			
			prod.setCodBarras(codProd);
			
			//Tratando Preço
			TextField precoTF = (TextField) this.PaneGerente.lookup("#FieldPrecoProduto");
			String stringPrecoProd = precoTF.getText();
			if (stringPrecoProd.isBlank()) {
				this.ErroEmNovoEEditarProd(LY);
				return;
			}else {
				stringPrecoProd = stringPrecoProd.replaceAll(",", ".");
				try {
					Double doublePrecoProd = Double.parseDouble(stringPrecoProd);
					prod.setPreco(doublePrecoProd);
				}catch (Exception e){
					this.ErroEmNovoEEditarProd(LY);
					return;
				}
			}
			
			//Tratando quantidade
			TextField quantidadeTF = (TextField) this.PaneGerente.lookup("#FieldQuantidadeProduto");
			String stringQuantidadeProd = quantidadeTF.getText();
			if (stringQuantidadeProd.isBlank()) {
				this.ErroEmNovoEEditarProd(LY);
				return;
			}else {
				try {
					Double doubleQuantidadeProd = Double.parseDouble(stringQuantidadeProd);
					prod.setQuantidade(doubleQuantidadeProd);
				}catch (Exception e){
					this.ErroEmNovoEEditarProd(LY);
					return;
				}
			}
			
			//Tratando Tipo
			ChoiceBox tipoCB = (ChoiceBox) this.PaneGerente.lookup("#ChoiceNomeTipoProduto");
			String nomeTipoProd = (String) tipoCB.getValue();
			if (nomeTipoProd == null) {
				this.ErroEmNovoEEditarProd(LY);
				return;
			}else {
				Tipo tipoEscolhido = new Tipo();
				tipoEscolhido.setNome(nomeTipoProd);
				
				List<Tipo> tipoBD = this.BOTipo.listarPorCampoEspecifico(tipoEscolhido, "nome");
				tipoEscolhido = tipoBD.get(0);
				prod.setTipo(tipoEscolhido);
			}
			
			if (this.prodBO.inserir(prod)) {
				this.RemoveInfo(true);
				this.GerarTela(true);
			}else {
				Double LYErro = LY;
				Double LXErro = 300.0;
				
				LYErro -= 30;
				
				Label msgError = LabelFabrica(
						"Produto já existe no armazém ou a quantidade é decimal para produto unitario",
						LXErro,
						LYErro,
						12,
						false
						);
				msgError.setTextFill(Color.RED);
				this.PaneGerente.getChildren().add(msgError);
			}
			
		}
		);
		this.PaneGerente.getChildren().add(Adicionar);
	}

	public void ErroEmNovoEEditarProd( Double LY) {
		LY -= 20;
		Double LX = 390.0;
		Label msgError = LabelFabrica(
				"Algum item está errado",
				LX,
				LY,
				12,
				false
				);
		msgError.setTextFill(Color.RED);
		this.PaneGerente.getChildren().add(msgError);
	}
	
	public List<Double> BaseTelaNovoEEditarProduto(String titulo) {
		this.BaseParaNovaPagina(titulo);
		List<String> itens = new ArrayList<String>();
		
		
			
		Double LX = 340.0;
		Double LY = 192.0;
		Double DistanciaLabelEField = 75.0;
		Double DistanciaLabelELabel = 35.0;
		Double LarguraField = 150.0;
	
		Label nomeTipo = LabelFabrica(
				"Tipo",
				LX, LY+5,
				12, 
				false,
				70.0
				);
		nomeTipo.setAlignment(Pos.CENTER_RIGHT);
		
		LX += DistanciaLabelEField;
		ChoiceBox CBNomeTipo = new ChoiceBox<String>();
		
		try {
			List<Tipo> TodosTipos = BOTipo.listarTodos();
			
			for(int x=0;x<TodosTipos.size();x++) itens.add(TodosTipos.get(x).getNome());
			
			CBNomeTipo = ChoiceBoxFabrica(
					"ChoiceNomeTipoProduto",
					LX,
					LY,
					LarguraField,
					itens
					);
		}catch (Exception e){
			CBNomeTipo = ChoiceBoxFabrica(
					"ChoiceNomeTipoProduto",
					LX,
					LY,
					LarguraField
					);
		}
		 
		
		Button NovoTipo = ButtonFabrica(
				"+",
				"NovoTipoEmProduto",
				LX + 150, LY,
				12,
				10.0);
		NovoTipo.setOnAction(event->{
			this.NovoTipo();
		});
		
		LX -= DistanciaLabelEField;
		LY += DistanciaLabelELabel;
		
		this.PaneGerente.getChildren().addAll(
				nomeTipo, CBNomeTipo, NovoTipo);
		
		List<String> LabelNome = new ArrayList<String>();
		LabelNome.add("Nome");
		LabelNome.add("Marca");
		LabelNome.add("Cod.");
		LabelNome.add("Preço");
		LabelNome.add("Quantidade");
		
		List<String> IdField = new ArrayList<String>();
		IdField.add("FieldNomeProduto");
		IdField.add("FieldMarcaProduto");
		IdField.add("FieldCodProduto");
		IdField.add("FieldPrecoProduto");
		IdField.add("FieldQuantidadeProduto");
		
		for (int x=0;x<IdField.size();x++) {
			Label lb = LabelFabrica(
					LabelNome.get(x),
					LX,LY+5,
					12,
					false,
					70.0
					);
			lb.setAlignment(Pos.CENTER_RIGHT);
			
			LX += DistanciaLabelEField;
			
			TextField tf = TextFieldFabrica(
					IdField.get(x),
					LarguraField,
					13.0,
					LX,LY
					);
			this.PaneGerente.getChildren().addAll(lb, tf);
			
			LX -= DistanciaLabelEField;
			LY += DistanciaLabelELabel;
		}
		
		LX += 40;
		LY += 13;
		
		Button Voltar = ButtonFabrica(
				"Voltar",
				"Voltar",
				LX,LY,
				13,
				90.0
				);
		Voltar.setOnAction(event->{
			this.RemoveInfo(false);
		});
		
		List<Double> LXLY = new ArrayList<Double>();
		LXLY.add(LX);
		LXLY.add(LY);
		
		this.PaneGerente.getChildren().add(Voltar);
		
		return LXLY;
		
	}
	
	public void Pesquisar() {
		if (this.Pesquisa.getText() == "") {
			this.RemoveInfo(true);
			this.GerarTela(true);
			return;
		}
		String NomeColuna;
		Produto prod = new Produto();
		switch(this.EscolhaPesquisa.getValue()) {
			case "Nome":
				prod.setNome(this.Pesquisa.getText());
				NomeColuna = "nome";
				break;
			case "Cod. Barras":
				prod.setCodBarras(this.Pesquisa.getText());
				NomeColuna = "cod_de_barras";
				break;
			case "Marca":
				prod.setMarca(this.Pesquisa.getText());
				NomeColuna = "marca";
				break;
			default:
				return;
		}
		this.ListaProdutos = this.prodBO.listarPorCampoEspecificoIncompleto(prod, NomeColuna);
		this.RemoveInfo(true);
		this.GerarTela(false);
	}

	public void GerenciarTipo() {

		this.BaseParaNovaPagina("Gerenciar Tipos");
		
		List<Tipo> TodosTipos = BOTipo.listarTodos();
		
		List<String> itens = new ArrayList<String>();
		
		for(int x=0;x<TodosTipos.size();x++) itens.add(TodosTipos.get(x).getNome());
			
		Double LX = 380.0;
		Double LY = 192.0;
	
		Label nomeTipo = LabelFabrica(
				"Nome",
				LX, LY,
				12, 
				false
				);
		LX += 50;
		ChoiceBox CBNome = ChoiceBoxFabrica(
				"ChoiceNomeTipo",
				LX,
				LY,
				150.0,
				itens
				);

		Double TamanhoBotao = 70.0;
		LY += 100;
		
		Button NovoTipo = ButtonFabrica(
				"Novo",
				"BotaoNovoTipo",
				LX,
				LY,
				12,
				TamanhoBotao,
				"#06FF6A"
				);
		NovoTipo.setOnAction(event -> this.NovoTipo());
		
		Double DiferencaEntreBotoes = 30.0;
		
		LY += DiferencaEntreBotoes;
		
		Button EditarTipo = ButtonFabrica(
				"Editar",
				"BotaoEditarTipo",
				LX,
				LY,
				12,
				TamanhoBotao
				);
		EditarTipo.setOnAction(event -> this.EditarTipo());
		
		LY += DiferencaEntreBotoes;
		
		Button DeletarTipo = ButtonFabrica(
				"Deletar",
				"BotaoDeletarTipo",
				LX,
				LY,
				12,
				TamanhoBotao,
				"#cc1515"
				);
		DeletarTipo.setOnAction(event -> this.DeletarTipo());
		
		LY += DiferencaEntreBotoes;
		
		Button Voltar = ButtonFabrica(
				"Voltar",
				"BotaoVoltarTipo",
				LX,
				LY,
				12,
				TamanhoBotao
				);
		Voltar.setOnAction(event -> {
			this.RemoveInfo(false);
		});
		
		this.PaneGerente.getChildren().addAll(
				nomeTipo, 
				CBNome, 
				NovoTipo,
				EditarTipo,
				DeletarTipo,
				Voltar
				);
	}
	
	private void NovoTipo() {
		this.BaseTelaNovoEEditarTipo("Novo Tipo");
		
		Double TamanhoButton = 70.0;
		Double LX = 450.0;
		Double LY = 300.0;
		
		Button adicionar = ButtonFabrica(
				"Adicionar",
				"AdicionarTipo",
				LX,
				LY,
				12,
				TamanhoButton,
				"#06FF6A"
				);
		adicionar.setOnAction(event->{
			Tipo tipo = new Tipo();
			
			TextField EscolhaNome = (TextField) this.PaneGerente.lookup("#EscolhaNomeTipo");
			ChoiceBox CBForma = (ChoiceBox) this.PaneGerente.lookup("#ChoiceForma");
			
			String CampoNome = EscolhaNome.getText();
			String FormaCompra = (String) CBForma.getValue();
			
			if (CampoNome == "" || FormaCompra == null) {
				Label msgErro = LabelFabrica(
						"Algum item errado",
						430.0,
						250.0,
						12,
						false
						);
				msgErro.setTextFill(Color.RED);
				this.PaneGerente.getChildren().add(msgErro);
			}else {
				tipo.setNome(CampoNome);
				switch (FormaCompra) {
					case "Quilo": 
						tipo.setFormaDeVenda("q");
						break;
					case "Unidade":
						tipo.setFormaDeVenda("u");
						break;
					default:
						throw new IllegalArgumentException("Unexpected value: " + CampoNome);
				}
				
				if (this.BOTipo.inserir(tipo)) {
					this.RemoveInfo(false);
				}else {
					Label msgErro = LabelFabrica(
							"Item já existe no armazém ou a quantidade é decimal para produto unitario",
							430.0,
							280.0,
							12,
							false
							);
					msgErro.setTextFill(Color.RED);
					this.PaneGerente.getChildren().add(msgErro);
				}
			}
		});
		
		this.PaneGerente.getChildren().add(adicionar);
	}
	
	private void EditarTipo() {
		ChoiceBox CB = (ChoiceBox) this.PaneGerente.lookup("#ChoiceNomeTipo");
		String nomeTipo = (String) CB.getValue();
		
		if (nomeTipo == null) {
			Label msgErro = LabelFabrica(
					"Escolha um Tipo",
					430.0,
					250.0,
					12,
					false
					);
			msgErro.setTextFill(Color.RED);
			this.PaneGerente.getChildren().add(msgErro);
		}else {
			
			this.BaseTelaNovoEEditarTipo("Editar Tipo");
			
			Tipo tipoOriginal = new Tipo();
			tipoOriginal.setNome(nomeTipo);
			
			List<Tipo> listTipoOriginal= BOTipo.listarPorCampoEspecifico(tipoOriginal, "nome");
			Tipo tipoRecebido = listTipoOriginal.get(0);
			tipoOriginal.setNome(tipoRecebido.getNome());
			tipoOriginal.setId(tipoRecebido.getId());
			tipoOriginal.setFormaDeVenda(tipoRecebido.getFormaDeVenda());
			
			TextField EscolhaNome = (TextField) this.PaneGerente.lookup("#EscolhaNomeTipo");
			EscolhaNome.setText(nomeTipo);
			
			String item;
			
			switch (tipoOriginal.getFormaDeVenda()) {
				case "q": 
					item = "Quilo";
					break;
				case "u":
					item = "Unidade";
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + tipoOriginal.getFormaDeVenda());
		}
			
			ChoiceBox CBForma = (ChoiceBox) this.PaneGerente.lookup("#ChoiceForma");
			
			CBForma.setValue(item);
			
			Double TamanhoButton = 70.0;
			Double LX = 450.0;
			Double LY = 300.0;
			
			Button editar = ButtonFabrica(
					"Editar",
					"EditarTipo",
					LX,
					LY,
					12,
					TamanhoButton,
					"#06FF6A"
					);
			editar.setOnAction(event->{

				Tipo tipo = new Tipo();
				
				String CampoNome = EscolhaNome.getText();
				String FormaCompra = (String) CBForma.getValue();
				
				if (CampoNome == "" || FormaCompra == null) {
					Label msgErro = LabelFabrica(
							"Algum item errado",
							430.0,
							250.0,
							12,
							false
							);
					msgErro.setTextFill(Color.RED);
					this.PaneGerente.getChildren().add(msgErro);
				}else {
					tipo.setNome(CampoNome);
					
					switch (FormaCompra) {
						case "Quilo": 
							tipo.setFormaDeVenda("q");
							break;
						case "Unidade":
							tipo.setFormaDeVenda("u");
							break;
						default:
							throw new IllegalArgumentException("Unexpected value: " + CampoNome);
					}
					tipo.setId(tipoOriginal.getId());
					
					if (BOTipo.alterar(tipo)) {
						this.RemoveInfo(true);
						this.GerarTela(true);
					}else {
						Label msgErro = LabelFabrica(
								"Erro",
								430.0,
								280.0,
								12,
								false
								);
						msgErro.setTextFill(Color.RED);
						this.PaneGerente.getChildren().add(msgErro);
					}
				}
			});
		
			this.PaneGerente.getChildren().add(editar);		
	}
		
	}
	
	private void BaseTelaNovoEEditarTipo(String titulo) {
			this.BaseParaNovaPagina(titulo);
			
			Double LX = 400.0;
			Double LY = 192.0;
			
			Double DistanciaLabelField = 50.0;
			Double DistanciaEntreOBJ = 30.0;
			
			Label nome = LabelFabrica(
					"Nome",
					LX, LY,
					12, 
					false
					);
			
			LX += DistanciaLabelField;
			
			TextField EscolhaNome = TextFieldFabrica(
					"EscolhaNomeTipo",
					150.0,
					15.0,
					LX, LY
					);
			
			LX -= DistanciaLabelField;
			LY += DistanciaEntreOBJ;
			
			Label forma = LabelFabrica(
					"Forma",
					LX, LY,
					12, 
					false
					);
			
			LX += DistanciaLabelField;
			
			List<String> itens = new ArrayList<String>();
			itens.add("Quilo");
			itens.add("Unidade");
			
			ChoiceBox CBForma = ChoiceBoxFabrica(
					"ChoiceForma",
					LX,
					LY,
					150.0,
					itens
					);
			
			LY += 80;
			
			Double TamanhoButton = 70.0;
			
			LY += DistanciaEntreOBJ;
			
			Button voltar = ButtonFabrica(
					"Voltar",
					"VoltarTipo",
					LX,
					LY,
					12,
					TamanhoButton
					);
			voltar.setOnAction(event ->{
				this.RemoveInfo(true);
				this.GerarTela(false);
			});
				
			this.PaneGerente.getChildren().addAll(
					nome, EscolhaNome,
					forma, CBForma,
					voltar
					);
			
	}
	
	private void DeletarTipo() {
		ChoiceBox<String> CB = (ChoiceBox) this.PaneGerente.lookup("#ChoiceNomeTipo");
		String nomeTipo = (String) CB.getValue();
		
		if (nomeTipo == null) {
			Label msgErro = LabelFabrica(
					"Escolha um Tipo",
					430.0,
					250.0,
					12,
					false
					);
			msgErro.setTextFill(Color.RED);
			this.PaneGerente.getChildren().add(msgErro);
		}else {
			this.BaseParaNovaPagina("Voce quer mesmo deletar " + nomeTipo + "?");
			
			Double LX = 380.0;
			Double LY = 200.0;
			Double TamanhoButton = 80.0;
			
			Button voltar = ButtonFabrica(
					"Voltar",
					"VoltarTipo",
					LX,
					LY,
					12,
					TamanhoButton
					);
			voltar.setOnAction(event ->{
				this.RemoveInfo(false);
			});
			
			LX += 90;		
			Button deletar = ButtonFabrica(
					"Deletar",
					"DeletarTipo",
					LX,
					LY,
					12,
					TamanhoButton,
					"#cc1515"
					);
			deletar.setOnAction(event->{
				Tipo tipo = new Tipo();
				tipo.setNome(nomeTipo);
				if (this.BOTipo.deletar(tipo)) {
					this.RemoveInfo(true);
					this.GerarTela(true);
				}else {
					Label msgErro = LabelFabrica(
							"Esse tipo está associado com algum Produto",
							360.0,
							180.0,
							12,
							false
							);
					msgErro.setTextFill(Color.RED);
					this.PaneGerente.getChildren().add(msgErro);
				}
			});
			
			this.PaneGerente.getChildren().addAll(deletar, voltar);
			
		}
	}
	
  	private void BaseParaNovaPagina(String titulo) {
		ImageView IV = ImageFabrica(
						659.0,
						321.0,
						150.0,
						150.0,
						"view/ve/RectanglePrincipal.png"
						);
				
		Label t = LabelFabrica(
					titulo, 
					150.0, 
					155.0, 
					18, 
					true,
					650.0
					);
		
		
		this.PaneGerente.getChildren().addAll(IV, t);
	}
}
