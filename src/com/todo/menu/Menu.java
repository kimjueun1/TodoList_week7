package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("---------------메뉴---------------");
        System.out.println("1. [add] 항목 추가");
        System.out.println("2. [del] 항목 삭제");
        System.out.println("3. [edit] 항목 수정");
        System.out.println("4. [ls] 전체 리스트");
        System.out.println("5. [ls_name_asc] 리스트 제목순 정렬");
        System.out.println("6. [ls_name_desc] 리스트 역제목순 정렬");
        System.out.println("7. [ls_date] 리스트 날짜순 정렬");
        System.out.println("8. [help] 도움말 - 메뉴 출력");
        System.out.println("9. [exit] 종료");
    }
    public static void prompt() {
    	System.out.println("\n메뉴입력 > ");
    }
}
