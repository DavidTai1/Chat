package data_objects;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
@NamedQueries(value = { @NamedQuery(name = "Users.all", query = "SELECT u FROM Users u"),
		@NamedQuery(name = "Users.byUsername", query = "SELECT u FROM Users u where u.username = ?1"),
		@NamedQuery(name = "Users.login", query = "SELECT u FROM Users u where u.username = :uname and u.password = :pw") })

public class Users {
	@Id
	@GeneratedValue(generator = "jpa_user_id")
	@SequenceGenerator(name = "jpa_user_id", sequenceName = "USER_ID_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "userid")
	private int userid;

	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;

	@OneToMany(targetEntity=Chatlogs.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "users")
	List<Chatlogs> user_chats;

	public Users() {
	}

	public Users(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Chatlogs> getUser_chats() {
		return user_chats;
	}

	public void setUser_chats(List<Chatlogs> user_chats) {
		this.user_chats = user_chats;
	}

	public String to_string() {
		StringBuffer sb = new StringBuffer();
		sb.append("Userid: ");
		sb.append(userid);
		sb.append("Username: ");
		sb.append(username);
		sb.append("Password: ");
		sb.append(password);
		return sb.toString();
	}
}
