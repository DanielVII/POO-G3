package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Telas extends Application {
	private static Stage primaryStage;
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void setPrimaryStage(Stage primaryStage) {
		Telas.primaryStage = primaryStage;
		
	
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		setPrimaryStage(primaryStage);
		primaryStage.setTitle("mercado");
		primaryStage.show();
		primaryStage.centerOnScreen();
		telaLogin();
	}

	private static void prepararCena(String caminho) throws Exception{
		Parent root = FXMLLoader.load(Telas.class.getResource(caminho));
		Scene cena = new Scene(root);
		primaryStage.setScene(cena);
	}
	
	public static void telaLogin() throws Exception{
		prepararCena("ve/TelaLogin.fxml");
	}
	
	public static void telaGerente() throws Exception{
		prepararCena("ve/TelaGerente.fxml");
	}
	
	public static void telaFuncionario() throws Exception{
		prepararCena("ve/TelaFuncionario.fxml");
	}
	public static void main(String[] args) {
		launch();
	}
	
}
