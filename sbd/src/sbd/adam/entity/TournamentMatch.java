package sbd.adam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tournament_match")
public class TournamentMatch {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="id_team1")
	private int idTeam1;
	
	@Column(name="id_team2")
	private int idTeam2;
	
	@Column(name="result")
	private int result;
	
	@Column(name="place")
	private String place;
	
	@Column(name="id_tournament")
	private int idTournament;
	public TournamentMatch() {
		
	}
	public TournamentMatch(int idTeam1, int idTeam2,  int idTournament) {
		super();
		this.idTeam1 = idTeam1;
		this.idTeam2 = idTeam2;
		this.idTournament = idTournament;
	}

	public int getId() {
		return id;
	}

	public int getIdTeam1() {
		return idTeam1;
	}

	public void setIdTeam1(int idTeam1) {
		this.idTeam1 = idTeam1;
	}

	public int getIdTeam2() {
		return idTeam2;
	}

	public void setIdTeam2(int idTeam2) {
		this.idTeam2 = idTeam2;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getIdTournament() {
		return idTournament;
	}

	public void setIdTournament(int idTournament) {
		this.idTournament = idTournament;
	}

	@Override
	public String toString() {
		return "TournamentMatch [id=" + id + ", idTeam1=" + idTeam1 + ", idTeam2=" + idTeam2 + ", result=" + result + ", place=" + place + ", idTournament=" + idTournament + "]";
	}
	
	
}
