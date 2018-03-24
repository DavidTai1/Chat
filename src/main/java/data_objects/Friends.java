package data_objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries(value = { @NamedQuery(name = "Friends.all", query = "SELECT f FROM Friends f where f.userid = ?1"),
		@NamedQuery(name = "Friends.has", query = "SELECT f FROM Friends f where f.friendid = ?1 and f.userid = ?2")})
@Table(name = "Friends")

public class Friends {
	@Id
	@GeneratedValue(generator = "friend_id")
	@SequenceGenerator(name = "friend_id", sequenceName = "FRIEND_ID_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "fid")
	private int fid;
	@Column(name = "userid")
	private String userid;
	@Column(name = "friendid")
	private String friendid;
	
	public Friends() {
		// TODO Auto-generated constructor stub
	}
	public Friends(String userid, String friendid){
		this.userid=userid;
		this.friendid=friendid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFriendid() {
		return friendid;
	}
	public void setFriendid(String friendid) {
		this.friendid = friendid;
	}
}
