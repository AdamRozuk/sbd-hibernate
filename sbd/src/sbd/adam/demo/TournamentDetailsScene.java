package sbd.adam.demo;

import java.util.List;


import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import sbd.adam.entity.*;

public class TournamentDetailsScene extends Scene{

	Label label;
	Button backButton;
	Button deleteButton;
	Button submitButton;
	Button entryResult;
	VBox vBox;
	Tournament tournament;
	int tournamentId;
	ListView<TeamTournament> listView;
	List<Team> teamsAdd;
	List<TeamTournament> teamTournament;
	List<Team> teamsJoined;
	List<TournamentMatch> matches;
	ListView<TournamentMatch> tournamentMatches;
	ChoiceBox<Team> teamBox;
	public TournamentDetailsScene(VBox vBox,int x,int y, Tournament tournament) {

		super(vBox,x,y);

		this.vBox = vBox;
		this.tournament=tournament;

		initializeComponents();
		addComponents();

	}
	
	private void addComponents() {
		vBox.getChildren().addAll(label,backButton,listView,submitButton,deleteButton,teamBox,tournamentMatches,entryResult);
	}
	
	private void initializeComponents() {
		
		label = new Label(tournament.toString());
		backButton = new Button("Back to tournaments");
		deleteButton = new Button("Delete team from tournament"); 
		submitButton = new Button("Add team to tournament");
		entryResult = new Button("Entry result for selected match");
		teamBox = new ChoiceBox<Team>();
		tournamentMatches = new ListView<TournamentMatch>();
		
		backButton.setOnAction(e -> backToTournaments());
		submitButton.setOnAction(e -> addTeam());
		deleteButton.setOnAction(e -> deleteTeam());
		entryResult.setOnAction(e -> entryResult());
		
		refreshListView();
		refreshTeamBox();
	}
	
	private void refreshListView() {
		Main.teamTournamentSession = Main.teamTournamentFactory.getCurrentSession();
		try {
			Main.teamTournamentSession.beginTransaction();
			tournamentId = tournament.getId();
			teamTournament = Main.teamTournamentSession.createQuery("from TeamTournament tabela where tabela.idTournament = :param").setParameter("param", tournamentId).list();
			Main.teamTournamentSession.getTransaction().rollback();
			listView = new ListView<TeamTournament>();
			for(TeamTournament tT : teamTournament) {
				listView.getItems().add(tT);
			}
		} 
		finally {
			Main.teamTournamentSession.close();
		}
		Main.tournamentMatchSession = Main.tournamentMatchFactory.getCurrentSession();
		try {
			Main.tournamentMatchSession.beginTransaction();
			matches = Main.tournamentMatchSession.createQuery("from TournamentMatch tM where tM.idTournament = :param").setParameter("param", tournament.getId()).list();
			Main.tournamentMatchSession.getTransaction().rollback();
			tournamentMatches = new ListView<TournamentMatch>();
			for(TournamentMatch tournamentMatch : matches) {
				tournamentMatches.getItems().add(tournamentMatch);
			}
		} 
		finally {
			Main.tournamentMatchSession.close();
		}
	}
	private void refreshTeamBox() {
		Main.teamSession = Main.teamFactory.getCurrentSession();
		try {
			Main.teamSession.beginTransaction();
			teamsAdd = Main.teamSession.createQuery("from Team").list();
			Main.teamSession.getTransaction().rollback();
			teamBox = new ChoiceBox<Team>();
			for(Team team : teamsAdd) {
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
	
	private void addTeam() {
		if(listView.getItems().size()<2) {
			TeamTournament tT = new TeamTournament(tournament.getId(),teamBox.getValue().getId());
	
			try {
				Main.teamTournamentSession = Main.teamTournamentFactory.getCurrentSession();
				Main.teamTournamentSession.beginTransaction();
				Main.teamTournamentSession.save(tT);
				Main.teamTournamentSession.getTransaction().commit();
				listView.getItems().add(tT);
			} 
			finally {
				Main.teamTournamentSession.close();
			}
			if(listView.getItems().size()==2) {
				TournamentMatch tM = new TournamentMatch(listView.getItems().get(0).getIdTeam(),listView.getItems().get(1).getIdTeam(),tournament.getId());
				try {
					Main.tournamentMatchSession = Main.tournamentMatchFactory.getCurrentSession();
					Main.tournamentMatchSession.beginTransaction();
					Main.tournamentMatchSession.save(tM);
					Main.tournamentMatchSession.getTransaction().commit();
					tournamentMatches.getItems().add(tM);
				} 
				finally {
					Main.tournamentMatchSession.close();
				}
				
				vBox.getChildren().remove(tournamentMatches);
				refreshListView();
				vBox.getChildren().add(6,tournamentMatches);
				
			}
		}
		
	}
	private void entryResult() {
		if(tournamentMatches.getSelectionModel().getSelectedItem().getPlace()==null) {
			Main.scene2 = new EntryResultScene(new VBox(), 800,600,tournamentMatches.getSelectionModel().getSelectedItem(),tournament);
			Main.mainStage.setScene(Main.scene2);
		}
	}
	private void deleteTeam() {
		if(listView.getItems().size()<2) {
			try {
				
				TeamTournament tT = listView.getSelectionModel().getSelectedItem();
			
				Main.teamTournamentSession = Main.teamTournamentFactory.getCurrentSession();
				Main.teamTournamentSession.beginTransaction();
				Main.teamTournamentSession.createQuery("delete from TeamTournament t where t.idTournament=:param1 and t.idTeam = :param2").setParameter("param1", tournament.getId()).setParameter("param2", tT.getIdTeam()).executeUpdate();
				Main.teamTournamentSession.getTransaction().commit();
				//Main.scene2 = new TournamentDetailsScene(new VBox(),800,600,tournament);
				//Main.mainStage.setScene(Main.scene2);
				vBox.getChildren().remove(listView);
				refreshListView();
				vBox.getChildren().add(2,listView);
			} 
			finally {
				Main.teamTournamentSession.close();
			}
		}
		
	}
	private void backToTournaments() {
		Main.scene2 = new TournamentScene(new VBox(),800,600);
		Main.mainStage.setScene(Main.scene2);
	}
}
