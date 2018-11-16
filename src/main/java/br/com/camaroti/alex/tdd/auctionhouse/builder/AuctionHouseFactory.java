package br.com.camaroti.alex.tdd.auctionhouse.builder;

import br.com.camaroti.alex.tdd.auctionhouse.model.Bid;
import br.com.camaroti.alex.tdd.auctionhouse.model.User;
import br.com.camaroti.alex.tdd.auctionhouse.service.AuctionHouse;

public class AuctionHouseFactory {

	private AuctionHouse auctionHouse;

	public AuctionHouseFactory createAuction(String description) {
		this.auctionHouse = new AuctionHouse(description);
		return this;
	}

	public AuctionHouseFactory offer(User user, double value) {
		auctionHouse.offer(new Bid(user, value));
		return this;
	}

	public AuctionHouse build() {
		return auctionHouse;
	}

}
