package br.com.camaroti.alex.tdd.auctionhouse.service;

import org.junit.Before;
import org.junit.Test;

import br.com.camaroti.alex.tdd.auctionhouse.model.Bid;
import br.com.camaroti.alex.tdd.auctionhouse.model.User;

public class BidTest {
	
	
	private User balthazar;

	@Before
	public void setUp() {
		balthazar = new User("Balthazar");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void bidNegativeValueOrNothing() {
		new Bid(balthazar, 0);
		new Bid(balthazar, -20);
	}

}
