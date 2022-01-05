package favorites;

public class FavoritesVO {

	private int id;
	private int pid;
	private String flag;
	private String like_member_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getLike_member_id() {
		return like_member_id;
	}

	public void setLike_member_id(String like_member_id) {
		this.like_member_id = like_member_id;
	}

}
