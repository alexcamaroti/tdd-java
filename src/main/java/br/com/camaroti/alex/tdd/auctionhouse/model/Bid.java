package br.com.camaroti.alex.tdd.auctionhouse.model;

import lombok.Data;


public @Data class Bid {
	
	private User user;
	private double value;
	
	public Bid(User user, double value) {
		if(this.value <= 0) {
			throw new IllegalArgumentException("You can't offer nothing or a negative value.");
		}
		this.user = user;
		this.value = value;
	}
	
	
}
