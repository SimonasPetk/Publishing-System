import java.util.ArrayList;

public class Review {
	private int reviewerId;
	private int submissionId;
	private String summary;
	private String typingErrors;
	private ArrayList<String> criticisms;
	
	public Review(int reviewerId, int submissionId, String summary, String typingErrors,ArrayList<String> criticisms) {
		this.reviewerId = reviewerId;
		this.submissionId = submissionId;
		this.summary = summary;
		this.typingErrors = typingErrors;
		this.criticisms = criticisms;
	}

	public int getReviewerId() {
		return reviewerId;
	}

	public int getSubmissionId() {
		return submissionId;
	}

	public String getSummary() {
		return summary;
	}

	public String getTypingErrors() {
		return typingErrors;
	}

	public ArrayList<String> getCriticisms() {
		return criticisms;
	}
	
}
