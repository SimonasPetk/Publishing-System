import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class TestUploadingPDF {
	private int PDFID;
	
	public void readPDF() {
		try(Scanner file = new Scanner(new File("src/H21005.pdf"))) {
			while (file.hasNext()) {
				String line = file.nextLine();
				System.out.println(line);
			}
		}catch (IOException e) {
			System.out.println(e);
		}
	}
}