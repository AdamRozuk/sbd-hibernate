package sbd.adam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tournament")
public class Tournament {

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="id")
		private int id;
		
		@Column(name="id_prize")
		private int idprize;
		
		@Column(name="name")
		private String name;

		public Tournament(int idprize, String name) {
			super();
			this.idprize = idprize;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public int getIdprize() {
			return idprize;
		}

		public void setIdprize(int idprize) {
			this.idprize = idprize;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public Tournament() {
			
		}

		@Override
		public String toString() {
			return "Tournament [id=" + id + ", idprize=" + idprize + ", name=" + name + "]";
		}

}
