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
import sbd.adam.entity.Tournament;
import sbd.adam.entity.Prizes;

public class TournamentScene extends Scene{

	Label label;
	Button backButton;
	Button deleteButton;
	Button submitTournamentButton;
	Button goToTournamentButton;
	VBox vBox;
	ListView<Tournament> listView;
	List<Tournament> tournaments;
	List<Prizes> prizes;
	TextField nameField;
	ChoiceBox<Prizes> prizeBox;
	
	public TournamentScene(VBox vBox,int x,int y) {
		super(vBox,x,y);
		this.vBox = vBox;
		initializeComponents();
		addComponents();
	}
	
	private void addComponents() {
		vBox.getChildren().addAll(label,backButton,listView,nameField,submitTournamentButton,deleteButton,prizeBox,goToTournamentButton);
	}
	
	private void initializeComponents() {

		label = new Label("Tournaments");
		backButton = new Button("Back to menu");
		deleteButton = new Button("Delete Tournament"); 
		nameField = new TextField("name");
		submitTournamentButton = new Button("Add tournament");
		goToTournamentButton = new Button("Go to selected tournament");
		prizeBox = new ChoiceBox<Prizes>();
		backButton.setOnAction(e -> Main.backToMain());
		submitTournamentButton.setOnAction(e -> addTournament());
		deleteButton.setOnAction(e -> deleteTournament());


		goToTournamentButton.setOnAction(e -> goToTournament());
		
		refreshListView();
		refreshPrizesBox();
	}
	
	private void refreshListView() {
		Main.tournamentsSession = Main.tournamentsFactory.getCurrentSession();
		try {
			Main.tournamentsSession.beginTransaction();
			tournaments = Main.tournamentsSession.createQuery("from Tournament").list();
			Main.tournamentsSession.getTransaction().rollback();
			listView = new ListView<Tournament>();
			for(Tournament tournament : tournaments) {
				listView.getItems().add(tournament);
			}
		} 
		finally {
			Main.tournamentsSession.close();
		}
	}
	private void refreshPrizesBox() {
		Main.prizesSession = Main.prizesFactory.getCurrentSession();
		try {
			Main.prizesSession.beginTransaction();
			prizes = Main.prizesSession.createQuery("from Prizes").list();
			Main.prizesSession.getTransaction().rollback();
			prizeBox = new ChoiceBox<Prizes>();
			for(Prizes prize : prizes) {
				prizeBox.getItems().add(prize);
				
			}
			try {
			prizeBox.setValue(prizeBox.getItems().get(0));
			}
			catch(Exception e) {
				
			}
		} 
		finally {
			Main.prizesSession.close();
		}
	}

	private void addTournament() {
		if(nameField.getText() == null || nameField.getText().trim().isEmpty() )
			return;
		Tournament tournament = new Tournament(prizeBox.getValue().getId(),nameField.getText());
		try {
			Main.tournamentsSession = Main.tournamentsFactory.getCurrentSession();
			Main.tournamentsSession.beginTransaction();
			Main.tournamentsSession.save(tournament);
			Main.tournamentsSession.getTransaction().commit();
			listView.getItems().add(tournament);
			nameField.clear();
		} 
		finally {
			Main.tournamentsSession.close();
		}
	}
	
	private void deleteTournament() {
		try {
			Tournament tournament = listView.getSelectionModel().getSelectedItem();
			Main.tournamentsSession = Main.tournamentsFactory.getCurrentSession();
			Main.tournamentsSession.beginTransaction();
			Main.tournamentsSession.delete(tournament);
			Main.tournamentsSession.getTransaction().commit();
			listView.getItems().remove(tournament);
		} 
		finally {
			Main.tournamentsSession.close();
		}
	}
	private void goToTournament() {
		
		Main.scene2 = new TournamentDetailsScene(new VBox(),800,600,listView.getSelectionModel().getSelectedItem());
		

		Main.mainStage.setScene(Main.scene2);
	}
}
