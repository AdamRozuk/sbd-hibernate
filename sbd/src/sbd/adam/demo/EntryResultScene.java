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

public class EntryResultScene extends Scene{

	Button submitButton;
	VBox vBox;
	List<Team> teams;
	TextField placeField;
	TournamentMatch tournamentMatch;
	Tournament tournament;
	Prizes prizes;
	ChoiceBox<String> resultField;
	
	public EntryResultScene(VBox vBox,int x,int y, TournamentMatch tournamentMatch, Tournament tournament) {
		super(vBox,x,y);
		this.vBox = vBox;
		this.tournamentMatch = tournamentMatch;
		this.tournament = tournament;
		initializeComponents();
		addComponents();
	}
	
	private void addComponents() {
		vBox.getChildren().addAll(submitButton,resultField,placeField);
	}
	
	private void initializeComponents() {
		
		resultField = new ChoiceBox<String>();
		resultField.getItems().addAll(tournamentMatch.getIdTeam1() + " team won",tournamentMatch.getIdTeam2() + " team won");
		resultField.setValue(resultField.getItems().get(0));
		placeField = new TextField("place");
		submitButton = new Button("Entry result");
		
		submitButton.setOnAction(e -> playMatch());
		
		Main.prizesSession = Main.prizesFactory.getCurrentSession();
		Main.prizesSession.beginTransaction();
		prizes = Main.prizesSession.get(Prizes.class, tournament.getIdprize());
		
		System.out.println(prizes.toString());
		
		Main.prizesSession.getTransaction().commit();
		
	}
	
	
	private void playMatch() {
			
			tournamentMatch.setPlace(placeField.getText());
			try {
				Main.tournamentMatchSession = Main.tournamentMatchFactory.getCurrentSession();
				Main.tournamentMatchSession.beginTransaction();
				if(resultField.getSelectionModel().getSelectedIndex()==0) {
					Main.tournamentMatchSession.createQuery("update TournamentMatch set result=0 where id=:param").setParameter("param", tournamentMatch.getId()).executeUpdate();
								
				}
				else {
					Main.tournamentMatchSession.createQuery("update TournamentMatch set result=1 where id=:param").setParameter("param", tournamentMatch.getId()).executeUpdate();
				}
				Main.tournamentMatchSession.createQuery("update TournamentMatch set place=:param2 where id=:param").setParameter("param", tournamentMatch.getId()).setParameter("param2", placeField.getText()).executeUpdate();
				tournamentMatch = Main.tournamentMatchSession.get(TournamentMatch.class,tournamentMatch.getId());
				Main.tournamentMatchSession.getTransaction().commit();
				placeField.clear();
			} 
			finally {
				Main.tournamentMatchSession.close();
			}
			if(tournamentMatch.getResult()==0) {
				AlertBox.display("Result", tournamentMatch.getIdTeam1() + " has won "+prizes.getValue1() +"\n" + tournamentMatch.getIdTeam2() + " has won " + prizes.getValue2());
			}
			else {

				AlertBox.display("Result", tournamentMatch.getIdTeam2() + " has won "+prizes.getValue1() +"\n" + tournamentMatch.getIdTeam1() + " has won " + prizes.getValue2());
			}
			
			Main.scene2 = new TournamentDetailsScene(new VBox(),800,600,tournament);
			Main.mainStage.setScene(Main.scene2);
			
			
			
		
			
	}
}