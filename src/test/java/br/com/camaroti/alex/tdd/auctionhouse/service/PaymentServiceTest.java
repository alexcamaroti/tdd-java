package br.com.camaroti.alex.tdd.auctionhouse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.camaroti.alex.tdd.auctionhouse.builder.AuctionHouseFactory;
import br.com.camaroti.alex.tdd.auctionhouse.dao.AuctionHouseDAO;
import br.com.camaroti.alex.tdd.auctionhouse.dao.IPaymentDAO;
import br.com.camaroti.alex.tdd.auctionhouse.infra.Clock;
import br.com.camaroti.alex.tdd.auctionhouse.model.Appraiser;
import br.com.camaroti.alex.tdd.auctionhouse.model.Payment;
import br.com.camaroti.alex.tdd.auctionhouse.model.User;

public class PaymentServiceTest {

	private Appraiser appraiser;
	private AuctionHouseDAO autionMock;
	private IPaymentDAO paymentMock;
	private AuctionHouseFactory ahf1;
	private AuctionHouseFactory ahf2;
	private AuctionHouseFactory ahf3;
	private Calendar oldDate;
	private Calendar yesterday;
	private User john;
	private User jose;
	private User mariah;
	private User alex;
	private User claudete;
	private User cayley;
	private Clock clockMock;

	@Before
	public void setUp() {
		paymentMock = mock(IPaymentDAO.class);
		autionMock = mock(AuctionHouseDAO.class);
		clockMock = mock(Clock.class);
		appraiser = new Appraiser();
		ahf1 = new AuctionHouseFactory().createAuction("4K Ultra HD TV 55 pol LG");
		ahf2 = new AuctionHouseFactory().createAuction("Refrigerator Consul");
		ahf3 = new AuctionHouseFactory().createAuction("Nintendo switch 32GB Gray");

		oldDate = Calendar.getInstance();
		oldDate.set(1999, 1, 25);

		yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);

		this.john = new User(1, "John");
		this.jose = new User(2, "José");
		this.mariah = new User(3, "Mariah");
		this.alex = new User(4, "Alex");
		this.claudete = new User(5, "Claudete");
		this.cayley = new User(6, "Cayley");
	}

	@Test
	public void mustGeneratePaymentForClosedAuction() {
		AuctionHouse ah1 = ahf1.offer(john, 1700).offer(alex, 1800).offer(cayley, 2500).onDate(oldDate).build();
		when(autionMock.closedAuctions()).thenReturn(Arrays.asList(ah1));

		appraiser.evaluate(ah1);

		PaymentService paymentService = new PaymentService(autionMock, appraiser, paymentMock);
		paymentService.generate();

		ArgumentCaptor<Payment> argument = ArgumentCaptor.forClass(Payment.class);
		verify(paymentMock).save(argument.capture());

		Payment paymentGenerated = argument.getValue();

		assertEquals(2500.0, paymentGenerated.getValue(), 0000.1);

	}

	@Test
	public void mustSetNextBusinessDay() {
		AuctionHouse ah1 = ahf1.offer(john, 1700)
				.offer(alex, 1800)
				.offer(cayley, 2500)
				.onDate(oldDate).build();

		when(autionMock.closedAuctions()).thenReturn(Arrays.asList(ah1));

		appraiser.evaluate(ah1);
	
		Calendar saturday = Calendar.getInstance();
		saturday.set(2018, Calendar.DECEMBER, 8);
		when(clockMock.today()).thenReturn(saturday);
		System.out.println(saturday.get(Calendar.DAY_OF_WEEK));

		PaymentService paymentService = new PaymentService(autionMock, appraiser, paymentMock, clockMock);
		paymentService.generate();
		
		
		ArgumentCaptor<Payment> argument = ArgumentCaptor.forClass(Payment.class);
		verify(paymentMock).save(argument.capture());
		
		Payment paymentGenerated = argument.getValue();
		assertEquals(Calendar.MONDAY, paymentGenerated.getDate().get(Calendar.DAY_OF_WEEK));
	}
}
