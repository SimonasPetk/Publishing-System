package com.publishingsystem.mainclasses;
import java.util.ArrayList;

public class Reviewer {
	private int reviewerId;
	private ArrayList<Article> articlesInReview;
	
	public Reviewer(int reviewerId) {
		this.reviewerId = reviewerId;
	}
	
	public void addArticleToReview(Article r) {
		this.articlesInReview.add(r);
	}
	
	public int getReviewerId() {
		return this.reviewerId;
	}
}
