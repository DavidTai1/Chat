package data_objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Chatlogs")
@NamedQueries(value = { @NamedQuery(name = "Chatlogs.call", query = "SELECT c FROM Chatlogs c"),
		@NamedQuery(name = "Chatlogs.cbyUsername", query = "SELECT c FROM Chatlogs c where (c.from_userid = ?1 and c.to_userid= ?2) or "
				+ "(c.from_userid = ?2 and c.to_userid= ?1)"),
		@NamedQuery(name = "Chatlogs.size", query= "SELECT count(c.chatid) FROM Chatlogs c")})
public class Chatlogs implements Comparable<Chatlogs>{	
	@Id
	@GeneratedValue(generator = "jpa_chat_id")
	@SequenceGenerator(name = "jpa_chat_id", sequenceName = "CHAT_ID_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name="chatid")
	private int chatid;
	@Column(name="from_userid")
	private String from_userid;
	@Column(name="to_userid")
	private String to_userid;
	@Column(name="timestamp")
	private String timestamp;
	@Column(name="message")
	private String message;
	
	@ManyToOne(targetEntity=Users.class,fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	private Users users; 
	
	
	public Chatlogs() {
	}
	
	public Chatlogs(String from,String to,String time,String mess){
		this.from_userid = from;
		this.to_userid = to;
		this.timestamp = time;
		this.message = mess;
	}
	public int get_chatid(){
		return chatid;
	}
	public int getChatid() {
		return chatid;
	}

	public void setChatid(int chatid) {
		this.chatid = chatid;
	}

	public String getFrom_userid() {
		return from_userid;
	}

	public void setFrom_userid(String from_userid) {
		this.from_userid = from_userid;
	}

	public String getTo_userid() {
		return to_userid;
	}

	public void setTo_userid(String to_userid) {
		this.to_userid = to_userid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	


	@Override
	public int compareTo(Chatlogs c) {
		if(this.chatid < c.chatid){
			return -1;
		}
		else{
			return 1;
		}
	}
}
