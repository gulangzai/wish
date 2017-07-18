package icom.yh.wish.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import icom.yh.wish.util.IDGenertor;

@Entity
@Table(name="wish_task")
public class WishTask {
	private int id;
	private int userId;
	private String key;
	private int isS = 0;
	private Timestamp time;
	private String taskId;
	public WishTask() {
	}
	public WishTask(int userId, String key, int isS, Timestamp time, String taskId) {
		super();
		this.userId = userId;
		this.key = key;
		this.isS = isS;
		this.time = time;
		this.taskId = taskId;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Column(name="_key")
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getIsS() {
		return isS;
	}
	public void setIsS(int isS) {
		this.isS = isS;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
