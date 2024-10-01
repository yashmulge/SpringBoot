//package com.example.springboot.document;
//
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class CreateWordDocument {
//
//    public static void main(String[] args) {
//        // Create a new document
//        XWPFDocument document = new XWPFDocument();
//
//        // Create a new paragraph
//        XWPFParagraph paragraph = document.createParagraph();
//        XWPFRun run = paragraph.createRun();
//        run.setText("Hello, this is a sample Word document created using Apache POI.");
//
//        // Write the document to a file
//        String filePath = "sample.docx";
//        try (FileOutputStream out = new FileOutputStream(filePath)) {
//            document.write(out);
//            System.out.println("Word document created successfully at " + filePath);
//        } catch (IOException e) {
//            System.err.println("Error writing Word document: " + e.getMessage());
//        } finally {
//            // Close the document to free resources
//            try {
//                document.close();
//            } catch (IOException e) {
//                System.err.println("Error closing document: " + e.getMessage());
//            }
//        }
//    }
//}
