package com.card.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.card.card.entity.Card;

@Repository
@EnableJpaRepositories
public interface CardRepository extends JpaRepository<Card, Long> {

}
