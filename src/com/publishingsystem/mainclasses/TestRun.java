package com.publishingsystem.mainclasses;

import java.util.ArrayList;
import java.util.Calendar;

public class TestRun {

	public static void main(String[] args) {
		
		Editor e1 = new Editor("Ms", "J.", "Smith", "j.smith@uniofsmith.ac.uk", "Uni of smith", new Hash("smith"));
		Editor e2 = new Editor("Ms", "E.", "Jackson", "e.jackson@uniofjacks.ac.uk", "Uni of jacks", new Hash("jackson"));
		
		Calendar calendar = Calendar.getInstance();
		Journal j = new Journal((int)(Math.random()*100000000), "Computer Science", new java.sql.Date(calendar.getTime().getTime()));
		
		e1.createJournal(j);
		
		ArrayList<Editor> editors = new ArrayList<Editor>();
		editors.add(e2);
		
		e1.registerEditors(j, editors);
		editors.add(e1);
		
		//Adding to DB
		Database.registerEditors(editors);
		Database.addJournal(j);

		Author a1 = new Author("Mr", "J.", "Doe", "j.doe@uniofdoe.ac.uk", "Uni of doe", new Hash("doe"));
		Author a2 = new Author("Mr", "H.", "Humphry", "h.humphry@uniofhumphry.ac.uk", "Uni of humphry", new Hash("humphry"));
		
		ArrayList<Author> authors = new ArrayList<Author>();
		authors.add(a2);
	
		Submission s = new Submission("How doe works", "Article about how doe works", j.getJournalName(), new PDF("TEST_PDF.pdf", new java.sql.Date(calendar.getTime().getTime())));
		a1.registerCoAuthors(s, authors);
		
		a1.submit(s);
		authors.add(a1);
		
		//Adding to DB
		Database.registerAuthors(authors);
		Database.addSubmission(s);
//		
//		System.out.println(a1.getSubmissionStatus(s));
//		
		Author a3 = new Author("Mr", "P.", "Prince", "p.prince@uniofprince.ac.uk", "Uni of prince", new Hash("prince"));
		Author a4 = new Author("Mr", "K.", "King", "k.king@uniofking.ac.uk", "Uni of king", new Hash("king"));
		Author a5 = new Author("Mr", "Q.", "Queen", "q.queen@uniofqueen.ac.uk", "Uni of queen", new Hash("humphry"));
		
		ArrayList<Author> authorsReviewers = new ArrayList<Author>();
		authorsReviewers.add(a4);
		authorsReviewers.add(a5);
//		
		Submission tempS = new Submission("Temp article", "Article about temp", j.getJournalName(),  new PDF("temp.pdf", new java.sql.Date(calendar.getTime().getTime())));
		a3.registerCoAuthors(tempS, authorsReviewers);
		
		a3.submit(tempS);
		authorsReviewers.add(a3);
		
		Database.registerAuthors(authorsReviewers);
		Database.addSubmission(tempS);
//		
		ArrayList<Reviewer> reviewers = new ArrayList<Reviewer>();
		reviewers.add(new Reviewer(a3));
		reviewers.add(new Reviewer(a4));
		reviewers.add(new Reviewer(a5));
		
		Database.addReviewers(reviewers);
//		
		//Reviewer and reviews
		for(Reviewer r : reviewers) {
			
			ArrayList<String> criticisms = new ArrayList<String>();
			criticisms.add("Perhaps explain origin of doe");
			
			Review review = new Review(r, s, "Good", "None", criticisms);
			r.addReview(s, review);
			Database.addReview(review);
		}
//		
//
//		System.out.println(a1.getSubmissionStatus(s));
//		
		//Verdicts
		for(Reviewer r : reviewers) {
			Verdict [] verdicts = Verdict.values();
			int rndIndex = (int)(Math.random()*verdicts.length);
			Verdict rndVerdict = verdicts[rndIndex];
			Review review = r.getReviews().get(0);
			r.setVerdict(review, rndVerdict);
			Database.setVerdict(review);
		}
////		
//		System.out.println(a1.getSubmissionStatus(s));
//		
		
		//Revised article
		PDF revisedPDF = new PDF("revisedS.pdf", new java.sql.Date(calendar.getTime().getTime()));
		a1.submitRevisedVersion(s, revisedPDF);
		Database.addRevisedSubmission(revisedPDF);
		
		//Responses
		for(Reviewer r : reviewers) {
			ArrayList<String> answers = new ArrayList<String>();
			answers.add("Explained on page 22 in revised article");
			Review review = s.getReview(r.getReviewerId());
			a1.respond(review, answers);
			
			Database.addResponse(review);
		}
		
//		System.out.println(a1.getSubmissionStatus(s));
//		
//		
//
//		e1.acceptArticle(s);
//		
//		System.out.println(a1.getSubmissionStatus(s));
		
	}
}
