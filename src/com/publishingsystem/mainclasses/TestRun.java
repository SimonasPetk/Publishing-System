package com.publishingsystem.mainclasses;

import java.util.ArrayList;
import java.util.Calendar;

public class TestRun {

	public static void main(String[] args) {
		
		Editor e1 = new Editor(-1, "Ms", "J.", "Smith", "j.smith@uniofsmith.ac.uk", "Uni of smith", new Hash("smith"));
		Editor e2 = new Editor(-1, "Ms", "E.", "Jackson", "e.jackson@uniofjacks.ac.uk", "Uni of jacks", new Hash("jackson"));
		
		Calendar calendar = Calendar.getInstance();
		Journal j = new Journal((int)(Math.random()*100000000), "Computer Science", new java.sql.Date(calendar.getTime().getTime()));
		Journal j1 = new Journal((int)(Math.random()*100000000), "Software Engineering", new java.sql.Date(calendar.getTime().getTime()));
		
		e1.createJournal(j);
		e1.createJournal(j1);
		ArrayList<Editor> editors = new ArrayList<Editor>();
		editors.add(e2);
		editors.add(e1);
		e1.registerEditors(j, editors);
		
		//Adding to DB
		Database.registerEditors(editors);
		Database.addJournal(j);
		Database.addJournal(j1);
		
		Author a1 = new Author(-1, "Mr", "J.", "Doe", "j.doe@uniofdoe.ac.uk", "Uni of doe", new Hash("doe"));
		Author a2 = new Author(-1, "Mr", "H.", "Humphry", "h.humphry@uniofhumphry.ac.uk", "Uni of humphry", new Hash("humphry"));
//		
		ArrayList<Author> authors = new ArrayList<Author>();
		authors.add(a2);
		authors.add(a1);
		Article article = new Article(-1, "How doe works", "Article about how doe works", j);
		ArrayList<Integer> numReview1 = new ArrayList<Integer>();
		numReview1.add(1);
		numReview1.add(2);
		a1.registerCoAuthors(article, authors, numReview1);
//		
		a1.submit(article, new PDF(-1, new java.sql.Date(calendar.getTime().getTime()), null), 1);
		System.out.println(a1.getAuthorOfArticles().size());
//		
//		//Adding to DB
		Database.registerAuthors(authors);
		Database.addSubmission(article, new byte[] {});
////		

		
//		Author a3 = new Author(-1, "Mr", "P.", "Prince", "p.prince@uniofprince.ac.uk", "Uni of prince", new Hash("prince"));
		Author a4 = new Author(-1, "Mr", "K.", "King", "k.king@uniofking.ac.uk", "Uni of king", new Hash("king"));
		Author a5 = new Author(-1, "Mr", "Q.", "Queen", "q.queen@uniofqueen.ac.uk", "Uni of queen", new Hash("humphry"));
		
		ArrayList<Author> authorsReviewers = new ArrayList<Author>();
		authorsReviewers.add(a1);
		authorsReviewers.add(a5);
		authorsReviewers.add(a4);
//		
		Article tempS = new Article(-1, "Temp article", "Article about temp", j);
	
		ArrayList<Integer> numReview = new ArrayList<Integer>();
		numReview.add(1);
		numReview.add(2);
		numReview.add(0);
		a4.registerCoAuthors(tempS, authorsReviewers, numReview);
		a4.submit(tempS, new PDF(-1, new java.sql.Date(calendar.getTime().getTime()), null), 1);
		
		Database.registerAuthors(authorsReviewers);
		Database.addSubmission(tempS, new byte[] {});

		ArrayList<Reviewer> reviewers = new ArrayList<Reviewer>();
		reviewers.add(new Reviewer(a1));
		reviewers.add(new Reviewer(a4));
		reviewers.add(new Reviewer(a5));
		
		Database.registerReviewers(reviewers);
		//Reviewer and reviews
		for(Reviewer r : reviewers) {
			ArrayList<Submission> submissions = new ArrayList<Submission>();
			submissions.add(article.getSubmission());
			r.addSubmissionsToReview(submissions);
			Database.selectSubmissionsToReview(r, submissions);
			ArrayList<Criticism> criticisms = new ArrayList<Criticism>();
			criticisms.add(new Criticism("Perhaps explain origin of doe"));
			
			ReviewerOfSubmission ros = new ReviewerOfSubmission(r, article.getSubmission());
			Review review = new Review(ros, "Good", "None", criticisms);
			ros.addReview(review);
			Database.addReview(review);

		}
////		
////
////		System.out.println(a1.getSubmissionStatus(s));
////		
//		//Verdicts
//		for(Reviewer reviewer : reviewers) {
//			Verdict [] verdicts = Verdict.values();
//			int rndIndex = (int)(Math.random()*verdicts.length);
//			Verdict rndVerdict = verdicts[rndIndex];
//			Review review = reviewer.getReviews().get(0);
//			reviewer.setVerdict(review, rndVerdict);
//			Database.setVerdict(review);
//		}
//////		
////		System.out.println(a1.getSubmissionStatus(s));
////		
//		
//		//Revised article
//		PDF revisedPDF = new PDF(-1, new java.sql.Date(calendar.getTime().getTime()), tempS.getSubmission());
//		a1.submitRevisedVersion(revisedPDF);
////		Database.addRevisedSubmission(revisedPDF);
//		System.out.println(revisedPDF.getPdfId());
//		//Responses
//		for(Review rev : article.getSubmission().getReviews()) {
//			ArrayList<String> answers = new ArrayList<String>();
//			answers.add("Explained on page 22 in revised article");
//			a1.respond(rev, answers);
//			
//			Database.addResponse(rev);
//		}
//		
////		System.out.println(a1.getSubmissionStatus(s));
////		
////		
////
////		e1.acceptArticle(s);
////		Database.acceptArticle(s);
////		
////		System.out.println(a1.getSubmissionStatus(s));
		
	}
}
