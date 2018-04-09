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
		Button coachesButton, leaguesButton, playersButton, teamsButton, prizesButton, compositionsButton,tournamentsButton;
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
		static Session teamSession;
		static SessionFactory teamFactory;
		static Session prizesSession;
		static SessionFactory prizesFactory;
		static Session compositionsSession;
		static SessionFactory compositionsFactory;
		static Session tournamentsSession;
		static SessionFactory tournamentsFactory;
		static Session teamTournamentSession;
		static SessionFactory teamTournamentFactory;
		static Session leagueMatchSession;
		static SessionFactory leagueMatchFactory;
		static Session tournamentMatchSession;
		static SessionFactory tournamentMatchFactory;
		
		
	public static void main(String[] args) {
		connect();
		launch(args);
	}
	
	@Override
	public void start (Stage primaryStage) throws Exception {
		primaryStage.setTitle("LeagueCompetitions");
		initializeComponents();
		mainStage = primaryStage;
		layout.getChildren().addAll(label,coachesButton,leaguesButton,playersButton,teamsButton,prizesButton,compositionsButton,tournamentsButton);
		
		setListeners();
		
		primaryStage.setScene(scene); 
		primaryStage.show();
		
	}
	private void setListeners() {
		coachesButton.setOnAction(e -> goToCoaches());
		leaguesButton.setOnAction(e -> goToLeagues());
		playersButton.setOnAction(e -> goToPlayers());
		teamsButton.setOnAction(e -> goToTeams());
		prizesButton.setOnAction(e -> goToPrizes());
		compositionsButton.setOnAction(e -> goToCompositions());
		tournamentsButton.setOnAction(e -> goToTournaments());
	}
	
	private void initializeComponents() {
		layout = new VBox(20);
		
		coachesButton = new Button("Coaches");
		leaguesButton = new Button("Leagues");
		playersButton = new Button("Players");
		teamsButton = new Button("Teams");
		prizesButton = new Button("Prizes");
		compositionsButton = new Button("Compositions");
		tournamentsButton = new Button("Tournaments");
		
		
		label = new Label("scena pierwsza");
		scene = new Scene(layout,800,600);
	}
	
		
	public void goToCoaches() {
		scene2 = new CoachScene(new VBox(),800,600);
		mainStage.setScene(scene2);
	}
	public void goToLeagues() {
		scene2 = new LeagueScene(new VBox(),800,600);
		mainStage.setScene(scene2);
	}
	public void goToPlayers() {
		scene2 = new PlayersScene(new VBox(),800,600);
		mainStage.setScene(scene2);
	}
	public void goToTeams() {
		scene2 = new TeamScene(new VBox(),800,600);
		mainStage.setScene(scene2);
	}
	public void goToPrizes() {
		scene2 = new PrizesScene(new VBox(),800,600);
		mainStage.setScene(scene2);
	}
	public void goToCompositions() {
		scene2 = new CompositionsScene(new VBox(),800,600);
		mainStage.setScene(scene2);
	}
	public void goToTournaments() {
		scene2 = new TournamentScene(new VBox(),800,600);
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
		
		teamFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Team.class)
				.buildSessionFactory();
		
		prizesFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Prizes.class)
				.buildSessionFactory();
		
		compositionsFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Composition.class)
				.buildSessionFactory();
		
		tournamentsFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Tournament.class)
				.buildSessionFactory();
		
		teamTournamentFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(TeamTournament.class)
				.buildSessionFactory();
		leagueMatchFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(LeagueMatch.class)
				.buildSessionFactory();
		tournamentMatchFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(TournamentMatch.class)
				.buildSessionFactory();
		
		
		
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}
