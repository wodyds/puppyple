package pet;

public class PetVO {

	private int pet_id;
	private String member_id;
	private String pet_name;
	private String pet_breed;
	private String pet_age;
	private String pet_gender;
	private String pet_nuetering;
	private String pet_weight;
	private String pet_filename;
	private String pet_filepath;
	private String member_nickname;

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public String getPet_filepath() {
		return pet_filepath;
	}

	public void setPet_filepath(String pet_filepath) {
		this.pet_filepath = pet_filepath;
	}

	public int getPet_id() {
		return pet_id;
	}

	public void setPet_id(int pet_id) {
		this.pet_id = pet_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getPet_name() {
		return pet_name;
	}

	public void setPet_name(String pet_name) {
		this.pet_name = pet_name;
	}

	public String getPet_breed() {
		return pet_breed;
	}

	public void setPet_breed(String pet_breed) {
		this.pet_breed = pet_breed;
	}

	public String getPet_age() {
		return pet_age;
	}

	public void setPet_age(String pet_age) {
		this.pet_age = pet_age;
	}

	public String getPet_gender() {
		return pet_gender;
	}

	public void setPet_gender(String pet_gender) {
		this.pet_gender = pet_gender;
	}

	public String getPet_nuetering() {
		return pet_nuetering;
	}

	public void setPet_nuetering(String pet_nuetering) {
		this.pet_nuetering = pet_nuetering;
	}

	public String getPet_weight() {
		return pet_weight;
	}

	public void setPet_weight(String pet_weight) {
		this.pet_weight = pet_weight;
	}

	public String getPet_filename() {
		return pet_filename;
	}

	public void setPet_filename(String pet_filename) {
		this.pet_filename = pet_filename;
	}

}
