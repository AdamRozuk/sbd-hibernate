package sbd.adam.demo;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sbd.adam.entity.*;
public class Main extends Application{

		Label label;
		Button coachesButton, leaguesButton, playersButton;
		VBox layout;
		static Scene scene;
		static Scene scene2;
		static Stage mainStage;
		static Session coachSession;
		static SessionFactory coachFactory;
		static Session leagueSession;
		static SessionFactory leagueFactory;
		static Session playerSession;
		static SessionFactory playerFactory;
		
		
	public static void main(String[] args) {
		connect();
		launch(args);
	}
	
	@Override
	public void start (Stage primaryStage) throws Exception {
		primaryStage.setTitle("LeagueCompetitions");
		initializieComponents();
		mainStage = primaryStage;
		layout.getChildren().addAll(label,coachesButton,leaguesButton,playersButton);
		
		setListeners();
		
		primaryStage.setScene(scene); 
		primaryStage.show();
		
	}
	private void setListeners() {
		coachesButton.setOnAction(e -> goToCoaches());
		leaguesButton.setOnAction(e -> goToLeagues());
		playersButton.setOnAction(e -> goToPlayers());
	}
	
	private void initializieComponents() {
		layout = new VBox(20);
		
		coachesButton = new Button("Coaches");
		leaguesButton = new Button("Leagues");
		playersButton = new Button("Players");
		
		label = new Label("scena pierwsza");
		scene = new Scene(layout,800,600);
	}
		
	public void goToCoaches() {
		//VBox vBox = new VBox();
		scene2 = new CoachScene(new VBox(),800,600);
		mainStage.setScene(scene2);
	}
	public void goToLeagues() {
		//VBox vBox = new VBox();
		scene2 = new LeagueScene(new VBox(),800,600);
		mainStage.setScene(scene2);
	}
	public void goToPlayers() {
		//VBox vBox = new VBox();
		scene2 = new PlayersScene(new VBox(),800,600);
		mainStage.setScene(scene2);
	}
	public static void backToMain() {
		mainStage.setScene(scene);
	}
	private static void connect() {
		coachFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Coach.class)
				.buildSessionFactory();
		
		leagueFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(League.class)
				.buildSessionFactory();
		
		playerFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Player.class)
				.buildSessionFactory();
		
	}
}
