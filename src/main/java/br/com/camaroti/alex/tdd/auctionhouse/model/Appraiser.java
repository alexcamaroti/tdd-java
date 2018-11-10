package br.com.camaroti.alex.tdd.auctionhouse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.camaroti.alex.tdd.auctionhouse.service.AuctionHouse;

public class Appraiser {


	private Double biggestBid = Double.NEGATIVE_INFINITY;
	private Double lowestBid = Double.POSITIVE_INFINITY;
	private Double averageBid = 0.0;
	private List<Bid> biggerBids;
	private List<Bid> biggerThreeBids;
	
	public void evaluate(AuctionHouse auctionhouse) {
		
		for (Bid bid : auctionhouse.getBids()) {
			if (bid.getValue() > biggestBid) {
				biggestBid = bid.getValue();
			}
			if (bid.getValue() < lowestBid ) {
				lowestBid = bid.getValue();
			}
			
			averageBid = averageBid + bid.getValue();
		}
		averageBid = averageBid / auctionhouse.getBids().size();
		
		orderAndGetByBiggerBids(auctionhouse);
	}


	private void orderAndGetByBiggerBids(AuctionHouse auctionhouse) {
		biggerBids = new ArrayList<Bid>(auctionhouse.getBids());
		Collections.sort(biggerBids, new Comparator<Bid>(){

			public int compare(Bid o1, Bid o2) {
				if(o1.getValue() < o2.getValue())  return 1;
				if (o1.getValue() > o2.getValue()) return -1;				
				return 0;
			}
			
		});
		biggerBids = biggerBids.subList(0, biggerBids.size());
		biggerThreeBids = biggerBids.subList(0, biggerBids.size() > 3 ? 3 : biggerBids.size());
	}
	
	
	public List<Bid> getBiggerThreeBids() {
		return biggerThreeBids;
	}
	
	public List<Bid> getBiggerBids() {
		return biggerBids;
	}
	
	public Double getAverageBid() {
		return averageBid;
	}
	
	public Double getBiggestBid() {
		return biggestBid;
	}
	
	public Double getLowestBid() {
		return lowestBid;
	}
}


