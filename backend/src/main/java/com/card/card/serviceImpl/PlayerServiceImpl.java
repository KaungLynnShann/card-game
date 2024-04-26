package com.card.card.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.card.card.dto.PlayerDto;
import com.card.card.entity.Player;
import com.card.card.repository.PlayerRepository;
import com.card.card.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	PlayerRepository playerRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public Player createPlayer(PlayerDto playerDto) {
		return playerRepository.save(mapTOEntity(playerDto));
	}

	@Override
	public List<PlayerDto> getAllPlayer() {
		List<Player> playerList = playerRepository.findAll();
		List<PlayerDto> playerDtoList = playerList.stream().map
				(player ->mapToDto(player)).collect(Collectors.toList());
		return playerDtoList;
	}

	@Override
	public PlayerDto getPlayer(int id) {
		Player player = playerRepository.findById(id).orElse(null);
		return mapToDto(player);
	}

	@Override
	public Player updatePlayer(int id, PlayerDto playerDto) {
		PlayerDto updatePlayer = this.getPlayer(id);
		
		if(updatePlayer == null) {
			return null;
		}
		
		updatePlayer.setPlayerName(playerDto.getPlayerName());
		
		return playerRepository.save(mapTOEntity(playerDto));
	}

	@Override
	public Boolean deletePlayer(int id) {
		Player player = playerRepository.findById(id).orElse(null);
		
		if(player == null) {
			return false;
		}
		playerRepository.delete(player);
		return true;
	}
	
	/*********CONVERT ENTITY TO DTO ***********/
	
	public PlayerDto mapToDto(Player player) {
		return modelMapper.map(player, PlayerDto.class);
	}
	
	/*********CONVERT  DTO TO ENTITY***********/
	
	public Player mapTOEntity(PlayerDto playerDto) {
		return modelMapper.map(playerDto, Player.class);
	}

}
