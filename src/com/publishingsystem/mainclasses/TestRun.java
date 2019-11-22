package com.publishingsystem.mainclasses;

import java.util.ArrayList;
import java.util.Calendar;

public class TestRun {

	public static void main(String[] args) {
		
		Editor e1 = new Editor("Ms", "J.", "Smith", "j.smith@uniofsmith.ac.uk", "Uni of smith", new Hash("smith"));
		Editor e2 = new Editor("Ms", "E.", "Jackson", "e.jackson@uniofjacks.ac.uk", "Uni of jacks", new Hash("jackson"));
		
		Calendar calendar = Calendar.getInstance();
		Journal j = new Journal((int)(Math.random()*100000000), "Computer Science", new java.sql.Date(calendar.getTime().getTime()));
		Journal j1 = new Journal((int)(Math.random()*100000000), "Software Engineering", new java.sql.Date(calendar.getTime().getTime()));
		
		e1.createJournal(j);
		e1.createJournal(j1);
		ArrayList<Editor> editors = new ArrayList<Editor>();
		editors.add(e2);
		
		e1.registerEditors(j, editors);
		editors.add(e1);
		
		//Adding to DB
		Database.registerEditors(editors);
		Database.addJournal(j);
		Database.addJournal(j1);

		Author a1 = new Author("Mr", "J.", "Doe", "j.doe@uniofdoe.ac.uk", "Uni of doe", new Hash("doe"));
		Author a2 = new Author("Mr", "H.", "Humphry", "h.humphry@uniofhumphry.ac.uk", "Uni of humphry", new Hash("humphry"));
		
		ArrayList<Author> authors = new ArrayList<Author>();
		authors.add(a2);
	
		Article article = new Article("How doe works", "Article about how doe works", j, new PDF("TEST_PDF.pdf", new java.sql.Date(calendar.getTime().getTime())));
		a1.registerCoAuthors(article, authors);
		
		a1.submit(article);
		authors.add(a1);
		
		//Adding to DB
		Database.registerAuthors(authors);
		Database.addSubmission(article);
//		
//		System.out.println(a1.getSubmissionStatus(s));
//		
//		Author a3 = new Author("Mr", "P.", "Prince", "p.prince@uniofprince.ac.uk", "Uni of prince", new Hash("prince"));
		Author a4 = new Author("Mr", "K.", "King", "k.king@uniofking.ac.uk", "Uni of king", new Hash("king"));
		Author a5 = new Author("Mr", "Q.", "Queen", "q.queen@uniofqueen.ac.uk", "Uni of queen", new Hash("humphry"));
		
		ArrayList<Author> authorsReviewers = new ArrayList<Author>();
		authorsReviewers.add(a1);
		authorsReviewers.add(a5);
//		
		Article tempS = new Article("Temp article", "Article about temp", j,  new PDF("temp.pdf", new java.sql.Date(calendar.getTime().getTime())));
		a4.registerCoAuthors(tempS, authorsReviewers);
		authorsReviewers.add(a4);
		a4.submit(tempS);
		
		Database.registerAuthors(authorsReviewers);
		Database.addSubmission(tempS);
//		
		ArrayList<Reviewer> reviewers = new ArrayList<Reviewer>();
		reviewers.add(new Reviewer(a1));
		reviewers.add(new Reviewer(a4));
		reviewers.add(new Reviewer(a5));
		
		Database.addReviewers(reviewers);
//		
		//Reviewer and reviews
		for(Reviewer r : reviewers) {
			
			ArrayList<Criticism> criticisms = new ArrayList<Criticism>();
			criticisms.add(new Criticism("Perhaps explain origin of doe"));
			
			Review review = new Review(article.getSubmission(), "Good", "None", criticisms);
			r.addReview(article.getSubmission(), review);
			Database.addReview(r, review);
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
			Database.setVerdict(r, review);
		}
////		
//		System.out.println(a1.getSubmissionStatus(s));
//		
		
		//Revised article
		PDF revisedPDF = new PDF("revisedS.pdf", new java.sql.Date(calendar.getTime().getTime()));
		a1.submitRevisedVersion(article, revisedPDF);
		Database.addRevisedSubmission(revisedPDF);
		
		//Responses
		for(Review r : article.getSubmission().getReviews()) {
			ArrayList<String> answers = new ArrayList<String>();
			answers.add("Explained on page 22 in revised article");
			a1.respond(r, answers);
			
			Database.addResponse(r);
		}
		
//		System.out.println(a1.getSubmissionStatus(s));
//		
//		
//
//		e1.acceptArticle(s);
//		Database.acceptArticle(s);
//		
//		System.out.println(a1.getSubmissionStatus(s));
		
	}
}
