package dataAccess;
import java.text.SimpleDateFormat;


import data_objects.Chatlogs;
import data_objects.Friends;
import data_objects.Users;

public class Jpa_Runner {
	public static void main(String[] args) {
		Dao d = new Dao();

		d.addUser(new Users("user1","a"));
		d.addUser(new Users("user2","a"));
		d.addUser(new Users("user3","a"));
		d.addUser(new Users("user4","a"));
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss").format(new java.util.Date());
		Chatlogs c1 = new Chatlogs("user1","user2",timeStamp,"hello1");
		Chatlogs c2 = new Chatlogs("user2","user1",timeStamp,"hello2");
		Chatlogs c3 = new Chatlogs("user1","user2",timeStamp,"hello3");
		Chatlogs c4 = new Chatlogs("user2","user1",timeStamp,"hello4");
		Chatlogs c5 = new Chatlogs("user1","user2",timeStamp,"hello5");
		Chatlogs c6 = new Chatlogs("user2","user1",timeStamp,"hello6");
		Chatlogs c7 = new Chatlogs("user1","user2",timeStamp,"hello7");
		Chatlogs c8 = new Chatlogs("user2","user1",timeStamp,"hello8");
		d.addChatlogs(c1);
		d.addChatlogs(c2);
		d.addChatlogs(c3);
		d.addChatlogs(c4);
		d.addChatlogs(c5);
		d.addChatlogs(c6);
		d.addChatlogs(c7);
		d.addChatlogs(c8);
		d.addFriends("user1","user2");
		d.addFriends("user1","user3");
		d.addFriends("user1","user4");
		d.addFriends("user2","user1");
		d.addFriends("user2","user3");
		d.addFriends("user2","user4");
		d.addFriends("user3","user1");
		d.addFriends("user3","user2");
		d.addFriends("user3","user4");
		d.addFriends("user4","user1");
		d.addFriends("user4","user2");
		d.addFriends("user4","user3");
		
		for(Friends f:d.getFriends("user1")){
			System.out.println(f.getFriendid());
		};
	}
	
}
