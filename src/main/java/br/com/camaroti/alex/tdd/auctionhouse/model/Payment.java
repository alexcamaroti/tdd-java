package br.com.camaroti.alex.tdd.auctionhouse.model;

import java.util.Calendar;

import lombok.Data;

@Data
public class Payment {

	
	private double value;
	private Calendar date;

	public Payment(double Value, Calendar date) {
		value = Value;
		this.date = date;
	}
}
