package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;
import com.todo.service.DbConnect;

public class TodoList {
	Connection conn;
	
	private List<TodoItem> list;


	public TodoList() {
		this.conn = DbConnect.getConnection();
		this.list = new ArrayList<TodoItem>();
	}
	

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, desc, category, current_date, due_date, is_completed, importance, time)" + "values (?,?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count =0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, 0);
			pstmt.setString(7, t.getImportance());
			pstmt.setString(8, t.getTime());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, desc=?, category=?, current_date=?, due_date=?, is_completed, importance, time" + " where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getIs_completed());
			pstmt.setString(7, t.getImportance());
			pstmt.setString(8, t.getTime());
			count = pstmt.executeUpdate();
			pstmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, desc, category, current_date, due_date, importance, time)" + "values (?,?,?,?,?,?,?);";
			int records = 0;
			while((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				String importance = st.nextToken();
				String time = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, desc);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				pstmt.setString(6, importance);
				pstmt.setString(7, time);
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				pstmt.close();
			}
			System.out.println(records + " records read!!");
			br.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int completeItem(int index) {
		String sql = "update list set is_completed = ?" + " where id = ?";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1); 
			pstmt.setInt(2, index);
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int completeCancleItem(int index) {
		String sql = "update list set is_completed = ?" + " where id = ?";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0); //t.getIs_completed()
			pstmt.setInt(2, index);
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	public ArrayList<TodoItem> getCompList(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;	
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list WHERE is_completed = 1";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String importance = rs.getString("importance");
				String time = rs.getString("time");
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, time);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed); //
				list.add(t);
				
			}
			stmt.close();
		} catch(SQLException e) { e.printStackTrace();}
		
		return list;
	}
	
	
	public ArrayList<TodoItem> getList(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;	
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String importance = rs.getString("importance");
				String time = rs.getString("time");
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, time);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				list.add(t);
			}
			stmt.close();
		} catch(SQLException e) { e.printStackTrace();}
		
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";	
		try {
			String sql = "SELECT * FROM list WHERE title like ? or desc like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String importance = rs.getString("importance");
				String time = rs.getString("time");
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, time);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
		}
		catch(SQLException e) { e.printStackTrace();}
		
		return list;
	}
	
	public ArrayList<TodoItem> getList(int number) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;	
		try {
			String sql = "SELECT * FROM list WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				String importance = rs.getString("importance");
				String time = rs.getString("time");
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, time);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				list.add(t);
			}
		}
		catch(SQLException e) { e.printStackTrace();}
		
		return list;
	}
	
	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("\n"
				+ "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println("["+myitem.getTitle()+"] " + myitem.getDesc() );
		}
	}

	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}

	public static void closeConnection() {
		DbConnect.closeConnection();
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
		}
		catch(SQLException e) { e.printStackTrace();}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String importance = rs.getString("importance");
				String time = rs.getString("time");
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, time);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
		}
		catch(SQLException e) { e.printStackTrace();}
		
		return list;
		
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if(ordering==0) 
				sql+=" desc";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String importance = rs.getString("importance");
				String time = rs.getString("time");
				TodoItem t = new TodoItem(title, desc, category, due_date, importance, time);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setImportance(importance);
				list.add(t);
			}
		}
		catch(SQLException e) { e.printStackTrace();}
		return list;
	}
}
