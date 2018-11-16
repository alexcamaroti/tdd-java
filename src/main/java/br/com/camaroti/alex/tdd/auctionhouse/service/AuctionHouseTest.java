package br.com.camaroti.alex.tdd.auctionhouse.service;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.camaroti.alex.tdd.auctionhouse.builder.AuctionHouseFactory;
import br.com.camaroti.alex.tdd.auctionhouse.model.Appraiser;
import br.com.camaroti.alex.tdd.auctionhouse.model.User;

public class AuctionHouseTest {
	
	private Appraiser appraiser;
	private User jose;
	private User john;
	private User mariah;
	private User alex;
	private User claudete;
	private User cayley;

	@Before
	public void setUp() {
		this.appraiser = new Appraiser();
		this.jose = new User("2", "José");
		this.john = new User("1", "John");
		this.mariah = new User("3", "Mariah");
		this.alex = new User("4", "Alex");
		this.claudete = new User("5", "Claudete");
		this.cayley = new User("6", "Cayley");
	}
	
	@BeforeClass
	public static void testandoBeforeClass() {
	  System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
	  System.out.println("after class");
	}

	@Test
	public void verifyBiggestLowestAverageBid() {
		AuctionHouse auctionhouse = new AuctionHouseFactory().createAuction("Playstation 4")
				.offer(john, 250.0)
				.offer(jose, 300.0)
				.offer(mariah, 400.0)
				.build();

		appraiser.evaluate(auctionhouse);

		Double biggestExpected = 400d;
		Double lowestExpected = 250d;
		Double averageExpected = 316.66;

		assertEquals(biggestExpected, appraiser.getBiggestBid());
		assertEquals(lowestExpected, appraiser.getLowestBid());
		assertEquals(averageExpected, appraiser.getAverageBid(), 0.01);

	}

	@Test
	public void verifyAuctionHouseJustOneBid() {
		AuctionHouse auctionhouse = new AuctionHouseFactory()
				.createAuction("Playstation 3 Novo")
				.offer(john, 800.0)
				.build();

		appraiser.evaluate(auctionhouse);

		assertEquals(800.0, appraiser.getBiggestBid(), 0.000001);
		assertEquals(800.0, appraiser.getLowestBid(), 0.000001);
		assertEquals(800, appraiser.getAverageBid(), 0.01);

	}

	@Test
	public void verifyBiggerThreeBids() {
		AuctionHouse auctionhouse = new AuctionHouseFactory()
				.createAuction("Playstation 4 Novo")
				.offer(john, 1500.0)
				.offer(jose, 1600.0)
				.offer(mariah, 1700.0)
				.offer(alex, 1850.0)
				.build();

		appraiser.evaluate(auctionhouse);

		assertEquals(3, appraiser.getBiggerThreeBids().size());

		assertEquals(1850, appraiser.getBiggerThreeBids().get(0).getValue(), 0.0000001);
		assertEquals(1700, appraiser.getBiggerThreeBids().get(1).getValue(), 0.0000001);
		assertEquals(1600, appraiser.getBiggerThreeBids().get(2).getValue(), 0.0000001);

	}

	@Test
	public void verifyAuctionHouseBidsRandomnly() {
		AuctionHouse auctionhouse = new AuctionHouseFactory()
				.createAuction("Playstation 4 Novo")
				.offer(john, 200.0)
				.offer(jose, 450.0)
				.offer(mariah, 120.0)
				.offer(alex, 700.0)
				.offer(claudete, 630.0)
				.offer(cayley, 230.0)
				.build();

		appraiser.evaluate(auctionhouse);

		assertEquals(700.0, appraiser.getBiggestBid(), 0.00001);
		assertEquals(120, appraiser.getLowestBid(), 0.00001);

	}

	@Test
	public void verifyBidsDescendingOrder() {
		AuctionHouse auctionhouse = new AuctionHouseFactory()
				.createAuction("World of Warcraft: Battle for Azeroth Deluxe Edition")
				.offer(john, 400.0)
				.offer(jose, 300.0)
				.offer(mariah, 200.0)
				.offer(alex, 100.0)
				.build();

		appraiser.evaluate(auctionhouse);

		assertEquals(400.0, appraiser.getBiggerBids().get(0).getValue(), 0.00001);
		assertEquals(300.0, appraiser.getBiggerBids().get(1).getValue(), 0.00001);
		assertEquals(200.0, appraiser.getBiggerBids().get(2).getValue(), 0.00001);
		assertEquals(100.0, appraiser.getBiggerBids().get(3).getValue(), 0.00001);

		assertEquals(400.0, appraiser.getBiggestBid(), 0.0001);
		assertEquals(100.0, appraiser.getLowestBid(), 0.0001);

	}

	@Test(expected=RuntimeException.class)
	public void verifyEmptyOffer() {
		AuctionHouse auctionhouse = new AuctionHouse("World of Warcraft: Battle for Azeroth Deluxe Edition");
		appraiser.evaluate(auctionhouse);

	}

}
