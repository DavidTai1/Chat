package jpa.basetest;

import javax.persistence.EntityManager;  
import javax.persistence.EntityTransaction;  
  
import data_objects.Users; 
import dataAccess.Jpa_Runner;
  
public class BaseTest {  
 
    /**  
     * @param args  
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
  
        Users user =new Users().set_username("admin").set_password("passwrod");  
        addUser(user);  
    }  
      
    static void addUser(Users user){  
    	EntityManager em=null;  
    	EntityTransaction  tx=null;  
    	try{  
    		em=Jpa_Runner.getEntiryManager();//get jpa  
    		tx=em.getTransaction();//get trans 
    		tx.begin();//
    		em.persist(user);//
    		tx.commit();//
    	}finally{  
    		if(em!=null)  
    			em.close();  
    	}  
	}  
  
 
} 

