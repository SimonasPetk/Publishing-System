
public class Academic {
	protected int academicId;
	protected String forename;
	protected String surname;
	protected String emailId;
	protected String university;
	
	public Academic(int academicId, String forename, String surname, String emailId, String university) {
		this.academicId = academicId;
		this.forename = forename;
		this.surname = surname;
		this.emailId = emailId;
		this.university = university;
	}
	
	public void register() {}

	public int getAcademicId() {
		return academicId;
	}

	public String getForename() {
		return forename;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getUniversity() {
		return university;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setUniversity(String university) {
		this.university = university;
	}
	
}
