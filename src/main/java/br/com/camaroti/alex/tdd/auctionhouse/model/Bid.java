package br.com.camaroti.alex.tdd.auctionhouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public @Data class Bid {
	
	private User user;
	@NonNull private Double value;
}
