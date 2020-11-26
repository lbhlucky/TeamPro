package dao.order;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import com.sun.xml.internal.ws.Closeable;

import vo.DetailOrderBean;
import vo.OrderBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import static db.JdbcUtil.*;

public class OrderDAO {
	Connection con;
	
	// 싱글톤 패턴 활용
	private OrderDAO() {}
	
	private static OrderDAO instance = new OrderDAO();

	public static OrderDAO getInstance() {
		return instance;
	}

	public void setConnection(Connection con) {
		this.con = con;
		
	}
	
	public String createOrderCode() {
		String orderCode = null;
		Date now = new Date();
		
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		
		String date = format.format(now);
		
		int index = 1;
		
		orderCode = date + index;
		index++;
		
		return orderCode;
	}

	public int insertOrder(OrderBean ob) {
		System.out.println("OrderDAO - insertOrder()!");
		int insertCount = 0;
		
		PreparedStatement p = null;
		ResultSet rs = null;
		
		try {
			String sql = "INSERT INTO mainorder VALUES(?,?,?,?,?,?,?,?)";
			p = con.prepareStatement(sql);
			p.setString(1, ob.getCode());
			p.setString(2, ob.getName());
			p.setString(3, ob.getPhone());
			p.setString(4, ob.getAddress());
			p.setTimestamp(5, ob.getDate());
			p.setString(6, ob.getStatus());
			p.setString(7, ob.getPayment());
			p.setString(8, ob.getMember_id());
			
			System.out.println(ob.getCode());
			System.out.println(ob.getName());
			System.out.println(ob.getPhone());
			System.out.println(ob.getAddress());
			System.out.println(ob.getDate());
			System.out.println(ob.getStatus());
			System.out.println(ob.getPayment());
			System.out.println(ob.getMember_id());
			
			insertCount = p.executeUpdate();
		} catch (Exception e) {
			System.out.println("OrderDAO insertOrder() 오류! - " +e.getMessage());
			e.printStackTrace();
		} finally {
			close(p);
			close(rs); 
		}
		
		return insertCount;
	}

	public ArrayList<OrderBean> getMainorder() {
		ArrayList<OrderBean> mainorderList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from mainorder";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				OrderBean order = new OrderBean();
				
				order.setCode(rs.getString("code"));
				order.setName(rs.getString("name"));
				order.setPhone(rs.getString("phone"));
				order.setAddress(rs.getString("address"));
				order.setStatus(rs.getString("status"));
				order.setPayment(rs.getString("payment"));
				order.setMember_id(rs.getString("member_id"));
				order.setDate(rs.getTimestamp("date"));
				
				mainorderList.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}
		
		return mainorderList;
	}

	public int getDetailorderCount(String mainorder_code) {
		int count = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) from detailorder where mainorder_code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mainorder_code);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}
		
		return count;
	}

	public ArrayList<DetailOrderBean> getDetailorderList(String mainorder_code) {
		ArrayList<DetailOrderBean> detailorderList = new ArrayList<DetailOrderBean>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from detailorder where mainorder_code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mainorder_code);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				DetailOrderBean order = new DetailOrderBean();
				
				order.setNum(rs.getInt("num"));
				order.setName(rs.getString("name"));
				order.setMain_img(rs.getString("main_img"));
				order.setPrice(rs.getInt("price"));
				order.setCnt(rs.getInt("cnt"));
				order.setColor(rs.getString("color"));
				order.setSize(rs.getString("size"));
				order.setMainorder_code(mainorder_code);
				order.setOpt_productCode(rs.getString("opt_productCode"));
				order.setDate(rs.getTimestamp("date"));
				
				detailorderList.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}
		
		return detailorderList;
	}

}
