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
import sbd.adam.entity.Coach;

public class TeamScene extends Scene{

	Label label;
	Button backButton;
	Button deleteButton;
	Button submitTeamButton;
	VBox vBox;
	ListView<Team> listView;
	List<Team> teams;
	List<League> leagues;
	List<Coach> coaches;
	TextField nameField;
	TextField tagField;
	TextField ownerField;
	ChoiceBox<League> leagueBox;
	ChoiceBox<Coach> coachBox;
	
	public TeamScene(VBox vBox,int x,int y) {
		super(vBox,x,y);
		this.vBox = vBox;
		initializeComponents();
		addComponents();
	}
	
	private void addComponents() {
		vBox.getChildren().addAll(label,backButton,listView,nameField,tagField,ownerField,submitTeamButton,deleteButton,leagueBox,coachBox);
	}
	
	private void initializeComponents() {
		
		label = new Label("Teams");
		backButton = new Button("Back to menu");
		deleteButton = new Button("Delete Team"); 
		nameField = new TextField("name");
		tagField = new TextField("tag");
		ownerField = new TextField("owner");
		submitTeamButton = new Button("Add team");
		leagueBox = new ChoiceBox<League>();
		coachBox = new ChoiceBox<Coach>();
		
		backButton.setOnAction(e -> Main.backToMain());
		submitTeamButton.setOnAction(e -> addTeam());
		deleteButton.setOnAction(e -> deleteTeam());
		
		refreshListView();
		refreshLeagueBox();
		refreshCoachBox();
	}
	
	private void refreshListView() {
		Main.teamSession = Main.teamFactory.getCurrentSession();
		try {
			Main.teamSession.beginTransaction();
			teams = Main.teamSession.createQuery("from Team").list();
			Main.teamSession.getTransaction().rollback();
			listView = new ListView<Team>();
			for(Team team : teams) {
				listView.getItems().add(team);
			}
		} 
		finally {
			Main.teamSession.close();
		}
	}
	private void refreshLeagueBox() {
		Main.leagueSession = Main.leagueFactory.getCurrentSession();
		try {
			Main.leagueSession.beginTransaction();
			leagues = Main.leagueSession.createQuery("from League").list();
			Main.leagueSession.getTransaction().rollback();
			leagueBox = new ChoiceBox<League>();
			for(League league : leagues) {
				leagueBox.getItems().add(league);
				
			}
			try {
			leagueBox.setValue(leagueBox.getItems().get(0));
			}
			catch(Exception e) {
				
			}
		} 
		finally {
			Main.leagueSession.close();
		}
	}
	private void refreshCoachBox() {
		Main.coachSession = Main.coachFactory.getCurrentSession();
		try {
			Main.coachSession.beginTransaction();
			coaches = Main.coachSession.createQuery("from Coach").list();
			Main.coachSession.getTransaction().rollback();
			coachBox = new ChoiceBox<Coach>();
			for(Coach coach : coaches) {
				coachBox.getItems().add(coach);
			}
			try {
				coachBox.setValue(coachBox.getItems().get(0));
				}
				catch(Exception e) {
					
				}
		} 
		finally {
			Main.coachSession.close();
		}
	
	}
	private void addTeam() {
		if(nameField.getText() == null || nameField.getText().trim().isEmpty() || tagField.getText() == null || tagField.getText().trim().isEmpty() || ownerField.getText() == null || ownerField.getText().trim().isEmpty())
			return;
		Team team = new Team(nameField.getText(),tagField.getText(),ownerField.getText(),leagueBox.getValue().getId(),coachBox.getValue().getId());
		try {
			Main.teamSession = Main.teamFactory.getCurrentSession();
			Main.teamSession.beginTransaction();
			Main.teamSession.save(team);
			Main.teamSession.getTransaction().commit();
			listView.getItems().add(team);
			nameField.clear();
			tagField.clear();
			ownerField.clear();
		} 
		finally {
			Main.teamSession.close();
		}
	}
	
	private void deleteTeam() {
		try {
			Team team = listView.getSelectionModel().getSelectedItem();
			Main.teamSession = Main.teamFactory.getCurrentSession();
			Main.teamSession.beginTransaction();
			Main.teamSession.delete(team);
			Main.teamSession.getTransaction().commit();
			listView.getItems().remove(team);
		} 
		finally {
			Main.teamSession.close();
		}
	}
}
