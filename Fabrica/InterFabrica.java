package Fabrica;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public interface InterFabrica {
	
	public Label LabelFabrica(
			String Nome, 
			Double LayX,
			Double LayY,
			int TamanhoFont,
			boolean centralizar);
	
	public Label LabelFabrica(
			String Nome, 
			Double LayX,
			Double LayY,
			int TamanhoFont,
			boolean centralizar,
			Double Largura);
	
	public Button ButtonFabrica(
			String Nome, 
			String Id,
			Double LayX,
			Double LayY,
			int TamanhoFont,
			Double Largura);
	
	public Button ButtonFabrica(
			String Nome, 
			String Id,
			Double LayX,
			Double LayY,
			int TamanhoFont,
			Double Largura,
			String CorHexadecimal);
	
	public TextField TextFieldFabrica( 
			String id,
			Double Largura,
			Double Altura,
			Double LayX,
			Double LayY
			);
	
	public ImageView ImageFabrica( 
			Double Largura,
			Double Altura,
			Double LayX,
			Double LayY,
			String CaminhoImg);
	
	public ChoiceBox ChoiceBoxFabrica(
			String id,
			Double LayX,
			Double LayY,
			Double Largura,
			List<String> ListaValores
			);
	
	public ChoiceBox ChoiceBoxFabrica(
			String id,
			Double LayX,
			Double LayY,
			Double Largura
			);
	
}
