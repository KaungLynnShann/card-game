package com.card.card.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.card.card.entity.Card;
import com.card.card.service.CardService;
import com.card.card.service.StorageService;

@RestController
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	StorageService storageService;
	
	
	@Autowired
	CardService cardService;
	
	/********************** GET CARD******************************/
	
	@GetMapping(value = "/card/list")
	public ResponseEntity<List<Card>> getAllCard(){
		return ResponseEntity.ok().body(cardService.getAllCard());
	}
	
	/*
	 * ----------------------------------- START BY FILE
	 * -----------------------------------
	 */
	/***** CreateFile ******************************/
	@PostMapping("/file/create")
	public ResponseEntity<String> createFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType) {
		String filePath = storageService.create(file, fileType);
		if (filePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(filePath);
	}

	/***** UpdateFile ******************************/
	@PutMapping("/file/update")
	public ResponseEntity<String> updateFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType, @RequestParam("filePath") String filePath) {
		String newFilePath = storageService.update(file, fileType, filePath);
		if (newFilePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(newFilePath);
	}
	/*
	 * ----------------------------------- END BY FILE
	 * -----------------------------------
	 */

}
