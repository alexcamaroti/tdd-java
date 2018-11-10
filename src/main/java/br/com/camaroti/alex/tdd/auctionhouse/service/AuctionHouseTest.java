package br.com.camaroti.alex.tdd.auctionhouse.service;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import br.com.camaroti.alex.tdd.auctionhouse.model.Appraiser;
import br.com.camaroti.alex.tdd.auctionhouse.model.Bid;
import br.com.camaroti.alex.tdd.auctionhouse.model.User;

public class AuctionHouseTest {

	@Test
	public void verifyBiggestLowestAverageBid() {
		User john = new User("1", "John");
		User jose = new User("2", "José");
		User mariah = new User("3", "Mariah");

		AuctionHouse auctionhouse = new AuctionHouse("Playstation 4");

		auctionhouse.offer(new Bid(john, 250.0));
		auctionhouse.offer(new Bid(jose, 300.0));
		auctionhouse.offer(new Bid(mariah, 400.0));

		Appraiser appraiser = new Appraiser();
		appraiser.evaluate(auctionhouse);

		Double biggestExpected = 400d;
		Double lowestExpected = 250d;
		Double averageExpected = 316.66;

		assertEquals(biggestExpected, appraiser.getBiggestBid());
		assertEquals(lowestExpected, appraiser.getLowestBid());
		assertEquals(averageExpected, appraiser.getAverageBid(), 0.01);
		System.out.println(appraiser.getLowestBid());

	}

	@Test
	public void verifyAuctionHouseJustOneBid() {
		User john = new User("1", "John");

		AuctionHouse auctionhouse = new AuctionHouse("Playstation 3 Novo");
		auctionhouse.offer(new Bid(john, 800.0));

		Appraiser appraiser = new Appraiser();
		appraiser.evaluate(auctionhouse);

		assertEquals(800.0, appraiser.getBiggestBid(), 0.000001);
		assertEquals(800.0, appraiser.getLowestBid(), 0.000001);
		assertEquals(800, appraiser.getAverageBid(), 0.01);

	}

	@Test
	public void verifyBiggerThreeBids() {
		User john = new User("1", "John");
		User jose = new User("2", "José");
		User mariah = new User("3", "Mariah");
		User alex = new User("4", "Alex");

		AuctionHouse auctionhouse = new AuctionHouse("Playstation 4 Novo");

		auctionhouse.offer(new Bid(john, 1500.0));
		auctionhouse.offer(new Bid(jose, 1600.0));
		auctionhouse.offer(new Bid(mariah, 1700.0));
		auctionhouse.offer(new Bid(alex, 1850.0));

		Appraiser appraiser = new Appraiser();
		appraiser.evaluate(auctionhouse);

		assertEquals(3, appraiser.getBiggerThreeBids().size());

		assertEquals(1850, appraiser.getBiggerThreeBids().get(0).getValue(), 0.0000001);
		assertEquals(1700, appraiser.getBiggerThreeBids().get(1).getValue(), 0.0000001);
		assertEquals(1600, appraiser.getBiggerThreeBids().get(2).getValue(), 0.0000001);

	}

	@Test
	public void verifyAuctionHouseBidsRandomnly() {
		User john = new User("1", "John");
		User jose = new User("2", "José");
		User mariah = new User("3", "Mariah");
		User alex = new User("4", "Alex");
		User claudete = new User("5", "Claudete");
		User cayley = new User("6", "Cayley");

		AuctionHouse auctionhouse = new AuctionHouse("Playstation 4 Novo");

		auctionhouse.offer(new Bid(john, 200.0));
		auctionhouse.offer(new Bid(jose, 450.0));
		auctionhouse.offer(new Bid(mariah, 120.0));
		auctionhouse.offer(new Bid(alex, 700.0));
		auctionhouse.offer(new Bid(claudete, 630.0));
		auctionhouse.offer(new Bid(cayley, 230.0));

		Appraiser appraiser = new Appraiser();
		appraiser.evaluate(auctionhouse);

		assertEquals(700.0, appraiser.getBiggestBid(), 0.00001);
		assertEquals(120, appraiser.getLowestBid(), 0.00001);

	}

	@Test
	public void verifyBidsDescendingOrder() {
		User john = new User("1", "John");
		User jose = new User("2", "José");
		User mariah = new User("3", "Mariah");
		User alex = new User("4", "Alex");

		AuctionHouse auctionhouse = new AuctionHouse("World of Warcraft: Battle for Azeroth Deluxe Edition");

		auctionhouse.offer(new Bid(john, 400.0));
		auctionhouse.offer(new Bid(jose, 300.0));
		auctionhouse.offer(new Bid(mariah, 200.0));
		auctionhouse.offer(new Bid(alex, 100.0));

		Appraiser appraiser = new Appraiser();
		appraiser.evaluate(auctionhouse);

		assertEquals(400.0, appraiser.getBiggerBids().get(0).getValue(), 0.00001);
		assertEquals(300.0, appraiser.getBiggerBids().get(1).getValue(), 0.00001);
		assertEquals(200.0, appraiser.getBiggerBids().get(2).getValue(), 0.00001);
		assertEquals(100.0, appraiser.getBiggerBids().get(3).getValue(), 0.00001);

		assertEquals(400.0, appraiser.getBiggestBid(), 0.0001);
		assertEquals(100.0, appraiser.getLowestBid(), 0.0001);

	}

	@Test
	public void verifyEmptyOffer() {
		AuctionHouse auctionhouse = new AuctionHouse("World of Warcraft: Battle for Azeroth Deluxe Edition");
		Appraiser appraiser = new Appraiser();
		appraiser.evaluate(auctionhouse);

		assertEquals(0, appraiser.getBiggerThreeBids().size());
	}

}
