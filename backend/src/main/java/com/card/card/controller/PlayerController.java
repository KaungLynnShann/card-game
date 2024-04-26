package com.card.card.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.card.card.dto.PlayerDto;
import com.card.card.entity.Player;
import com.card.card.service.PlayerService;
import com.card.card.service.StorageService;

@RestController
@RequestMapping("player")
public class PlayerController {
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	StorageService storageService;
	
	/****************************CREATE PLAYER********************************/
	
	@PostMapping(value = "/login")
	public ResponseEntity<Player> createPlayer(@Valid @RequestBody PlayerDto playerDto){
		return ResponseEntity.ok().body(playerService.createPlayer(playerDto));
	}
	
	/****************************UPDATE PLAYER********************************/
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Player> updatePlayer(@PathVariable int id,
			@Valid @RequestBody PlayerDto playerDto){
		Player updatePlayer = playerService.updatePlayer(id, playerDto);
		
		if(updatePlayer == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatePlayer);
	}
	

	@GetMapping(value = "/list")
	public ResponseEntity<List<PlayerDto>> getAllPlayer(){
		return ResponseEntity.ok().body(playerService.getAllPlayer());
	}
	
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<PlayerDto> getPlayer(@PathVariable int id){
		PlayerDto playerDto = playerService.getPlayer(id);
		
		if(playerDto == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(playerDto);
 	}

}
