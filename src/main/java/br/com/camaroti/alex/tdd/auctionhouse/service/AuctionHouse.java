package br.com.camaroti.alex.tdd.auctionhouse.service;

import java.util.ArrayList;
import java.util.List;

import br.com.camaroti.alex.tdd.auctionhouse.model.Bid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data 
public class AuctionHouse {

	@NonNull private String description;
	private List<Bid> bids;
	
	
	
	public void offer(Bid bid) {
		bids.add(bid);
	}


	public AuctionHouse(String description) {
		super();
		this.description = description;
		this.bids = new ArrayList<Bid>();
	}
	
}
