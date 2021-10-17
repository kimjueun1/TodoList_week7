package com.todo.service;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import java.util.HashSet;

public class TodoUtil {

	public static void createItem(TodoList l) {

		String category, title, desc, due_date, importance, time;
		Scanner sc = new Scanner(System.in);

		System.out.print("\n"
				+ "---------항목 추가---------\n"
				+ "카테고리 입력 > ");

		category = sc.next();
		sc.nextLine();

		System.out.print("제목 입력 > ");

		title = sc.nextLine().trim();

		if (l.isDuplicate(title)) {
			System.out.printf("제목 중복!");
			return;
		}

		System.out.print("내용 입력 > ");
		desc = sc.nextLine().trim();

		System.out.print("마감시간 입력(YY/MM/DD) >");
		due_date = sc.nextLine().trim();

		System.out.print("중요도 입력(1-5) > ");
		importance = sc.nextLine().trim();

		System.out.print("예상 소요시간 입력(단위:시) > ");
		time = sc.nextLine().trim();


		TodoItem t = new TodoItem(title, desc, category, due_date, importance, time);
		t.setIs_completed(0);
		if(l.addItem(t) > 0)
			System.out.println("추가 완료. ");
	}

	public static void deleteItem(TodoList l) {

		Scanner sc = new Scanner(System.in);


		System.out.print("\n"
				+ "---------항목 삭제---------\n"
				+ "삭제할 번호를 입력(,로 중복 가능) > ");

		String str = sc.nextLine().trim();
		StringTokenizer st = new StringTokenizer(str, " ,");
		int index = 0;

		while(st.hasMoreTokens()) {
			index = Integer.parseInt(st.nextToken());
			l.deleteItem(index);
		}

		if(l.deleteItem(index)>=0)
			System.out.println("삭제 완료");
	}


	public static void updateItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.print("\n"
				+ "---------항목 수정---------\n"
				+ "수정할 항목 번호 > ");


		int index = sc.nextInt();


		System.out.print("새로운 카테고리 >");
		String new_category = sc.next().trim();
		sc.nextLine();


		System.out.print("새로운 제목 > ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목 중복!");
			return;
		}

		System.out.print("새로운 내용 > ");
		String new_description = sc.nextLine().trim();

		System.out.print("새로운 마감시간(YY/MM/DD) >");
		String new_due_date = sc.next().trim();

		System.out.print("새로운 중요도 입력(1-5) > ");
		String new_importance = sc.next().trim();

		System.out.print("새로운 예상 소요시간 입력(단위:시) > ");
		String new_time = sc.next().trim();

		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date, new_importance, new_time);
		t.setId(index);
		//t.setIs_completed(t.getIs_completed());
		if(l.updateItem(t) > 0)
			System.out.println("수정 완료");

	}
	//////////////////
	public static void completeItem(TodoList l, String index) {
		TodoItem t = new TodoItem();
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "---------항목 체크---------\n"
				+ "체크할 번호를 입력(,로 중복 가능) > ");
		String str = sc.nextLine().trim();
		
		StringTokenizer st = new StringTokenizer(str, " ,");
		int indexNum=0;
		while(st.hasMoreTokens()) {
			indexNum = Integer.parseInt(st.nextToken());
			t.setId(indexNum);
			t.setIs_completed(1);
			l.completeItem(indexNum);
		}
		
		if(l.completeItem(indexNum) >= 0)
			System.out.println("체크 완료");
	}

	public static void completeCancleItem(TodoList l, String index) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "---------항목 체크취소---------\n"
				+ "체크취소할 번호를 입력(,로 중복 가능) > ");
		
		String str = sc.nextLine().trim();
		
		StringTokenizer st = new StringTokenizer(str, " ,");
		int indexNum=0;
		while(st.hasMoreTokens()) {
			indexNum = Integer.parseInt(st.nextToken());
			
			for(TodoItem item : l.getList(indexNum)) {
				if(item.getIs_completed()==1) {
					item.setIs_completed(0);
					l.completeCancleItem(indexNum);
				}
				else {
					item.setIs_completed(1);
					l.completeItem(indexNum);
				}
				//System.out.println(item.toString());
			}
		}
		if(l.completeCancleItem(indexNum) >= 0)
			System.out.println("체크항목 실행 취소 완료");
	}
 
	public static void compliteAll(TodoList l) {
		int count =0;
		for (TodoItem item : l.getCompList()) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목이 완료.\n", count);
	}
	////////////////

	public static void listAll(TodoList l) {
		System.out.printf("[전체목록, 총 %d개]\n", l.getCount());
		for(TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체목록, 총 %d개]\n", l.getCount());
		for(TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}

	public static void listCateAll(TodoList l) {
		int count=0;
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 들어 있습니다\n", count);

	}

	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다\n", count);

	}

	public static void findList(TodoList l, String keyword) {
		int count =0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾음.\n", count);
	}
}
