package br.com.camaroti.alex.tdd.auctionhouse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import br.com.camaroti.alex.tdd.auctionhouse.model.Bid;
import br.com.camaroti.alex.tdd.auctionhouse.model.User;
import br.com.camaroti.alex.tdd.auctionhouse.service.AuctionHouse;

public class AuctionHouseTest {

	@Test
	public void mustReceiveOneBid() {
		AuctionHouse ah = new AuctionHouse("One Piece Series");
		ah.offer(new Bid(new User("Alex"), 400.0));
		
		assertEquals(1, ah.getBids().size());
		assertEquals(400.0, ah.getBids().get(0).getValue(), 0.00001);
	}
	
	@Test
	public void mustReceiveManyBids() {		
		AuctionHouse ah = new AuctionHouse("One Piece Series");
		ah.offer(new Bid(new User("Alex"), 400.0));
		ah.offer(new Bid(new User("Bartolomeu"), 600.0));
		
		assertEquals(2, ah.getBids().size());
		assertEquals(400.0, ah.getBids().get(0).getValue(), 0.00001);
		assertEquals(600.0, ah.getBids().get(1).getValue(), 0.00001);
		
	}
	
	@Test
	public void shouldNotAcceptMoreThanTwoBidsSameUser()
	{
		AuctionHouse ah = new AuctionHouse("One Piece Series");
		User alex = new User("Alex");		
		ah.offer(new Bid(alex, 400.0));
		ah.offer(new Bid(alex, 600.0));
		
		assertEquals(1, ah.getBids().size());
		assertEquals(400.0, ah.getBids().get(0).getValue(), 0.00001);
		
	}
	
	@Test
	public void shouldNotAcceptMoreThanFiveBidsSameUser() {
		AuctionHouse ah = new AuctionHouse("One Piece Series");
		User balthazar = new User("Balthazar");		
		User belquior = new User("Belquior");				
		ah.offer(new Bid(balthazar, 400.0));
		ah.offer(new Bid(belquior, 600.0));
		
		ah.offer(new Bid(balthazar, 700.0));
		ah.offer(new Bid(belquior, 800.0));
		
		ah.offer(new Bid(balthazar, 850.0));
		ah.offer(new Bid(belquior, 900.0));
		
		ah.offer(new Bid(balthazar, 950.0));
		ah.offer(new Bid(belquior, 1000.0));
		
		ah.offer(new Bid(balthazar, 1100.0));
		ah.offer(new Bid(belquior, 1200.0));
		
		ah.offer(new Bid(balthazar, 1300.0));
		
		
		assertEquals(10, ah.getBids().size());
		assertEquals(1200.0, ah.getBids().get(ah.getBids().size() - 1).getValue(), 0.00001);
	}
	
	@Test
	public void verifyFoldBid() {
		AuctionHouse ah = new AuctionHouse("One Piece Series");
		User balthazar = new User("Balthazar");		
		User belquior = new User("Belquior");
		ah.offer(new Bid(balthazar, 400.0));
		ah.offer(new Bid(belquior, 600.0));
		ah.foldBid(balthazar);
		
		assertEquals(3, ah.getBids().size());
		assertEquals(1200.0, ah.getBids().get(ah.getBids().size() - 1).getValue(), 0.00001);
	}
	
	@Test
	public void verifyFoldBidSameUser() {
		AuctionHouse ah = new AuctionHouse("One Piece Series");
		User balthazar = new User("Balthazar");		
		User belquior = new User("Belquior");
		ah.offer(new Bid(balthazar, 400.0));
		ah.offer(new Bid(belquior, 600.0));
		ah.foldBid(belquior);
		
		assertEquals(2, ah.getBids().size());
		assertEquals(600.0, ah.getBids().get(ah.getBids().size() - 1).getValue(), 0.00001);
	}

	@Test
	public void verifyFoldBidLessThanFiveTimes() {
		AuctionHouse ah = new AuctionHouse("One Piece Series");
		User balthazar = new User("Balthazar");		
		User belquior = new User("Belquior");
	
		ah.offer(new Bid(balthazar, 400.0));
		ah.offer(new Bid(belquior, 600.0));
		
		ah.offer(new Bid(balthazar, 700.0));
		ah.offer(new Bid(belquior, 800.0));
		
		ah.offer(new Bid(balthazar, 850.0));
		ah.offer(new Bid(belquior, 900.0));
		
		ah.offer(new Bid(balthazar, 950.0));
		ah.offer(new Bid(belquior, 1000.0));
		
		ah.offer(new Bid(balthazar, 1100.0));
		ah.offer(new Bid(belquior, 1200.0));
		
		ah.foldBid(balthazar);
		
		assertEquals(10, ah.getBids().size());
		assertEquals(1200.0, ah.getBids().get(ah.getBids().size() - 1).getValue(), 0.00001);
	}	
	
	
}
