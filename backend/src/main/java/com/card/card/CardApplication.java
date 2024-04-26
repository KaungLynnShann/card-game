package com.card.card;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.card.card.entity.Card;
import com.card.card.repository.CardRepository;

@SpringBootApplication
public class CardApplication implements CommandLineRunner {

    @Autowired
    CardRepository cardRepository;

    public static void main(String[] args) {
        SpringApplication.run(CardApplication.class, args);
    }

    public void run(String... args) throws Exception {
        List<String> colors = new ArrayList<>();
        colors.add("Red");
        colors.add("Blue");
        colors.add("orange");
        colors.add("violet");
        colors.add("yellow");
        colors.add("purple");
        colors.add("green");
        colors.add("black");

        List<Card> deck = new ArrayList<>();
        for (String color : colors) {
            for (int number = 1; number <= 10; number++) {
                Card card = new Card(color, number);
                deck.add(card);
            }
        }

        // Save all cards in the repository
        cardRepository.saveAll(deck);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
