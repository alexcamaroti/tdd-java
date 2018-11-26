package br.com.camaroti.alex.tdd.auctionhouse.builder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.camaroti.alex.tdd.auctionhouse.model.Bid;
import br.com.camaroti.alex.tdd.auctionhouse.model.User;
import br.com.camaroti.alex.tdd.auctionhouse.service.AuctionHouse;
import lombok.Data;

@Data
public class AuctionHouseFactory {

	private AuctionHouse auctionHouse;
	private Calendar date;
	private String description;
	private List<Bid> bids;

	public AuctionHouseFactory() {
		bids = new ArrayList<Bid>();
	}
	
	
	public AuctionHouseFactory createAuction(String description) {
		this.auctionHouse = new AuctionHouse(description);
		this.description = description;
		return this;
	}

	public AuctionHouseFactory offer(User user, double value) {
		auctionHouse.offer(new Bid(user, value));
		return this;
	}
	
	public AuctionHouseFactory onDate(Calendar date) {
		this.date = date;
		return this;
	}

	public AuctionHouse build() {
		AuctionHouse ah = new AuctionHouse(description, date);
		for (Bid bid : bids) {
			ah.offer(bid);
		}
		return ah;
	}

}
