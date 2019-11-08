import java.util.ArrayList;

public class Journal {
	private int journalName;
	private String iSSN;
	private String dateOfPublication;
	private ArrayList<Editor> boardOfEditors;
	private ArrayList<Volume> volumes;
	
	public Journal(int journalName, String iSSN, String dateOfPublication, ArrayList<Editor> boardOfEditors) {
		this.journalName = journalName;
		this.iSSN = iSSN;
		this.dateOfPublication = dateOfPublication;
		this.boardOfEditors = boardOfEditors;
	}

	public int getJournalName() {
		return this.journalName;
	}

	public void setJournalName(int journalName) {
		this.journalName = journalName;
	}

	public String getISSN() {
		return this.iSSN;
	}

	public void setISSN(String iSSN) {
		this.iSSN = iSSN;
	}

	public String getDateOfPublication() {
		return this.dateOfPublication;
	}

	public void setDateOfPublication(String dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}
	
	public ArrayList<Volume> getVolumes() {
		return this.volumes;
	}
	
	public void addVolume(Volume v) {
		this.volumes.add(v);
	}


	public ArrayList<Editor> getBoardOfEditors() {
		return this.boardOfEditors;
	}
	
	public void addEditorToBoard(Editor e) {
		this.boardOfEditors.add(e);
	}
	
}
