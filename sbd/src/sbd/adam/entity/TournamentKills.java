package sbd.adam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tournament_kills")
public class TournamentKills {

	@Id
	@Column(name="id_match")
	private int idMatch;
	
	@Column(name="id_player")
	private String idPlayer;
	
	@Column(name="time")
	private String time;

	public TournamentKills(int idMatch, String idPlayer, String time) {
		super();
		this.idMatch = idMatch;
		this.idPlayer = idPlayer;
		this.time = time;
	}

	public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	public String getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(String idPlayer) {
		this.idPlayer = idPlayer;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "TournamentKills [idMatch=" + idMatch + ", idPlayer=" + idPlayer + ", time=" + time + "]";
	}


}
