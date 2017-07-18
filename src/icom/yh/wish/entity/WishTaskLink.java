package icom.yh.wish.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wish_task_link")
public class WishTaskLink {
	private int id;
	private int taskId;
	private int sdId;
	public WishTaskLink() {
	}
	public WishTaskLink(int taskId, int sdId) {
		super();
		this.taskId = taskId;
		this.sdId = sdId;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getSdId() {
		return sdId;
	}
	public void setSdId(int sdId) {
		this.sdId = sdId;
	}
}
