package com.publishingsystem.mainclasses;

import java.util.ArrayList;

public class TestRun {

	public static void main(String[] args) {
		//Author
		Author a = new Author(1, "Mr", "J.", "Doe", "j.doe@uniofdoe.ac.uk", "Uni of doe");
		ArrayList<Author> authors = new ArrayList<Author>();
		authors.add(a);
		Submission s = new Submission(authors, "How doe works", "Article about how doe works", 1, 1, a.getAcademicId(), 1);
		a.submitArticle(s);
		System.out.println(a.getSubmissionStatus(s));
		
		//Reviewer and reviews
		Reviewer [] rvs = new Reviewer[3];
		for(int i = 0; i < rvs.length; i++) {
			rvs[i] = new Reviewer(1, "Ms", "J.", "Smith", "j.smith@uniofsmith.ac.uk", "Uni of smith", i);
			Reviewer r = rvs[i];
			r.addSubmissionToReview(s);
	
			ArrayList<String> criticisms = new ArrayList<String>();
			criticisms.add("Perhaps explain origin of doe");
			
			Review review = new Review(r.getReviewerId(), s.getSubmissionId(), "Good", "None", criticisms);
			s.addReview(review);
		}

		System.out.println(a.getSubmissionStatus(s));
		
		//Verdicts
		for(Reviewer r : rvs) {
			Verdict [] verdicts = Verdict.values();
			int rndIndex = (int)(Math.random()*verdicts.length);
			Verdict rndVerdict = verdicts[rndIndex];
			r.setVerdict(s, rndVerdict);
		}
		
		System.out.println(a.getSubmissionStatus(s));
		
		//Responses
		for(Reviewer r : rvs) {
			ArrayList<String> answers = new ArrayList<String>();
			answers.add("Explained on page 22 in revised article");
			Response response = new Response(s.getSubmissionId(), r.getReviewerId(), answers);
			a.respond(s, r.getReviewerId() , response);
		}
		
		System.out.println(a.getSubmissionStatus(s));
		
		
		//Final decision
		Editor e = new Editor(1, "Ms", "J.", "Smith", "j.smith@uniofsmith.ac.uk", "Uni of smith", 10);
		e.acceptArticle(s);
		
		System.out.println(a.getSubmissionStatus(s));
		
	}
}
