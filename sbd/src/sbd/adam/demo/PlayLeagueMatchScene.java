package sbd.adam.demo;

import java.util.List;


import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sbd.adam.entity.*;

public class PlayLeagueMatchScene extends Scene{

	Button backButton;
	Button submitButton;
	VBox vBox;
	List<Team> teams;
	TextField placeField;
	League league;
	ChoiceBox<Team> team1Box;
	ChoiceBox<Team> team2Box;
	ChoiceBox<String> resultField;
	
	public PlayLeagueMatchScene(VBox vBox,int x,int y, League league) {
		super(vBox,x,y);
		this.vBox = vBox;
		this.league = league;
		initializeComponents();
		addComponents();
	}
	
	private void addComponents() {
		vBox.getChildren().addAll(backButton,submitButton,team1Box,team2Box,resultField,placeField);
	}
	
	private void initializeComponents() {
		
		backButton = new Button("Back to menu"); 
		resultField = new ChoiceBox<String>();
		resultField.getItems().addAll("First team won", "Second team won");
		resultField.setValue(resultField.getItems().get(0));
		placeField = new TextField("place");
		submitButton = new Button("Play match");
		team1Box = new ChoiceBox<Team>();
		team2Box = new ChoiceBox<Team>();
		
		backButton.setOnAction(e -> Main.backToMain());
		submitButton.setOnAction(e -> playMatch());
		
		refreshTeamBoxes();
	}
	

	private void refreshTeamBoxes() {
		Main.teamSession = Main.teamFactory.getCurrentSession();
		try {
			Main.teamSession.beginTransaction();
			teams = Main.teamSession.createQuery("from Team t where t.idLeague = :param").setParameter("param", league.getId()).list();
			Main.teamSession.getTransaction().rollback();
			team1Box = new ChoiceBox<Team>();
			team2Box = new ChoiceBox<Team>();
			for(Team team : teams) {
				team1Box.getItems().add(team);
				team2Box.getItems().add(team);
			}
			try {
				team1Box.setValue(team1Box.getItems().get(0));
				team2Box.setValue(team2Box.getItems().get(0));
				}
				catch(Exception e) {
					
				}
		} 
		finally {
			Main.teamSession.close();
		}
	
	}
	
	private void playMatch() {
		if(team1Box.getSelectionModel().getSelectedIndex()!=team2Box.getSelectionModel().getSelectedIndex()) {
			LeagueMatch leagueMatch;
			if(resultField.getSelectionModel().getSelectedIndex()==0) {
				leagueMatch = new LeagueMatch(team1Box.getSelectionModel().getSelectedItem().getId(),team2Box.getSelectionModel().getSelectedItem().getId(),0,placeField.getText(),league.getId());
							
			}
			else {
				leagueMatch = new LeagueMatch(team1Box.getSelectionModel().getSelectedItem().getId(),team2Box.getSelectionModel().getSelectedItem().getId(),1,placeField.getText(),league.getId());
		
			}
			try {
				Main.leagueMatchSession = Main.leagueMatchFactory.getCurrentSession();
				Main.leagueMatchSession.beginTransaction();
				Main.leagueMatchSession.save(leagueMatch);
				Main.leagueMatchSession.getTransaction().commit();
				placeField.clear();
			} 
			finally {
				Main.leagueMatchSession.close();
			}
			
			Main.scene2 = new LeagueDetailsScene(new VBox(),800,600,league);
			Main.mainStage.setScene(Main.scene2);
			
		
		}
	}
}