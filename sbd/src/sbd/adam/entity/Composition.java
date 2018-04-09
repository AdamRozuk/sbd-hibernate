package sbd.adam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="composition")
public class Composition {
	
	@Id
	@Column(name="id_player")
	private int idPlayer;
	
	@Column(name="id_team")
	private int idTeam;
	
	@Column(name="role")
	private String role;

	public Composition() {
		
	}
	public Composition( int idTeam,int idPlayer, String role) {
		super();
		this.idPlayer = idPlayer;
		this.idTeam = idTeam;
		this.role = role;
	}

	public int getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Composition [idPlayer=" + idPlayer + ", idTeam=" + idTeam + ", role=" + role + "]";
	}



}
