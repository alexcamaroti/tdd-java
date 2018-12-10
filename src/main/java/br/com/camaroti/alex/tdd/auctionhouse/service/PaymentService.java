package br.com.camaroti.alex.tdd.auctionhouse.service;

import java.util.Calendar;
import java.util.List;

import br.com.camaroti.alex.tdd.auctionhouse.dao.AuctionHouseDAO;
import br.com.camaroti.alex.tdd.auctionhouse.dao.IPaymentDAO;
import br.com.camaroti.alex.tdd.auctionhouse.infra.Clock;
import br.com.camaroti.alex.tdd.auctionhouse.infra.SystemClock;
import br.com.camaroti.alex.tdd.auctionhouse.model.Appraiser;
import br.com.camaroti.alex.tdd.auctionhouse.model.Payment;

public class PaymentService {

	private AuctionHouseDAO auction;
	private Appraiser appraiser;
	private IPaymentDAO paymentDAO;
	private Clock clock;

	public PaymentService(AuctionHouseDAO auction, Appraiser appraiser, IPaymentDAO paymentDAO, Clock clock) {
		this.auction = auction;
		this.appraiser = appraiser;
		this.paymentDAO = paymentDAO;
		this.clock = clock;
	}
	
	public PaymentService(AuctionHouseDAO auction, Appraiser appraiser, IPaymentDAO paymentDAO) {
		this(auction, appraiser, paymentDAO, new SystemClock());
	}
	
	public void generate() {
		List<AuctionHouse> closedAuctions = this.auction.closedAuctions();
		
		for (AuctionHouse currentAuction : closedAuctions) {
			this.appraiser.evaluate(currentAuction);
			
			Payment newPayment = new Payment(this.appraiser.getBiggestBid(), firstBusinessDay());
			this.paymentDAO.save(newPayment);
		}
	}
	private Calendar firstBusinessDay() {
		Calendar calendar = clock.today();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek == Calendar.SATURDAY) {
			calendar.add(Calendar.DAY_OF_MONTH, 2);
		} else if (dayOfWeek == Calendar.SUNDAY) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return calendar;
	}
}
