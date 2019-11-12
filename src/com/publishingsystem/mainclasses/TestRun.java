package com.publishingsystem.mainclasses;

import java.util.ArrayList;
import java.util.Calendar;

public class TestRun {

	public static void main(String[] args) {
		
		Editor e1 = new Editor(-1, "Ms", "J.", "Smith", "j.smith@uniofsmith.ac.uk", "Uni of smith", new Hash("smith"));
		Editor e2 = new Editor(-1, "Ms", "E.", "Jackson", "e.jackson@uniofjacks.ac.uk", "Uni of jacks", new Hash("jackson"));
		
		ArrayList<Editor> editors = new ArrayList<Editor>();
		editors.add(e1);
		editors.add(e2);
		Database.addEditors(editors);
		
		Calendar calendar = Calendar.getInstance();
		Journal j = new Journal((int)(Math.random()*100000000), "Computer Science", new java.sql.Date(calendar.getTime().getTime()));
		
		e1.createJournal(j);
		
		Database.addJournal(j, editors);
		
		Author a1 = new Author(-1, "Mr", "J.", "Doe", "j.doe@uniofdoe.ac.uk", "Uni of doe", new Hash("doe"));
		Author a2 = new Author(-1, "Mr", "H.", "Humphry", "h.humphry@uniofhumphry.ac.uk", "Uni of humphry", new Hash("humphry"));
		
		ArrayList<Author> authors = new ArrayList<Author>();
		authors.add(a1);
		authors.add(a2);
		Database.addAuthors(authors);
		
		Submission s = new Submission(-1, authors, "How doe works", "Article about how doe works", "TEST_PDF.pdf", 1);
		a1.submit(s);
		Database.addSubmission(s, authors);
		
		System.out.println(a1.getSubmissionStatus(s));
		
		//Reviewer and reviews
		Reviewer [] rvs = new Reviewer[3];
		for(int i = 0; i < rvs.length; i++) {
			rvs[i] = new Reviewer(1, "Ms", "J.", "Smith", "j.smith@uniofsmith.ac.uk", "Uni of smith", i, new Hash("rvr"));
			Reviewer r = rvs[i];
			r.addSubmissionToReview(s);
	
			ArrayList<String> criticisms = new ArrayList<String>();
			criticisms.add("Perhaps explain origin of doe");
			
			Review review = new Review(r.getReviewerId(), s.getSubmissionId(), "Good", "None", criticisms);
			s.addReview(review);
		}

		System.out.println(a1.getSubmissionStatus(s));
		
		//Verdicts
		for(Reviewer r : rvs) {
			Verdict [] verdicts = Verdict.values();
			int rndIndex = (int)(Math.random()*verdicts.length);
			Verdict rndVerdict = verdicts[rndIndex];
			r.setVerdict(s, rndVerdict);
		}
		
		System.out.println(a1.getSubmissionStatus(s));
		
		//Responses
		for(Reviewer r : rvs) {
			ArrayList<String> answers = new ArrayList<String>();
			answers.add("Explained on page 22 in revised article");
			Response response = new Response(s.getSubmissionId(), r.getReviewerId(), answers);
			a1.respond(s, r.getReviewerId() , response);
		}
		
		System.out.println(a1.getSubmissionStatus(s));
		
		

		e1.acceptArticle(s);
		
		System.out.println(a1.getSubmissionStatus(s));
		
	}
}
