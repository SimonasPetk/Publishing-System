package com.publishingsystem.mainclasses;

import java.util.ArrayList;
import java.util.Calendar;

public class TestRun {

	public static void main(String[] args) {
		
		ChiefEditor e1 = new ChiefEditor("Ms", "J.", "Smith", "j.smith@uniofsmith.ac.uk", "Uni of smith", new Hash("smith"));
		Editor e2 = new Editor("Ms", "E.", "Jackson", "e.jackson@uniofjacks.ac.uk", "Uni of jacks", new Hash("jackson"));
		
		ArrayList<Editor> editors = new ArrayList<Editor>();
		editors.add(e1);
		editors.add(e2);
		
		Database.registerEditors(editors);
		
		Calendar calendar = Calendar.getInstance();
		Journal j = new Journal((int)(Math.random()*100000000), "Computer Science", new java.sql.Date(calendar.getTime().getTime()), editors);
		
		e1.createJournal(j);
		
		Database.addJournal(j);
		
		Author a1 = new Author("Mr", "J.", "Doe", "j.doe@uniofdoe.ac.uk", "Uni of doe", new Hash("doe"));
		Author a2 = new Author("Mr", "H.", "Humphry", "h.humphry@uniofhumphry.ac.uk", "Uni of humphry", new Hash("humphry"));
		
		ArrayList<Author> authors = new ArrayList<Author>();
		authors.add(a1);
		authors.add(a2);
		Database.registerAuthors(authors);
		
		Submission s = new Submission("How doe works", "Article about how doe works", j.getISSN(), authors, new PDF("TEST_PDF.pdf", new java.sql.Date(calendar.getTime().getTime())));
		a1.submit(s);
		Database.addSubmission(s);
		
		System.out.println(a1.getSubmissionStatus(s));
		
		Author a3 = new Author("Mr", "P.", "Prince", "p.prince@uniofprince.ac.uk", "Uni of prince", new Hash("prince"));
		Author a4 = new Author("Mr", "K.", "King", "k.king@uniofking.ac.uk", "Uni of king", new Hash("king"));
		Author a5 = new Author("Mr", "Q.", "Queen", "q.queen@uniofqueen.ac.uk", "Uni of queen", new Hash("humphry"));
		
		ArrayList<Author> authorsReviewers = new ArrayList<Author>();
		authorsReviewers.add(a3);
		authorsReviewers.add(a4);
		authorsReviewers.add(a5);
		
		Database.registerAuthors(authorsReviewers);
		
		Submission tempS = new Submission("Temp article", "Article about temp", j.getISSN(), authorsReviewers, new PDF("temp.pdf", new java.sql.Date(calendar.getTime().getTime())));
		
		a3.submit(tempS);
		
		Database.addSubmission(tempS);
		
		ArrayList<Reviewer> reviewers = new ArrayList<Reviewer>();
		reviewers.add(new Reviewer(a3));
		reviewers.add(new Reviewer(a4));
		reviewers.add(new Reviewer(a5));
		
		Database.addReviewers(reviewers);
		
		//Reviewer and reviews
		for(Reviewer r : reviewers) {
			r.addSubmissionToReview(s);
			Database.addSubmissionToReview(r, s);
			
			ArrayList<String> criticisms = new ArrayList<String>();
			criticisms.add("Perhaps explain origin of doe");
			
			Review review = new Review(r.getReviewerId(), s.getSubmissionId(), "Good", "None", criticisms);
			s.addReview(review);
			Database.addReview(review);
		}
		

		System.out.println(a1.getSubmissionStatus(s));
		
		//Verdicts
		for(Reviewer r : reviewers) {
			Verdict [] verdicts = Verdict.values();
			int rndIndex = (int)(Math.random()*verdicts.length);
			Verdict rndVerdict = verdicts[rndIndex];
			r.setVerdict(s, rndVerdict);
			Database.setVerdict(r, s);
		}
//		
		System.out.println(a1.getSubmissionStatus(s));
		
		//Responses
		for(Reviewer r : reviewers) {
			a1.submitRevisedVersion(s, new PDF("revisedS.pdf", new java.sql.Date(calendar.getTime().getTime())));
			ArrayList<String> answers = new ArrayList<String>();
			answers.add("Explained on page 22 in revised article");
			Response response = new Response(s.getSubmissionId(), r.getReviewerId(), answers);
			a1.respond(s, r , response);
			
			Database.addResponse(r, s);
		}
		
		System.out.println(a1.getSubmissionStatus(s));
		
		

//		e1.acceptArticle(s);
//		
//		System.out.println(a1.getSubmissionStatus(s));
//		
	}
}
