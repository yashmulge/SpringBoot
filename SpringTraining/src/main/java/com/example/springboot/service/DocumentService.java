package com.example.springboot.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

	// Read Word Document from byte array
	public String readWordDocument(byte[] fileBytes) throws IOException {
		try (InputStream is = new ByteArrayInputStream(fileBytes); XWPFDocument document = new XWPFDocument(is)) {
			StringBuilder content = new StringBuilder();
			for (XWPFParagraph paragraph : document.getParagraphs()) {
				content.append(paragraph.getText()).append("\n");
			}
			return content.toString();
		}
	}

	// Read PDF Document from byte array
	public String readPdfDocument(byte[] fileBytes) throws IOException {
		try (InputStream is = new ByteArrayInputStream(fileBytes); PDDocument document = PDDocument.load(is)) {
			PDFTextStripper pdfStripper = new PDFTextStripper();
			return pdfStripper.getText(document);
		}
	}

}
