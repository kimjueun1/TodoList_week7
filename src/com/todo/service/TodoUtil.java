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

		String category, title, desc, due_date;
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
		
		System.out.print("마감시간 입력 >");
		due_date = sc.nextLine().trim();

		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(l.addItem(t) > 0)
			System.out.println("추가 완료. ");
	}

	public static void deleteItem(TodoList l) {

		Scanner sc = new Scanner(System.in);


		System.out.print("\n"
				+ "---------항목 삭제---------\n"
				+ "삭제할 번호를 입력 > ");
		int index = sc.nextInt();
		if(l.deleteItem(index)>0)
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
		
		System.out.print("새로운 마감시간 >");
		String new_due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("수정 완료");
		

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
	
	
	
//	public static void ls_cate(TodoList l) {
//		HashSet<String> set = new HashSet<String>();
//		for (TodoItem item : l.getList()) {
//			set.add(item.getCategory());
//		}
//		
//		Iterator iter = set.iterator();	
//		while(iter.hasNext()) {
//		    System.out.print(iter.next());
//		    if(iter.hasNext()) System.out.print(" / ");
//		}
//		System.out.println();
//		System.out.println("총 "+set.size()+"개의 카테고리 존재");
//	}

	public static void loadList(TodoList l, String filename ) {


		try {
			int count=0;
			
			BufferedReader br  = new BufferedReader(new FileReader(filename));
			String oneline;

			while((oneline = br.readLine()) != null) {
				//System.out.println(oneline);
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String content = st.nextToken();
				String due_date = st.nextToken();
				String date = st.nextToken();
				TodoItem item = new TodoItem(title, content, category, due_date);
				item.setCurrent_date(date);
				l.addItem(item);	
				count++;
			}
			if(count ==0) {
				System.out.println("불러올 항목이 없습니다.");
			}
			else {
				System.out.println(count+"개의 항목을 불러왔습니다.");
			}
			br.close();
		}
		catch (IOException e) {
			System.out.println("파일을 찾을 수 없음.");
		}
	}

	public static void saveList(TodoList l, String filename ) {


		try {

			Writer w = new FileWriter(filename);

			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}

			w.close();

			System.out.println("모든 내용 저장 완료.");
		} catch (IOException e) {
		}

	}
	
//	public static void find(TodoList l, String word) {
//		int count=0, count2=0;
//		for (TodoItem item : l.getList()) {
//			count++;
//			if (item.getTitle().contains(word) || item.getDesc().contains(word)) {
//				System.out.println(count+"."+item.toString());
//				count2++;
//			
//			}
//		}
//		System.out.println("총 "+count2+"개의 항목 찾음");
//	}
	
//	public static void find_cate(TodoList l, String cate) {
//		int count=0, count2=0;
//		for (TodoItem item : l.getList()) {
//			count++;
//			if (item.getCategory().contains(cate)) {
//				System.out.println(count+"."+item.toString());
//				count2++;
//			}
//		}
//		System.out.println("총 "+count2+"개의 항목 찾음");
//	}
	
	public static void findList(TodoList l, String keyword) {
		int count =0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾음.\n", count);
	}
	
	
}
