package sbd.adam.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sbd.adam.entity.League;

public class LeagueScene extends Scene{

	Label label;
	Button backButton;
	Button deleteButton;
	Button submitLeagueButton;
	VBox vBox;
	ListView<League> listView;
	List<League> leagues;
	TextField nameField;
	
	public LeagueScene(VBox vBox,int x,int y) {
		super(vBox,x,y);
		this.vBox = vBox;
		initializeComponents();
		addComponents();
	}
	
	private void addComponents() {
		vBox.getChildren().addAll(label,backButton,listView,nameField,submitLeagueButton,deleteButton);
	}
	
	private void initializeComponents() {
		
		label = new Label("Leagues");
		backButton = new Button("Back to menu");
		deleteButton = new Button("Delete League"); 
		nameField = new TextField("name");
		submitLeagueButton = new Button("Add league");
		
		backButton.setOnAction(e -> Main.backToMain());
		submitLeagueButton.setOnAction(e -> addLeague());
		deleteButton.setOnAction(e -> deleteLeague());
		
		refreshListView();
	}
	
	private void refreshListView() {
		Main.leagueSession = Main.leagueFactory.getCurrentSession();
		try {
			Main.leagueSession.beginTransaction();
			leagues = Main.leagueSession.createQuery("from League").list();
			Main.leagueSession.getTransaction().rollback();
			listView = new ListView<League>();
			for(League league : leagues) {
				listView.getItems().add(league);
			}
		} 
		finally {
			Main.leagueSession.close();
		}
	}
	
	private void addLeague() {
		if(nameField.getText() == null || nameField.getText().trim().isEmpty())
			return;
		League league = new League(nameField.getText());
		try {
			Main.leagueSession = Main.leagueFactory.getCurrentSession();
			Main.leagueSession.beginTransaction();
			Main.leagueSession.save(league);
			Main.leagueSession.getTransaction().commit();
			listView.getItems().add(league);
			nameField.clear();
		} 
		finally {
			Main.leagueSession.close();
		}
	}
	
	private void deleteLeague() {
		try {
			League league = listView.getSelectionModel().getSelectedItem();
			Main.leagueSession = Main.leagueFactory.getCurrentSession();
			Main.leagueSession.beginTransaction();
			Main.leagueSession.delete(league);
			Main.leagueSession.getTransaction().commit();
			listView.getItems().remove(league);
		} 
		finally {
			Main.leagueSession.close();
		}
	}
}
