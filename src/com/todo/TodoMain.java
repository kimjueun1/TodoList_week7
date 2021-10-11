package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		//l.importData("todolist.txt");
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {
			
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);	
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				isList = true;
				break;
			
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;

			case "ls_name":
				System.out.println("이름순 정렬 완료.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("역 이름순 정렬 완료.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순 정렬 완료.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("역날짜순 정렬 완료.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
			
			case "help":
				Menu.displaymenu();
				break;
				
			
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l, cate);
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("정확한 메뉴명을 입력하세요. 도움말-help");
				break;
			}
			
			//if(isList) TodoUtil.listAll(l);
		} while (!quit);
		//TodoUtil.saveList(l, "todolist.txt");
		TodoList.closeConnection();
	}
}
