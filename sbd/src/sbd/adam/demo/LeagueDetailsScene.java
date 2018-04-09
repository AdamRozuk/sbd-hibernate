package sbd.adam.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sbd.adam.entity.Team;
import sbd.adam.entity.League;
import sbd.adam.entity.LeagueMatch;
import sbd.adam.entity.Coach;

public class LeagueDetailsScene extends Scene{

	Label label;
	Button backButton;
	Button playMatchButton;
	Button showScore;
	VBox vBox;
	League league;
	ListView<Team> listView;
	ListView<LeagueMatch> listViewMatch;
	List<Team> teams;
	List<LeagueMatch> matches;

	
	public LeagueDetailsScene(VBox vBox,int x,int y,League league) {
		super(vBox,x,y);
		this.vBox = vBox;
		this.league=league;
		initializeComponents();
		addComponents();
	}
	
	private void addComponents() {
		vBox.getChildren().addAll(label,backButton,listView,playMatchButton,listViewMatch,showScore);
	}
	
	private void initializeComponents() {
		
		label = new Label("Teams");
		backButton = new Button("Back to leagues");
		playMatchButton = new Button("Play Match!");
		showScore = new Button("Show score");
		
		backButton.setOnAction(e -> backToLeagues());
		playMatchButton.setOnAction(e -> playMatch());
		showScore.setOnAction(e -> showScore());
		
		refreshListView();
	}
	
	private void refreshListView() {
		Main.teamSession = Main.teamFactory.getCurrentSession();
		try {
			Main.teamSession.beginTransaction();
			teams = Main.teamSession.createQuery("from Team t where t.idLeague = :param").setParameter("param", league.getId()).list();
			Main.teamSession.getTransaction().rollback();
			listView = new ListView<Team>();
			for(Team team : teams) {
				listView.getItems().add(team);
			}
		} 
		finally {
			Main.teamSession.close();
		}
		
		Main.leagueMatchSession = Main.leagueMatchFactory.getCurrentSession();
		try {
			Main.leagueMatchSession.beginTransaction();
			matches = Main.leagueMatchSession.createQuery("from LeagueMatch lM where lM.idLeague = :param").setParameter("param", league.getId()).list();
			Main.leagueSession.getTransaction().rollback();
			listViewMatch = new ListView<LeagueMatch>();
			for(LeagueMatch leagueMatch : matches) {
				listViewMatch.getItems().add(leagueMatch);
			}
		} 
		finally {
			Main.leagueMatchSession.close();
		}
		
	}
	private void showScore() {
		Team team = listView.getSelectionModel().getSelectedItem();
		int score=0;
		for(LeagueMatch leagueMatch : matches) {
			if(leagueMatch.getIdTeam1()==team.getId()&&leagueMatch.getResult()==0) {
				score ++;
			}
			if(leagueMatch.getIdTeam2()==team.getId()&&leagueMatch.getResult()==1) {
				score ++;
			}	
		}
		AlertBox.display("Show score", team.getName()+  " has scored " +score+ " points.");
		
	}
	private void playMatch() {
		Main.scene2 = new PlayLeagueMatchScene(new VBox(),800,600,league);
		Main.mainStage.setScene(Main.scene2);
	}
	private void backToLeagues() {
		Main.scene2 = new LeagueScene(new VBox(),800,600);
		Main.mainStage.setScene(Main.scene2);
	}
}
