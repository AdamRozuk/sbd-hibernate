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
import sbd.adam.entity.Prizes;

public class PrizesScene extends Scene{

	Label label;
	Button backButton;
	Button deleteButton;
	Button submitPrizesButton;
	VBox vBox;
	ListView<Prizes> listView;
	List<Prizes> prizes;
	TextField prizes1Field,prizes2Field,prizes3Field;
	public PrizesScene(VBox vBox,int x,int y) {
		super(vBox,x,y);
		this.vBox = vBox;
		initializeComponents();
		addComponents();
	}
	
	private void addComponents() {
		vBox.getChildren().addAll(label,backButton,listView,prizes1Field,prizes2Field,prizes3Field,submitPrizesButton,deleteButton);
	}
	
	private void initializeComponents() {
		
		label = new Label("Prizess");
		backButton = new Button("Back to menu");
		deleteButton = new Button("Delete Prizes"); 
		prizes1Field = new TextField("prize1");
		prizes2Field = new TextField("prize2");
		prizes3Field = new TextField("prize3");
		submitPrizesButton = new Button("Add prizes");
		
		backButton.setOnAction(e -> Main.backToMain());
		submitPrizesButton.setOnAction(e -> addPrizes());
		deleteButton.setOnAction(e -> deletePrizes());
		
		refreshListView();
	}
	
	private void refreshListView() {
		Main.prizesSession = Main.prizesFactory.getCurrentSession();
		try {
			Main.prizesSession.beginTransaction();
			prizes = Main.prizesSession.createQuery("from Prizes").list();
			Main.prizesSession.getTransaction().rollback();
			listView = new ListView<Prizes>();
			for(Prizes prize : prizes) {
				listView.getItems().add(prize);
			}
		} 
		finally {
			Main.prizesSession.close();
		}
	}
	
	private void addPrizes() {
		if(Main.isInteger(prizes1Field.getText())==false || Main.isInteger(prizes2Field.getText())==false || Main.isInteger(prizes3Field.getText())==false)
			return;
		Prizes prize = new Prizes(Integer.parseInt(prizes1Field.getText()),Integer.parseInt(prizes2Field.getText()),Integer.parseInt(prizes3Field.getText()));
		
		try {
			Main.prizesSession = Main.prizesFactory.getCurrentSession();
			Main.prizesSession.beginTransaction();
			Main.prizesSession.save(prize);
			Main.prizesSession.getTransaction().commit();
			listView.getItems().add(prize);
			prizes1Field.clear();
			prizes2Field.clear();
			prizes3Field.clear();
		} 
		finally {
			Main.prizesSession.close();
		}
	}
	
	private void deletePrizes() {
		try {
			Prizes prize = listView.getSelectionModel().getSelectedItem();
			Main.prizesSession = Main.prizesFactory.getCurrentSession();
			Main.prizesSession.beginTransaction();
			Main.prizesSession.delete(prize);
			Main.prizesSession.getTransaction().commit();
			listView.getItems().remove(prize);
		} 
		finally {
			Main.prizesSession.close();
		}
	}
}
