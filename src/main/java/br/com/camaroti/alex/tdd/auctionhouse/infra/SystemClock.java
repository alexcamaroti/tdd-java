package br.com.camaroti.alex.tdd.auctionhouse.infra;

import java.util.Calendar;

public class SystemClock implements Clock{

	public Calendar today() {
		return Calendar.getInstance();
	}

}
