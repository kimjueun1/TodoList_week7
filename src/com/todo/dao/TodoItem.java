package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
	public String getId;
	private int is_completed;
	private String importance;
	private String time;

	
	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getGetId() {
		return getId;
	}

	public void setGetId(String getId) {
		this.getId = getId;
	}

	public TodoItem() {
		super();
	}


	public TodoItem(String title, String desc, String category, String due_date, String importance, String time){
        this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
        
        this.category = category;
        this.due_date = due_date;
        this.importance = importance;
        this.time = time;
    }
    
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}

	@Override
	public String toString() {
		String star = "";
		if(importance.equals("1")) {
			star = "  ★☆☆☆☆ ";
		}
		if(importance.equals("2")) {
			star = "  ★★☆☆☆ ";
		}
		if(importance.equals("3")) {
			star = "  ★★★☆☆ ";
		}
		if(importance.equals("4")) {
			star = "  ★★★★☆ ";
		}
		if(importance.equals("5")) {
			star = "  ★★★★★ ";
		}
		
		if(is_completed == 1) {
			String check = "  [V] ";
			return id + star + " [" + category + "]"+ title + " - "+ desc + " - " + due_date + " - " + current_date + " - 필요시간 " + time + "시간"+ check;
		}
		else return id + star + " [" + category + "]"+ title + " - "+ desc + " - " + due_date + " - " + current_date + " - 필요시간 " + time + "시간";
		
		
	}

	public String toSaveString() {
		return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "\n";
	}

	
}
