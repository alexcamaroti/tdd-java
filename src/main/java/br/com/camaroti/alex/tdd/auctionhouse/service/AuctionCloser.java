package br.com.camaroti.alex.tdd.auctionhouse.service;

import java.util.Calendar;
import java.util.List;

import br.com.camaroti.alex.tdd.auctionhouse.dao.IAuctionHouseDAO;
import br.com.camaroti.alex.tdd.auctionhouse.email.IEmailSender;

public class AuctionCloser {

	private IAuctionHouseDAO dao;
	private final IEmailSender postman;
	private int total = 0;
	
	public AuctionCloser(IAuctionHouseDAO dao, IEmailSender postman) {
		this.dao = dao;
		this.postman = postman;
	}
	
	

	public void close() {
		List<AuctionHouse> allCurrentAuctions = dao.activeAuctions();

		for (AuctionHouse ah : allCurrentAuctions) {
			if (startedLastWeek(ah)) {
				ah.finish();
				total++;
				dao.update(ah);
				postman.send(ah);
			}
		}
	}

	private boolean startedLastWeek(AuctionHouse ah) {
		return daysBetween(ah.getDate(), Calendar.getInstance()) >= 7;
	}

	private int daysBetween(Calendar begin, Calendar end) {
		Calendar data = (Calendar) begin.clone();
		int daysInInterval = 0;
		while (data.before(end)) {
			data.add(Calendar.DAY_OF_MONTH, 1);
			daysInInterval++;
		}

		return daysInInterval;
	}

	public int getTotalClosed() {
		return total;
	}
}
