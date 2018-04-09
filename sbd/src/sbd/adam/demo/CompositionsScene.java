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
import sbd.adam.entity.Composition;
import sbd.adam.entity.Team;
import sbd.adam.entity.Player;

public class CompositionsScene extends Scene{

	Label label;
	Button backButton;
	Button deleteButton;
	Button submitCompositionButton;
	VBox vBox;
	ListView<Composition> listView;
	List<Composition> compositions;
	List<Player> players;
	List<Team> teams;
	TextField roleField;
	ChoiceBox<Team> teamBox;
	ChoiceBox<Player> playerBox;
	
	public CompositionsScene(VBox vBox,int x,int y) {
		super(vBox,x,y);
		this.vBox = vBox;
		initializeComponents();
		addComponents();
	}
	
	private void addComponents() {
		vBox.getChildren().addAll(label,backButton,listView,roleField,submitCompositionButton,deleteButton,playerBox,teamBox);
	}
	
	private void initializeComponents() {
		
		label = new Label("Compositions");
		backButton = new Button("Back to menu");
		deleteButton = new Button("Delete Composition"); 
		roleField = new TextField("name");
		submitCompositionButton = new Button("Add composition");
		playerBox = new ChoiceBox<Player>();
		teamBox = new ChoiceBox<Team>();
		
		backButton.setOnAction(e -> Main.backToMain());
		submitCompositionButton.setOnAction(e -> addComposition());
		deleteButton.setOnAction(e -> deleteComposition());
		
		refreshListView();
		refreshTeamBox();
		refreshPlayerBox();
	}
	
	private void refreshListView() {
		Main.compositionsSession = Main.compositionsFactory.getCurrentSession();
		try {
			Main.compositionsSession.beginTransaction();
			compositions = Main.compositionsSession.createQuery("from Composition").list();
			Main.compositionsSession.getTransaction().rollback();
			listView = new ListView<Composition>();
			for(Composition composition : compositions) {
				listView.getItems().add(composition);
			}
		} 
		finally {
			Main.compositionsSession.close();
		}
	}
	private void refreshPlayerBox() {
		Main.playerSession = Main.playerFactory.getCurrentSession();
		try {
			Main.playerSession.beginTransaction();
			players = Main.playerSession.createQuery("from Player").list();
			Main.playerSession.getTransaction().rollback();
			playerBox = new ChoiceBox<Player>();
			for(Player player : players) {
				playerBox.getItems().add(player);
				
			}
			try {
			playerBox.setValue(playerBox.getItems().get(0));
			}
			catch(Exception e) {
				
			}
		} 
		finally {
			Main.playerSession.close();
		}
	}
	private void refreshTeamBox() {
		Main.teamSession = Main.teamFactory.getCurrentSession();
		try {
			Main.teamSession.beginTransaction();
			teams = Main.teamSession.createQuery("from Team").list();
			Main.teamSession.getTransaction().rollback();
			teamBox = new ChoiceBox<Team>();
			for(Team team : teams) {
				teamBox.getItems().add(team);
			}
			try {
				teamBox.setValue(teamBox.getItems().get(0));
				}
				catch(Exception e) {
					
				}
		} 
		finally {
			Main.teamSession.close();
		}
	
	}
	private void addComposition() {
		if(roleField.getText() == null || roleField.getText().trim().isEmpty() )
			return;
		Composition composition = new Composition(teamBox.getValue().getId(),playerBox.getValue().getId(),roleField.getText());
		try {
			Main.compositionsSession = Main.compositionsFactory.getCurrentSession();
			Main.compositionsSession.beginTransaction();
			Main.compositionsSession.save(composition);
			Main.compositionsSession.getTransaction().commit();
			listView.getItems().add(composition);
			roleField.clear();
		} 
		finally {
			Main.compositionsSession.close();
		}
	}
	
	private void deleteComposition() {
		try {
			Composition composition = listView.getSelectionModel().getSelectedItem();
			Main.compositionsSession = Main.compositionsFactory.getCurrentSession();
			Main.compositionsSession.beginTransaction();
			Main.compositionsSession.delete(composition);
			Main.compositionsSession.getTransaction().commit();
			listView.getItems().remove(composition);
		} 
		finally {
			Main.compositionsSession.close();
		}
	}
}
