package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
		Double LayY = 182.0;
		
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
		Double LarguraLabel = 60.0;
		boolean Centralizar = true;
		
		Double LarguraButton = 37.0;
		
		for (int i =start ; i < end; i++) {
		
			prod = this.ListaProdutos.get(i);
			
			LayX = 80.0;
			
			Label nome = LabelFabrica(
					prod.getNome(), 
					LayX, 
					LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel
					);
			
			LayX += 80;
			
			Label cod = LabelFabrica(
					prod.getCodBarras(), 
					LayX, LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel
					);
			
			LayX += 80;
			
			Label marca = LabelFabrica(
					prod.getMarca(), 
					LayX, 
					LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel
					);
			
			LayX += 80;
			
			Label quant = LabelFabrica(
					String.valueOf(prod.getQuantidade()), 
					LayX, 
					LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel);
			
			LayX += 80;
			
			Label tipo = LabelFabrica(
					prod.getTipo().getNome(), 
					LayX, 
					LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel
					);
			
			LayX += 80;
			
			Label preco = LabelFabrica(
					String.valueOf(prod.getPreco()), 
					LayX, 
					LayY, 
					TamanhoFont, 
					Centralizar, 
					LarguraLabel
					);
			
			LayX += 70;
			
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
			
			LayX += 38;
			
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
		Double LX = 102.0;
		Double LY = 392.0;
		
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
		//implementar
		Button b = (Button) e.getSource();
		Produto prod = new Produto();
		
		prod.setCodBarras(b.getId());
		
	}
	
	public void EditarProduto(ActionEvent e) {
		//implementar
		Button b = (Button) e.getSource();
		Produto prod = new Produto();
		
		prod.setCodBarras(b.getId());
	}
	
	public void RemersaNova() {
		this.BaseParaNovaPagina("NovaRemessa");;
		
		List<String> idTextF = new ArrayList<String>();
		idTextF.add("cod");
		idTextF.add("quant");
		
		List<String> textLabel = new ArrayList<String>();
		textLabel.add("Cod de barras");
		textLabel.add("quantidade");
		
		Double LY = 194.0;
		for (int n = 0; n<2;n++) {
			Label l = LabelFabrica(
					textLabel.get(n), 
					230.0, 
					LY,
					12, 
					false, 
					90.0
					);
			
			TextField tf = TextFieldFabrica(
					idTextF.get(n),
					150.0,
					17.0,
					312.0,
					LY
					);
			
			this.PaneGerente.getChildren().addAll(l, tf);
			LY += 34;
		}
		Button bV = ButtonFabrica(
				"Voltar",
				"Voltar",
				273.0,
				291.0,
				12,
				50.0
				);
		bV.setOnAction(event -> {
			this.RemoveInfo(false);
		});
		
		Button bMudar = ButtonFabrica(
				"Atualizar",
				"Atualizar",
				327.0,
				291.0,
				12,
				50.0
				);
		bMudar.setOnAction(event -> {
			TextField tFCode = (TextField) this.PaneGerente.lookup("#cod"); 
			Produto prod = new Produto();
			prod.setCodBarras(tFCode.getText());
			
			
			ProdutoBO bo = new ProdutoBO();
			if (bo.ExisteNoBD(prod)) {
				List<Produto> lProd = bo.listarPorCampoEspecifico(prod, "cod_de_barras");
				
				prod = lProd.get(0);
				TextField tFQuanti = (TextField) this.PaneGerente.lookup("#quant");
				Integer quantidade = Integer.parseInt(tFQuanti.getText());
				prod.setQuantidade(prod.getQuantidade()+quantidade);
				
				bo.alterar(prod);
				this.RemoveInfo(true);
				this.GerarTela(true);
			}else {
				Label msgErro = LabelFabrica(
						"O cod de barras não existe no armazém",
						250.0,
						260.0,
						12,
						false,
						300.0);
				msgErro.setTextFill(Color.RED);
				this.PaneGerente.getChildren().add(msgErro);
			}
		});
		this.PaneGerente.getChildren().addAll( bV, bMudar);
	}

	public void ProdutoNovo() {
		//vai add produto novo no BD
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
		TipoBO BOTipo = new TipoBO();
		
		this.BaseParaNovaPagina("Gerenciar Tipos");
		
		List<Tipo> TodosTipos = BOTipo.listarTodos();
		
		List<String> itens = new ArrayList<String>();
		
		for(int x=0;x<TodosTipos.size();x++) itens.add(TodosTipos.get(x).getNome());
			
		Double LX = 270.0;
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
		NovoTipo.setOnAction(event -> this.TipoNovo());
		
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
	
	private void TipoNovo() {
		this.BaseParaNovaPagina("Criar Novo Tipo");
		
		Double LX = 270.0;
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
				"ChoiceNome",
				LX,
				LY,
				150.0,
				itens
				);
		
		LY += 80;
		
		Double TamanhoButton = 70.0;
		
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
			TipoBO BOTipo = new TipoBO();
			
			Tipo tipo = new Tipo();
			
			String CampoNome = EscolhaNome.getText();
			String FormaCompra = (String) CBForma.getValue();
			
			if (CampoNome == "" || FormaCompra == null) {
				Label msgErro = LabelFabrica(
						"Algum item errado",
						300.0,
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
				
				if (BOTipo.inserir(tipo)) {
					this.RemoveInfo(false);
				}else {
					Label msgErro = LabelFabrica(
							"Item já existe no armazém",
							300.0,
							280.0,
							12,
							false
							);
					msgErro.setTextFill(Color.RED);
					this.PaneGerente.getChildren().add(msgErro);
				}
			}
		});
		
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
				adicionar, voltar
				);
	}
	
	private void EditarTipo() {
		ChoiceBox<String> CB = (ChoiceBox) this.PaneGerente.lookup("#ChoiceNomeTipo");
		String nomeTipo = (String) CB.getValue();
		
		TipoBO BOTipo = new TipoBO();
		
		if (nomeTipo == null) {
			Label msgErro = LabelFabrica(
					"Escolha um Tipo",
					300.0,
					250.0,
					12,
					false
					);
			msgErro.setTextFill(Color.RED);
			this.PaneGerente.getChildren().add(msgErro);
		}else {
			
			Tipo tipoOriginal = new Tipo();
			tipoOriginal.setNome(nomeTipo);
			
			List<Tipo> listTipoOriginal= BOTipo.listarPorCampoEspecifico(tipoOriginal, "nome");
			Tipo tipoRecebido = listTipoOriginal.get(0);
			tipoOriginal.setNome(tipoRecebido.getNome());
			tipoOriginal.setId(tipoRecebido.getId());
			tipoOriginal.setFormaDeVenda(tipoRecebido.getFormaDeVenda());
			
			this.BaseParaNovaPagina("Editar Tipo");
			
			Double LX = 270.0;
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
			EscolhaNome.setText(nomeTipo);
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
					"ChoiceNome",
					LX,
					LY,
					150.0,
					itens
					);
			
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
			
			CBForma.setValue(item);
			
			LY += 80;
			
			Double TamanhoButton = 70.0;
			
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
							300.0,
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
					}else {
						Label msgErro = LabelFabrica(
								"Erro",
								300.0,
								280.0,
								12,
								false
								);
						msgErro.setTextFill(Color.RED);
						this.PaneGerente.getChildren().add(msgErro);
					}
				}
			});
			
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
					editar, voltar
					);
	}
		
	}
	
	private void DeletarTipo() {
		ChoiceBox<String> CB = (ChoiceBox) this.PaneGerente.lookup("#ChoiceNomeTipo");
		String nomeTipo = (String) CB.getValue();
		
		TipoBO BOTipo = new TipoBO();
		
		if (nomeTipo == null) {
			Label msgErro = LabelFabrica(
					"Escolha um Tipo",
					300.0,
					250.0,
					12,
					false
					);
			msgErro.setTextFill(Color.RED);
			this.PaneGerente.getChildren().add(msgErro);
		}else {
			this.BaseParaNovaPagina("Voce quer mesmo deletar " + nomeTipo + "?");
			
			Double LX = 300.0;
			Double LY = 192.0;
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
				this.RemoveInfo(true);
				this.GerarTela(false);
			});
			
					
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
				
			});
			
			
		}
	}
	
  	private void BaseParaNovaPagina(String titulo) {
		ImageView IV = ImageFabrica(
						556.0,
						283.0,
						77.0,
						133.0,
						"view/ve/RectangleSecundario.png"
						);
				
		Label t = LabelFabrica(
					titulo, 
					298.0, 
					147.0, 
					18, 
					false
					);
		
		this.PaneGerente.getChildren().addAll(IV, t);
	}
}
