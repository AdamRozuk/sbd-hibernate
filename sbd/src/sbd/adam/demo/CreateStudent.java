package sbd.adam.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

import sbd.adam.entity.*;

public class CreateStudent {

	/**
	 * @param args
	 */
	public static void blabla(String[] args) {
		// TODO Auto-generated method stub
		
		SessionFactory leagueFactory = new Configuration()
		.configure("hibernate.cfg.xml")
		.addAnnotatedClass(League.class)
		.buildSessionFactory();
		
		SessionFactory factory = new Configuration()
		.configure("hibernate.cfg.xml")
		.addAnnotatedClass(Team.class)
		.buildSessionFactory();
		
		SessionFactory coachFactory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Coach.class)
				.buildSessionFactory();
		
		SessionFactory leageMatchFactory= new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(LeagueMatch.class)
				.buildSessionFactory();
		
		SessionFactory playerFactory= new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Player.class)
				.buildSessionFactory();
		
		SessionFactory compositionFactory= new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Composition.class)
				.buildSessionFactory();
		
		SessionFactory prizesFactory= new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Prizes.class)
				.buildSessionFactory();

		SessionFactory tournamentFactory= new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Tournament.class)
				.buildSessionFactory();
		
		SessionFactory teamTournamentFactory= new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(TeamTournament.class)
				.buildSessionFactory();
		
		Session tournamentSession = tournamentFactory.getCurrentSession();
		Session teamTournamentSession = teamTournamentFactory.getCurrentSession();
		Session prizesSession = prizesFactory.getCurrentSession();
		Session compositionSession = compositionFactory.getCurrentSession();
		Session playerSession = playerFactory.getCurrentSession();
		Session leagueMatchSession = leageMatchFactory.getCurrentSession();
		Session sessionLeague = leagueFactory.getCurrentSession();
		Session session = factory.getCurrentSession();
		Session coachSession = coachFactory.getCurrentSession();
		
		try {
			
			Coach coach = new Coach("Coach", "Fnatic");
			coachSession.beginTransaction();
			//coachSession.save(coach);
			coachSession.getTransaction().commit();
			
			LeagueMatch leagueMatch = new LeagueMatch(4,5,"2018-01-01",0,"Berlin",1);
			leagueMatchSession.beginTransaction();
			//leagueMatchSession.save(leagueMatch);
			leagueMatchSession.getTransaction().commit();
			
			Composition composition = new Composition(4,1,"Gajowy");
			compositionSession.beginTransaction();
			//compositionSession.save(composition);
			compositionSession.getTransaction().commit();
			
			Player player = new Player("Jankos", "Smurf", 22);
			playerSession.beginTransaction();
			//playerSession.save(player);
			playerSession.getTransaction().commit();
			
			League ligaEsports = new League("EULCS");			
			sessionLeague.beginTransaction();
			//sessionLeague.save(ligaEsports);
			sessionLeague.getTransaction().commit();
			
			
			Team team = new Team("Fnatic","FNC","typ",1,5);
			session.beginTransaction();
			//session.save(team);
			session.getTransaction().commit();
			
			Prizes prizes = new Prizes(100,50,10);
			prizesSession.beginTransaction();
			//prizesSession.save(prizes);
			prizesSession.getTransaction().commit();
			
			Tournament tournament = new Tournament(2,"WorldChampionShip");
			tournamentSession.beginTransaction();
			//tournamentSession.save(tournament);
			tournamentSession.getTransaction().commit();
			
			TeamTournament teamTournament = new TeamTournament(1,5);
			teamTournamentSession.beginTransaction();
			//teamTournamentSession.save(teamTournament);
			teamTournamentSession.getTransaction().commit();
			
			/*
			session=factory.getCurrentSession();
			session.beginTransaction();
			tempStudent = session.get(Student.class, 2);
			session.getTransaction().commit();
			
			
			
			session.beginTransaction();
			
			List<Student> students = session
					.createQuery("from Student WHERE last_name LIKE 'ro%'")
					.getResultList();
			session.getTransaction().commit();
			
			for(Student student : students) {
							System.out.println(student);
						}
			
			session=factory.getCurrentSession();
			session.beginTransaction();
			session.createQuery("delete from Student WHERE last_name LIKE 'rorzug'").executeUpdate();
			session.getTransaction().commit();*/
			
			//System.out.println(students);
		}
		finally {
			factory.close();
			leagueFactory.close();
			coachFactory.close();
		}
		
	}

}
