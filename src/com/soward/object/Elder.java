package com.soward.object;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.soward.db.DB;

//public class Elder extends Member {
//
//	public final static String TABLE_NAME = "elder";
//
//	public Elder() {
//	}
//
//	public ArrayList<Elder> getAllElders() {
//		ArrayList<Elder> Elders = new ArrayList<Elder>();
//
//		DB db = new DB();
//		try {
//			Connection conn = db.openConnection();
//			Statement stm = conn.createStatement();
//			ResultSet rset = stm.executeQuery("select * from family");
//			while (rset.next()) {
//				Elder cl = new Elder();
//				cl.setPid(rset.getString("pid"));
//				cl.setFirstName(rset.getString("first_name"));
//				cl.setLastName(rset.getString("last_name"));
//				cl.setAddr1(rset.getString("addr1"));
//				cl.setAddr2(rset.getString("addr2"));
//				cl.setEmail(rset.getString("email"));
//				cl.setPhone(rset.getString("phone"));
//				cl.setAge(rset.getString("age"));
//				cl.setCell(rset.getString("cell"));
//				Elders.add(cl);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return Elders;
//
//	}
//
//	public static void main(String args[]) {
//		Elder cc = new Elder();
//		ArrayList<Elder> Elders = cc.getAllElders();
//		for (Elder temp : Elders) {
//			//			temp.getFristName()+" "+temp.getLastName();
//			//temp.getAddr1()+" "+temp.getAddr2();
//			temp.getEmail();
//			temp.getPhone();
//
//		}
//	}
//}
