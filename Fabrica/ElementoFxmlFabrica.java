package Fabrica;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class ElementoFxmlFabrica implements InterFabrica{

	@Override
	public Label LabelFabrica(String Nome, Double LayX, Double LayY, int TamanhoFont, boolean centralizar) {
		Label lb = new Label(Nome);
		lb.setLayoutX(LayX);
		lb.setLayoutY(LayY);
		lb.setFont(new Font("Arial", TamanhoFont));
		if(centralizar) lb.setAlignment(Pos.CENTER);
		return lb;
	}
	
	@Override
	public Label LabelFabrica(String Nome, Double LayX, Double LayY, int TamanhoFont, boolean centralizar,
			Double Largura, Double LarguraMaxima) {
		Label lb = this.LabelFabrica(Nome, LayX, LayY, TamanhoFont, centralizar);
		lb.setMaxWidth(LarguraMaxima);
		lb.setPrefWidth(Largura);
		return lb;
	}

	@Override
	public Button ButtonFabrica(String Nome, String Id, Double LayX, Double LayY, int TamanhoFont,
			Double LarguraMaxima) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Button ButtonFabrica(String Nome, String Id, Double LayX, Double LayY, int TamanhoFont, Double LarguraMaxima,
			String CorHexadecimal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextField TextFieldFabrica(String id, Double Largura, Double Altura, Double LayX, Double LayY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageView ImageFabrica(Double Largura, Double Altura, Double LayX, Double LayY, String CaminhoImg) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
