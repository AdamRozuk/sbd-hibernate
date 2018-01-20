package sbd.adam.demo;

import java.util.List;


import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sbd.adam.entity.Coach;

public class CoachScene extends Scene{

	Label label;
	Button backButton;
	Button deleteButton;
	Button submitCoachButton;
	VBox vBox;
	ListView<Coach> listView;
	List<Coach> coaches;
	TextField firstNameField;
	TextField lastNameField;
	
	public CoachScene(VBox vBox,int x,int y) {
		super(vBox,x,y);
		this.vBox = vBox;
		initializeComponents();
		addComponents();
	}
	
	private void addComponents() {
		vBox.getChildren().addAll(label,backButton,listView,firstNameField,lastNameField,submitCoachButton,deleteButton);
	}
	
	private void initializeComponents() {
		
		label = new Label("Coaches");
		backButton = new Button("Back to menu");
		deleteButton = new Button("Delete Coach"); 
		firstNameField = new TextField("firstname");
		lastNameField = new TextField("lastname");
		submitCoachButton = new Button("Add coach");
		
		backButton.setOnAction(e -> Main.backToMain());
		submitCoachButton.setOnAction(e -> addCoach());
		deleteButton.setOnAction(e -> deleteCoach());
		
		refreshListView();
	}
	
	private void refreshListView() {
		Main.coachSession = Main.coachFactory.getCurrentSession();
		try {
			Main.coachSession.beginTransaction();
			coaches = Main.coachSession.createQuery("from Coach").list();
			Main.coachSession.getTransaction().rollback();
			listView = new ListView<Coach>();
			for(Coach coach : coaches) {
				listView.getItems().add(coach);
			}
		} 
		finally {
			Main.coachSession.close();
		}
	}
	
	private void addCoach() {
		if(firstNameField.getText() == null || firstNameField.getText().trim().isEmpty() || lastNameField.getText() == null || lastNameField.getText().trim().isEmpty())
			return;
		Coach coach = new Coach(firstNameField.getText(), lastNameField.getText());
		try {
			Main.coachSession = Main.coachFactory.getCurrentSession();
			Main.coachSession.beginTransaction();
			Main.coachSession.save(coach);
			Main.coachSession.getTransaction().commit();
			listView.getItems().add(coach);
			firstNameField.clear();
			lastNameField.clear();
		} 
		finally {
			Main.coachSession.close();
		}
	}
	
	private void deleteCoach() {
		try {
			Coach coach = listView.getSelectionModel().getSelectedItem();
			Main.coachSession = Main.coachFactory.getCurrentSession();
			Main.coachSession.beginTransaction();
			Main.coachSession.delete(coach);
			Main.coachSession.getTransaction().commit();
			listView.getItems().remove(coach);
		} 
		finally {
			Main.coachSession.close();
		}
	}
}
