package br.com.camaroti.alex.tdd.auctionhouse.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.camaroti.alex.tdd.auctionhouse.model.Bid;
import br.com.camaroti.alex.tdd.auctionhouse.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data 
public class AuctionHouse {

	private Calendar date;
	@NonNull private String description;
	private List<Bid> bids;
	private boolean finished;
	private int id;
	
	

	public AuctionHouse(String description, Calendar date) {
		super();
		this.date = date;
		this.description = description;
		this.bids = new ArrayList<Bid>();
	}
	
	
	public void offer(Bid bid) {		
		if(this.bids.isEmpty() || canBid(bid.getUser())) {
			bids.add(bid);	
		}
	}


	private boolean canBid(User user) {
		return !user.equals(getLastBid().getUser()) && quantityBidsOf(user) < 5;
	}


	private int quantityBidsOf(User user) {
		int countOffers = 0;
		for (Bid currentBid : bids) {
			if(user.equals(currentBid.getUser())) 
				countOffers++;
		}
		return countOffers;
	}


	private Bid getLastBid() {
		return this.bids.get(this.bids.size() -1);
	}


	public AuctionHouse(String description) {
		super();
		this.description = description;
		this.bids = new ArrayList<Bid>();
	}


	public void foldBid(User user) {
		this.offer(new Bid(user, getLastBidFolded()));
	}


	private double getLastBidFolded() {
		return getLastBid().getValue() * 2;
	}
	
	
	public void finish() {
		this.finished = true;
	}

}
