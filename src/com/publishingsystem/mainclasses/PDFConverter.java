package com.publishingsystem.mainclasses;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class PDFConverter {
	public static byte[] getByteArrayFromFile(String dir){
		try {
			File file = new File(dir);
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final InputStream in = new FileInputStream(file);
			final byte[] buffer = new byte[500];
			int read = -1;
			while ((read = in.read(buffer)) > 0) {
				baos.write(buffer, 0, read);
			}
			in.close();
			return baos.toByteArray();
		}catch(IOException io) {
			//			io.printStackTrace();
		}
		return null;
	}
	
	public static ByteArrayInputStream getPDFBlob(byte[] bytes) {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return bais;
	}

	public static void main(String[] args) {
//		byte[] pdf = PDFConverter.getByteArrayFromFile("pathToPDFfile.pdf");
//		System.out.println(pdf.length);
//		try {
//			OutputStream out = new FileOutputStream("pathToOutputLocation.pdf");
//			out.write(RetrieveDatabase.getPDF(1)); //PDF ID
//			out.close();
//		}catch(FileNotFoundException fnf) {
//			
//		}catch(IOException io) {
//			
//		}
	}
}
