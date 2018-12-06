package br.com.camaroti.alex.tdd.auctionhouse.service;

import java.util.Calendar;
import java.util.List;

import br.com.camaroti.alex.tdd.auctionhouse.dao.AuctionHouseDAO;
import br.com.camaroti.alex.tdd.auctionhouse.dao.IPaymentDAO;
import br.com.camaroti.alex.tdd.auctionhouse.model.Appraiser;
import br.com.camaroti.alex.tdd.auctionhouse.model.Payment;

public class PaymentService {

	private AuctionHouseDAO auction;
	private Appraiser appraiser;
	private IPaymentDAO paymentDAO;

	public PaymentService(AuctionHouseDAO auction, Appraiser appraiser, IPaymentDAO paymentDAO) {
		this.auction = auction;
		this.appraiser = appraiser;
		this.paymentDAO = paymentDAO;
		
	}
	public void gera() {
		List<AuctionHouse> closedAuctions = this.auction.closedAuctions();
		
		for (AuctionHouse currentAuction : closedAuctions) {
			this.appraiser.evaluate(currentAuction);
			
			Payment newPayment = new Payment(this.appraiser.getBiggestBid(), Calendar.getInstance());
			this.paymentDAO.save(newPayment);
		}
	}
}
