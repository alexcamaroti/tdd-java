package br.com.camaroti.alex.tdd.auctionhouse.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.inOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import br.com.camaroti.alex.tdd.auctionhouse.builder.AuctionHouseFactory;
import br.com.camaroti.alex.tdd.auctionhouse.dao.IAuctionHouseDAO;
import br.com.camaroti.alex.tdd.auctionhouse.email.IEmailSender;

public class AuctionCloserTest {

	private AuctionHouseFactory ahf1;
	private AuctionHouseFactory ahf2;
	private AuctionHouseFactory ahf3;
	private IAuctionHouseDAO daoFake;
	private Calendar oldDate;
	private Calendar yesterday;
	private IEmailSender postmanFake;

	@Before
	public void SetUp() {
		oldDate = Calendar.getInstance();
		oldDate.set(1999, 1, 25);
		
		yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		
		ahf1 = new AuctionHouseFactory().createAuction("4K Ultra HD TV 55 pol LG");
		ahf2 = new AuctionHouseFactory().createAuction("Refrigerator Consul");
		ahf3 = new AuctionHouseFactory().createAuction("Nintendo switch 32GB Gray");
		
		daoFake = mock(IAuctionHouseDAO.class);
		postmanFake = mock(IEmailSender.class);

	}

	@Test
	public void mustCloseAuctionsThatStartedWeekAgo() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1999, 1, 25);
		
		AuctionHouse ah1 = ahf1.onDate(calendar).build();
		AuctionHouse ah2 = ahf2.onDate(calendar).build();
		List<AuctionHouse> oldAuctions = Arrays.asList(ah1, ah2);

		when(daoFake.activeAuctions()).thenReturn(oldAuctions);

		AuctionCloser ahCloser = new AuctionCloser(daoFake, postmanFake);
		ahCloser.close();

		assertEquals(2, ahCloser.getTotalClosed());
		assertTrue(ah1.isFinished());
		assertTrue(ah2.isFinished());
	}

	@Test
	public void AuctionsStartedYesterdayCantBeClosed() {	
		
		AuctionHouse ah1 = ahf1.onDate(oldDate).build();
		AuctionHouse ah2 = ahf2.onDate(oldDate).build();
		AuctionHouse ah3 = ahf3.onDate(yesterday).build();
		List<AuctionHouse> oldAuctions = Arrays.asList(ah1, ah2, ah3);
		when(daoFake.activeAuctions()).thenReturn(oldAuctions);
		
		AuctionCloser ahCloser = new AuctionCloser(daoFake, postmanFake);
		ahCloser.close();

		assertEquals(2, ahCloser.getTotalClosed());
		assertTrue(ah1.isFinished());
		assertTrue(ah2.isFinished());
		assertFalse(ah3.isFinished());
	}
	
	@Test
	public void doNothingIfThereIsNoAuction() {
		AuctionHouse ah3 = ahf3.onDate(yesterday).build();
		when(daoFake.activeAuctions()).thenReturn(new ArrayList<AuctionHouse>());
		AuctionCloser ahCloser = new AuctionCloser(daoFake, postmanFake);
		ahCloser.close();

		assertEquals(0, ahCloser.getTotalClosed());
		assertFalse(ah3.isFinished());		
	}
	
	@Test
	public void mustUpdateClosedAuctions() {
		AuctionHouse ah1 = ahf1.onDate(oldDate).build();
		
		when(daoFake.activeAuctions()).thenReturn(Arrays.asList(ah1));
		AuctionCloser ahCloser = new AuctionCloser(daoFake, postmanFake);
		ahCloser.close();
		
		verify(daoFake, times(1)).update(ah1);
		
	}
	
	@Test
	public void cantUpdateRecentAuctions() {
		AuctionHouse ah1 = ahf1.onDate(yesterday).build();
		AuctionHouse ah2 = ahf2.onDate(yesterday).build();
		when(daoFake.activeAuctions()).thenReturn(Arrays.asList(ah1, ah2));
		AuctionCloser ahCloser = new AuctionCloser(daoFake, postmanFake);
		ahCloser.close();
		
		assertFalse(ah1.isFinished());
		assertFalse(ah2.isFinished());
		assertEquals(0, daoFake.closedAuctions().size());
		verify(daoFake, never()).update(ah1);
		verify(daoFake, never()).update(ah2);
		
	}
	
	@Test
	public void checkEmailSenderAfterUpdate() {
		AuctionHouse ah1 = ahf1.onDate(oldDate).build();		
		
		when(daoFake.activeAuctions()).thenReturn(Arrays.asList(ah1));
		AuctionCloser ahCloser = new AuctionCloser(daoFake, postmanFake);
		ahCloser.close();
		InOrder inOrder = inOrder(daoFake, postmanFake);
		inOrder.verify(daoFake, times(1)).update(ah1);
		inOrder.verify(postmanFake, times(1)).send(ah1);
	}
	
	

}
