import java.util.ArrayList;

public class Response {
	private int reviewerId;
	private int submissionId;
	private ArrayList<String> answers;
	
	public Response(int reviewerId, int submissionId, ArrayList<String> answers) {
		this.reviewerId = reviewerId;
		this.submissionId = submissionId;
		this.answers = answers;
	}

	public int getReviewerId() {
		return reviewerId;
	}

	public int getSubmissionId() {
		return submissionId;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}
	
	public void addAnswer(String answer) {
		this.answers.add(answer);
	}
	
}
