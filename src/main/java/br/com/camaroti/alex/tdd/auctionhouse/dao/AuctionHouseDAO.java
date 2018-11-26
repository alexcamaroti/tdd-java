package br.com.camaroti.alex.tdd.auctionhouse.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.camaroti.alex.tdd.auctionhouse.model.Bid;
import br.com.camaroti.alex.tdd.auctionhouse.model.User;
import br.com.camaroti.alex.tdd.auctionhouse.service.AuctionHouse;

public class AuctionHouseDAO implements IAuctionHouseDAO{

	private Connection conn;

	public AuctionHouseDAO() {
		try {
			this.conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/mocks", "root", "");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Calendar data(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	public void save(AuctionHouse ah) {
		try {
			String sql = "INSERT INTO AUCTIONHOUSE (DESCRIPTION, DATE, FINISHED) VALUES (?,?,?);";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, ah.getDescription());
			ps.setDate(2, new java.sql.Date(ah.getDate().getTimeInMillis()));
			ps.setBoolean(3, ah.isFinished());
			
			ps.execute();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            ah.setId(generatedKeys.getInt(1));
	        }
			
			for(Bid bid : ah.getBids()) {
				sql = "INSERT INTO bids (AUCTIONHOUSE_ID, USER_ID, VALUE) VALUES (?,?,?);";
				PreparedStatement ps2 = conn.prepareStatement(sql);
				ps2.setInt(1, ah.getId());
				ps2.setInt(2, bid.getUser().getId());
				ps2.setDouble(3, bid.getValue());
				
				ps2.execute();
				ps2.close();
				
			}
			
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public List<AuctionHouse> closedAuctions() {
		return returnAuctionsByStatus(true);
	}
	
	public List<AuctionHouse> activeAuctions() {
		return returnAuctionsByStatus(false);
	}
	
	private List<AuctionHouse> returnAuctionsByStatus(boolean status) {
		try {
			String sql = "SELECT * FROM auctionhouse WHERE finished = " + status + ";";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			List<AuctionHouse> leiloes = new ArrayList<AuctionHouse>();
			while(rs.next()) {
				AuctionHouse ah = new AuctionHouse(rs.getString("description"), data(rs.getDate("date")));
				ah.setId(rs.getInt("id"));
				if(rs.getBoolean("finished")) ah.finish();
				
				String sql2 = "SELECT VALLUE, NAME, U.ID AS USER_ID, L.ID AS BID_ID FROM LANCES L INNER JOIN USER U ON U.ID = L.USER_ID WHERE AUCTIONHOUSE_ID = " + rs.getInt("id");
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ResultSet rs2 = ps2.executeQuery();
				
				while(rs2.next()) {
					User user = new User(rs2.getInt("id"), rs2.getString("name"));
					Bid bid = new Bid(user, rs2.getDouble("value"));
					
					ah.offer(bid);
				}
				rs2.close();
				ps2.close();
				
				leiloes.add(ah);
				
			}
			rs.close();
			ps.close();
			
			return leiloes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(AuctionHouse ah) {
		
		try {
			String sql = "UPDATE AUCTIONHOUSE SET DESCRIPTION=?, DATE=?, FINISHED=? WHERE ID = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, ah.getDescription());
			ps.setDate(2, new java.sql.Date(ah.getDate().getTimeInMillis()));
			ps.setBoolean(3, ah.isFinished());
			ps.setInt(4, ah.getId());

			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int x() { return 10; }
	
	public String test() { return "test"; }
}
