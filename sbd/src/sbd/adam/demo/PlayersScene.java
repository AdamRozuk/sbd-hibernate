package sbd.adam.demo;

import java.util.List;


import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sbd.adam.entity.Player;

public class PlayersScene extends Scene{

	Label label;
	Button backButton;
	Button deleteButton;
	Button submitPlayerButton;
	VBox vBox;
	ListView<Player> listView;
	List<Player> players;
	TextField firstNameField;
	TextField lastNameField;
	TextField age;
	
	public PlayersScene(VBox vBox,int x,int y) {
		super(vBox,x,y);
		this.vBox = vBox;
		initializeComponents();
		addComponents();
	}
	
	private void addComponents() {
		vBox.getChildren().addAll(label,backButton,listView,firstNameField,lastNameField,submitPlayerButton,deleteButton,age);
	}
	
	private void initializeComponents() {
		
		label = new Label("Players");
		backButton = new Button("Back to menu");
		deleteButton = new Button("Delete Player"); 
		firstNameField = new TextField("firstname");
		lastNameField = new TextField("lastname");
		submitPlayerButton = new Button("Add player");
		age = new TextField("9");

		backButton.setOnAction(e -> Main.backToMain());
		submitPlayerButton.setOnAction(e -> addPlayer());
		deleteButton.setOnAction(e -> deletePlayer());

		refreshListView();
	}
	
	private void refreshListView() {
		
		Main.playerSession = Main.playerFactory.getCurrentSession();

		try {
			Main.playerSession.beginTransaction();

			System.out.println("dupa");
			players = Main.playerSession.createQuery("from Player").list();

			System.out.println("dupa1");
			Main.playerSession.getTransaction().rollback();
			listView = new ListView<Player>();
			for(Player player : players) {
				listView.getItems().add(player);
			}

			System.out.println("dupa2");
		} 
		finally {
			Main.playerSession.close();
		}
	}
	
	private void addPlayer() {
		if(firstNameField.getText() == null || firstNameField.getText().trim().isEmpty() || lastNameField.getText() == null || lastNameField.getText().trim().isEmpty())
			return;
		Player player = new Player(firstNameField.getText(), lastNameField.getText(),666);
		try {
			Main.playerSession = Main.playerFactory.getCurrentSession();
			Main.playerSession.beginTransaction();
			Main.playerSession.save(player);
			Main.playerSession.getTransaction().commit();
			listView.getItems().add(player);
			firstNameField.clear();
			lastNameField.clear();
			age.clear();
		} 
		finally {
			Main.playerSession.close();
		}
	}
	
	private void deletePlayer() {
		try {
			Player player = listView.getSelectionModel().getSelectedItem();
			Main.playerSession = Main.playerFactory.getCurrentSession();
			Main.playerSession.beginTransaction();
			Main.playerSession.delete(player);
			Main.playerSession.getTransaction().commit();
			listView.getItems().remove(player);
		} 
		finally {
			Main.playerSession.close();
		}
	}
}
