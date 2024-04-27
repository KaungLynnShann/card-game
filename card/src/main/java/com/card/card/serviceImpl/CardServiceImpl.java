package com.card.card.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.card.card.entity.Card;
import com.card.card.repository.CardRepository;
import com.card.card.service.CardService;

@Service
public class CardServiceImpl implements CardService {
	
	@Autowired
	CardRepository cardRepository;

	@Override
	public List<Card> getAllCard() {
		return cardRepository.findAll();
	}

	

}
