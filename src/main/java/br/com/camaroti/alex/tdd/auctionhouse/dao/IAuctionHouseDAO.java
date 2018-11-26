package br.com.camaroti.alex.tdd.auctionhouse.dao;

import java.util.List;

import br.com.camaroti.alex.tdd.auctionhouse.service.AuctionHouse;

public interface IAuctionHouseDAO {

	void save(AuctionHouse ah);
    List<AuctionHouse> closedAuctions();
    List<AuctionHouse> activeAuctions();
    void update(AuctionHouse ah);
	
}
