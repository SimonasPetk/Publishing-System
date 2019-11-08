import java.util.ArrayList;

public class MainAuthor extends Author{
	private ArrayList<Article> submittedArticles;
	
	public MainAuthor(ArrayList<Article> articles, int academicId, String forename, String surname, String emailId, String university) {
		super(articles, academicId, forename,surname, emailId, university);
	}
	
	public void submitArticle(Article article) {
		this.submittedArticles.add(article);
	}
	
	public void respond() {}
	
	public void registerCoAuthors(Article article, ArrayList<Author> authors) {
		
	}
	
	
}
