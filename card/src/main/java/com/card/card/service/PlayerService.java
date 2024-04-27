package com.card.card.service;

import java.util.List;

import com.card.card.dto.PlayerDto;
import com.card.card.entity.Player;

public interface PlayerService {
	
	public Player createPlayer(PlayerDto playerDto);
	
	public List<PlayerDto> getAllPlayer();
	
	public  PlayerDto getPlayer(int id);
	
	public  Player updatePlayer(int id,PlayerDto playerDto);
	
	Boolean deletePlayer(int id);

}
