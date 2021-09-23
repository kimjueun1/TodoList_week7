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

public class TodoUtil {

	public static void createItem(TodoList list) {

		String title, desc;
		Scanner sc = new Scanner(System.in);

		System.out.println("\n"
				+ "---------항목 추가---------\n"
				+ "제목 입력 > \n");

		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목 중복!");
			return;
		}
		sc.nextLine();

		System.out.println("내용 입력 > ");
		desc = sc.nextLine().trim();

		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("추가 완료. ");
	}

	public static void deleteItem(TodoList l) {

		Scanner sc = new Scanner(System.in);


		System.out.println("\n"
				+ "---------항목 삭제---------\n"
				+ "삭제할 제목을 입력 > ");

		String title = sc.next();

		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("삭제 완료. ");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.println("\n"
				+ "---------항목 수정---------\n"
				+ "수정할 항목 제목 > ");

		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("제목이 존재하지 않음.");
			return;
		}

		System.out.println("새로운 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목 중복!");
			return;
		}

		System.out.println("새로운 제목 > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정 완료.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("---------LIST---------");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}

	public static void loadList(TodoList l, String filename ) {


		try {
			int count=0;
			
			BufferedReader br  = new BufferedReader(new FileReader(filename));
			String oneline;

			while((oneline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String name = st.nextToken();
				String content = st.nextToken();
				String time = st.nextToken();
				TodoItem item = new TodoItem(name, content);
				item.setCurrent_date(time);
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
}
