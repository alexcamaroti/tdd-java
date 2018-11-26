package br.com.camaroti.alex.tdd.auctionhouse.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.camaroti.alex.tdd.auctionhouse.service.AuctionHouse;

public class AuctionHouseDAOFake implements IAuctionHouseDAO{

	private static List<AuctionHouse> auctions = new ArrayList<AuctionHouse>();
	
	public void save(AuctionHouse ah) {
		auctions.add(ah);
	}

	public List<AuctionHouse> closedAuctions() {
		
		List<AuctionHouse> filtrados = new ArrayList<AuctionHouse>();
		for(AuctionHouse ah : auctions) {
			if(ah.isFinished()) filtrados.add(ah);
		}

		return filtrados;
	}
	
	public List<AuctionHouse> activeAuctions() {
		
		List<AuctionHouse> filtrados = new ArrayList<AuctionHouse>();
		for(AuctionHouse ah : auctions) {
			if(!ah.isFinished()) filtrados.add(ah);
		}

		return filtrados;
	}
	
	public void update(AuctionHouse ah) { /* do nothing! */ }
}
