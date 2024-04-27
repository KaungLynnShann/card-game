package com.card.card.dto;

import com.card.card.entity.BaseEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerDto extends BaseEntity {
	
	private int playerId;
	
	private String playerName;
	
}
