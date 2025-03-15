package com.moshood.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ReceiptService {
	
	private final String result = "C:\\Users\\sOPHIAT\\Documents\\itext\\pdf"
			+ "\\receipt.pdf";
	public void generateReceipt(OutputStream stream,String receiverName , String senderName) throws FileNotFoundException, DocumentException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, stream);
		document.open();
		document.add(new Paragraph("Transfer Successful"));
		document.add(new Paragraph("Receiver's name             : " + receiverName));
		document.add(new Paragraph("Sender's name             : " + senderName));
		document.close();
	}

}
