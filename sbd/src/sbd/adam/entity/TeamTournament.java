package sbd.adam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="team_tournament")
public class TeamTournament {

	@Id
	@Column(name="id_tournament")
	private int idTournament;
	
	
	@Column(name="id_team")
	private int idTeam;

	public TeamTournament(int idTournament, int idTeam) {
		super();
		this.idTournament = idTournament;
		this.idTeam = idTeam;
	}

	public int getIdTournament() {
		return idTournament;
	}

	public void setIdTournament(int idTournament) {
		this.idTournament = idTournament;
	}

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}

	@Override
	public String toString() {
		return "TeamTournament [idTournament=" + idTournament + ", idTeam=" + idTeam + "]";
	}
	

}
