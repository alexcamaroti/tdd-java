package br.com.camaroti.alex.tdd.auctionhouse.email;

import br.com.camaroti.alex.tdd.auctionhouse.service.AuctionHouse;

public interface IEmailSender {
	void send(AuctionHouse ah);
}
