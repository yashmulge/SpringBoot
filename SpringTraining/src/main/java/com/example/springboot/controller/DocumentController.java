package com.example.springboot.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@PostMapping("/read/word")
	public ResponseEntity<String> readWordDocument(@RequestParam("file") MultipartFile file) {
		try {
			byte[] fileBytes = file.getBytes();
			String content = documentService.readWordDocument(fileBytes);
			return new ResponseEntity<>(content, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>("Error reading Word document", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/read/pdf")
	public ResponseEntity<String> readPdfDocument(@RequestParam("file") MultipartFile file) {
		try {
			byte[] fileBytes = file.getBytes();
			String content = documentService.readPdfDocument(fileBytes);
			return new ResponseEntity<>(content, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>("Error reading PDF document", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
