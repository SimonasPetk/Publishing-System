import java.util.ArrayList;

public class Submission extends Article{
	private ArrayList<Review> reviews;
	private ArrayList<Response> responses;
	private int submissionId;
	
	public Submission(ArrayList<Author> authors, String title, String summary, int articleId, int submissionId) {
		super(authors, title, summary, articleId);
		this.submissionId = submissionId;
	}
	
	public ArrayList<Review> getReviews() {
		return this.reviews;
	}
	
	public ArrayList<Response> getResponses() {
		return this.responses;
	}

	public int getSubmissionId() {
		return this.submissionId;
	}

	public void addReview(Review r) {
		this.reviews.add(r);
	}
	
	public void addResponse(Response r) {
		this.responses.add(r);
	}
}
