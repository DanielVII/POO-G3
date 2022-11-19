package Fabrica;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
		Button bt = new Button(Nome);
		bt.setId(Id);
		bt.setLayoutX(LayX);
		bt.setLayoutY(LayY);
		bt.setMaxWidth(LarguraMaxima);
		bt.setFont(new Font("Arial", TamanhoFont));
		// TODO Auto-generated method stub
		return bt;
	}
	
	@Override
	public Button ButtonFabrica(String Nome, String Id, Double LayX, Double LayY, int TamanhoFont, Double LarguraMaxima,
			String CorHexadecimal) {
		Button bt = this.ButtonFabrica(Nome, Id, LayX, LayY, TamanhoFont, LarguraMaxima, CorHexadecimal);
		bt.setStyle("-fx-background-color:" + CorHexadecimal + " ;");
		// TODO Auto-generated method stub
		return bt;
	}

	@Override
	public TextField TextFieldFabrica(String id, Double Largura, Double Altura, Double LayX, Double LayY) {
		TextField tf = new TextField();
		tf.setLayoutX(LayX);
		tf.setLayoutY(LayY);
		tf.setId(id);
		tf.setPrefHeight(Altura);
		tf.setPrefWidth(Largura);
		// TODO Auto-generated method stub
		return tf;
	}

	@Override
	public ImageView ImageFabrica(Double Largura, Double Altura, Double LayX, Double LayY, String CaminhoImg) {
		ImageView iv = new ImageView();
		iv.setLayoutX(LayX);
		iv.setLayoutY(LayY);
		iv.setImage(new Image(CaminhoImg));
		iv.prefHeight(Altura);
		iv.prefWidth(Largura);
		// TODO Auto-generated method stub
		return iv;
	}

	

}
