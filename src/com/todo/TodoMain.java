package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean quit = false;
		//l.importData("todolist.txt");
		Menu.displaymenu();
		do {
			Menu.prompt();
			String choice = sc.next();
			switch (choice) {

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
				TodoUtil.listAll(l);
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
			
			case "ls_importance":
				System.out.println("중요도순 정렬 완료.");
				TodoUtil.listAll(l, "importance", 0);
				break;
			
			case "ls_importance_desc":
				System.out.println("역중요도순 정렬 완료.");
				TodoUtil.listAll(l, "importance", 1);
				break;
			
			case "ls_category_ver":
				System.out.println("카테고리 이름순 정렬 완료.");
				TodoUtil.listAll(l, "category", 1);
				break;
			
			case "ls_category_ver_desc":
				System.out.println("카테고리 역이름순 정렬 완료.");
				TodoUtil.listAll(l, "category", 0);
				break;
			
			case "comp":
				String index = sc.nextLine();
				TodoUtil.completeItem(l, index);
				break;
				
			case "comp_cancel":
				String index2 = sc.nextLine();
				TodoUtil.completeCancleItem(l, index2);
				break;
			
			case "ls_comp":
				System.out.println("완료항목 정렬 완료.");
				TodoUtil.compliteAll(l);
				break;
			
			case "help":
				Menu.displaymenu();
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
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
		} while (!quit);
		TodoList.closeConnection();
	}
}
