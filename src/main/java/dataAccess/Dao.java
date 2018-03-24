package dataAccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import data_objects.Chatlogs;
import data_objects.Friends;
import data_objects.Users;

public class Dao {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chat");
	private EntityManager em = emf.createEntityManager();
	
	public Dao() {
	}
	
	public Users getUser(String username) {
		try {
			Users user = (Users) em.createNamedQuery("Users.byUsername").setParameter(1, username).getSingleResult();
			return user;
		} catch (Exception e) {
			System.out.println(e + " in Jpa_user getUser");
		}
		return null;
	}

	public void updateUserName(int userid, String name) {
		try {
			em.getTransaction().begin();
			Users user = em.find(Users.class, userid);
			user.setUsername(name);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e + "in Jpa_user");
		}
	}

	public void updateUserPassword(int userid, String Password) {
		try {
			em.getTransaction().begin();

			Users user =  em.find(Users.class, userid);
			user.setPassword(Password);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e + "in Jpa_user");
		} 
	}

	public void addUser(Users user) {
		try {
			em.getTransaction().begin();// get trans
			em.persist(user);//
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e + " in Jpa_user");
		}
	}

	public void removeUser(int userID) {
		try {
			Users user = em.find(Users.class, userID);
			if (user != null) {
				em.remove(user);
			}
		} catch (Exception e) {
			System.out.println(e + "in Jpa_user");
		} 
	}


	
	
	///////////////////////////////////
	
	
	public void addChatlogs(Chatlogs c){   
    	try{  
    		em.getTransaction().begin();//get trans 
    		em.persist(c);//
    		em.getTransaction().commit();
    	}catch(Exception e){
    		System.out.println(e+"in Jpa_user");
    	}
	} 
	@SuppressWarnings("unchecked")
	public ArrayList<Chatlogs> retriveChatlogs(){
		try{ 
			ArrayList<Chatlogs> al = (ArrayList<Chatlogs>) em.createNamedQuery("Chatlogs.call").getResultList();
			Collections.sort(al);
			return al;
		}catch(Exception e){
    		System.out.println(e+" in Jpa_user getUser");
    	}
		return null;
	}
	
	public void addFriends(String u1,String u2) {
		try {
			int size = em.createNamedQuery("Friends.has").setParameter(1, u2).setParameter(2, u1).getResultList().size();
			if(size == 0){
				Friends f = new Friends(u1,u2);
				em.getTransaction().begin();// get trans
				em.persist(f);//
				em.getTransaction().commit();
			}
			
		} catch (Exception e) {
			System.out.println(e + " in Jpa_user");
		}
	}
	
	public List<Friends> getFriends(String userid){
		try{
			@SuppressWarnings("unchecked")
			List<Friends> friends = em.createNamedQuery("Friends.all").setParameter(1, userid).getResultList();
			return friends;
		}catch(Exception e){
			System.out.println(e+ " in getFriends");
		}
		return null;
		
	}
	
	public int chatSize(){
		try{
			int result = (int) em.createNamedQuery("c.chatid").getSingleResult();
			return result;
		}catch(Exception e){
			System.out.println(e+ " in getFriends");
		}
		return 0;
	}
	
}
