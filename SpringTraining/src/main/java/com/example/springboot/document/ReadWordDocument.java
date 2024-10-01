package com.example.springboot.document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class ReadWordDocument {

	public static void main(String[] args) {
		String filePath = "/Users/yashraj/Downloads/hello.docx";

		try (FileInputStream fis = new FileInputStream(filePath); XWPFDocument document = new XWPFDocument(fis)) {

			List<XWPFParagraph> paragraphs = document.getParagraphs();
			for (XWPFParagraph paragraph : paragraphs) {
				System.out.println(paragraph.getText());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
