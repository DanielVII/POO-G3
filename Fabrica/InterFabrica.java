package Fabrica;

import javafx.scene.control.Button;
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
			Double Largura,
			Double LarguraMaxima);
	
	public Button ButtonFabrica(
			String Nome, 
			String Id,
			Double LayX,
			Double LayY,
			int TamanhoFont,
			Double LarguraMaxima);
	
	public Button ButtonFabrica(
			String Nome, 
			String Id,
			Double LayX,
			Double LayY,
			int TamanhoFont,
			Double LarguraMaxima,
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
	
}
