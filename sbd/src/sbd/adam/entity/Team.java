package sbd.adam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="team")
public class Team {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="id")
		private int id;
		
		@Column(name="name")
		private String name;
		
		@Column(name="tag")
		private String tag;
		
		@Column(name="owner")
		private String owner;
		
		@Column(name="id_league")
		private int idLeague;
		
		@Column(name="id_coach")
		private int idCoach;

		public Team(String name, String tag, String owner, int idLeague, int idCoach) {
			super();
			this.name = name;
			this.tag = tag;
			this.owner = owner;
			this.idLeague = idLeague;
			this.idCoach = idCoach;
		}
		public Team() {
			
		}
		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public String getOwner() {
			return owner;
		}

		public void setOwner(String owner) {
			this.owner = owner;
		}

		public int getIdLeague() {
			return idLeague;
		}

		public void setIdLeague(int idLeague) {
			this.idLeague = idLeague;
		}

		public int getIdCoach() {
			return idCoach;
		}

		public void setIdCoach(int idCoach) {
			this.idCoach = idCoach;
		}

		@Override
		public String toString() {
			return "Team [id=" + id + ", name=" + name + ", tag=" + tag + ", owner=" + owner + ", idLeague=" + idLeague
					+ ", idCoach=" + idCoach + "]";
		}
		
		

}
