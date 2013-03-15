package com.soward.object;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.soward.db.DB;

public class User {
	private String pid;

	private String name;

	private String pass;

	private String first;

	private String last;

	private String email;

	private String role;

	private String phone;

	public User() {
		this.pid = new String();
		this.name = new String();
		this.pass = new String();
		this.first = new String();
		this.last = new String();
		this.email = new String();
		this.role = new String();
		this.phone = new String();
	}

	public User(String pid, String name, String pass, String first, String last, String email, String role, String phone) {

		this.pid = pid;
		this.name = name;
		this.pass = pass;
		this.first = first;
		this.last = last;
		this.email = email;
		this.role = role;
		this.phone = phone;
	}

	public ArrayList<User> getAllUser() {
		ArrayList<User> user = new ArrayList<User>();

		DB db = new DB();
		try {
			Connection conn = db.openConnection();
			Statement stm = conn.createStatement();
			String sql = "SELECT * FROM users ";// join user_roles on
			// user_roles.user_pid=users.user_pid";
			ResultSet rset = stm.executeQuery(sql);
			while (rset.next()) {
				User cl = new User();
				cl.setPid(rset.getString("user_pid"));
				cl.setName(rset.getString("user_name"));
				cl.setPass(rset.getString("user_pass"));
				cl.setFirst(rset.getString("user_first"));
				cl.setLast(rset.getString("user_last"));
				cl.setEmail(rset.getString("user_email"));
				cl.setRole(rset.getString("user_role"));
				cl.setPhone(rset.getString("user_phone"));
				user.add(cl);
			}
			rset.close();
			stm.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	/*
	 * fetch for a single pid
	 */
	public void fetch(String pid) {
		DB db = new DB();
		try {
			Connection conn = db.openConnection();
			Statement stm = conn.createStatement();
			String sql = "SELECT * FROM users where user_pid='" + pid + "'";// join user_roles on
			ResultSet rset = stm.executeQuery(sql);
			while (rset.next()) {
				this.setPid(rset.getString("user_pid"));
				this.setName(rset.getString("user_name"));
				this.setPass(rset.getString("user_pass"));
				this.setFirst(rset.getString("user_first"));
				this.setLast(rset.getString("user_last"));
				this.setEmail(rset.getString("user_email"));
				this.setRole(rset.getString("user_role"));
				this.setPhone(rset.getString("user_phone"));
			}
			rset.close();
			stm.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean canEdit(HttpSession session) {
		boolean canEdit = false;
		if (session != null) {
			String Uid = (String) session.getAttribute("Uid");
			//if current user is trying to edit or if user pid =='scott' 
			if (Uid.equalsIgnoreCase(this.getPid())||Uid.equalsIgnoreCase("7")) {
				canEdit = true;
			}
		}
		return canEdit;
	}
	
	public static boolean isAdmin(HttpSession session) {
	    boolean isAdmin = false;
	    if (session != null) {
	        String Uid = (String) session.getAttribute("Uid");
            User rem = new User();
            rem.fetch(Uid);
            try {
                isAdmin = rem.getRole().equals( "admin" )?true:false;
            } catch ( Exception e ) {
                e.printStackTrace();
            }
	    }
	    return isAdmin;
	}
	public static boolean isStakeAdmin(HttpSession session) {
	    boolean isAdmin = false;
	    if (session != null) {
	        String Uid = (String) session.getAttribute("Uid");
	        User rem = new User();
	        rem.fetch(Uid);
	        try {
	            isAdmin = rem.getRole().equals( "stakeAdmin" )?true:false;
	        } catch ( Exception e ) {
	            e.printStackTrace();
	        }
	    }
	    return isAdmin;
	}
	public boolean remove(){
		DB db = new DB();
		int rset = 0;
		try {
			Connection conn = db.openConnection();
			Statement stm = conn.createStatement();
			String sql = "DELETE FROM users where user_pid='" + this.pid + "'";// join user_roles on
			rset = stm.executeUpdate(sql);
			stm.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (rset>0);
	}
	public void removeNull(){
		if(this.pid==null){this.pid = new String();}
		if(this.name==null){this.name = new String();}
		if(this.pass==null){this.pass = new String();}
		if(this.first==null){this.first = new String();}
		if(this.last==null){this.last = new String();}
		if(this.email==null){this.email = new String();}
		if(this.role==null){this.role = new String();}
		if(this.phone==null){this.phone = new String();}
	}

	public void setPid(String str) {
		this.pid = str;
	}

	public void setName(String str) {
		this.name = str;
	}

	public void setPass(String str) {
		this.pass = str;
	}

	public void setFirst(String str) {
		this.first = str;
	}

	public void setLast(String str) {
		this.last = str;
	}

	public void setEmail(String str) {
		this.email = str;
	}

	public void setRole(String str) {
		this.role = str;
	}

	public void setPhone(String str) {
		this.phone = str;
	}

	public String getPid() {
		return this.pid;
	}

	public String getName() {
		return this.name;
	}

	public String getPass() {
		return this.pass;
	}

	public String getFirst() {
		return this.first;
	}

	public String getLast() {
		return this.last;
	}

	public String getEmail() {
		return this.email;
	}

	public String getRole() {
		return this.role;
	}

	public String getPhone() {
		return this.phone;
	}

	public static void main(String[] args) {
		User user = new User();
		ArrayList<User> uu = user.getAllUser();
	}

}
