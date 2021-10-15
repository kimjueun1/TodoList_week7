package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("---------------메뉴---------------");
        System.out.println("[add] 항목 추가");
        System.out.println("[del] 항목 삭제");
        System.out.println("[edit] 항목 수정");
        System.out.println("[ls] 전체 리스트");
        System.out.println("[ls_cate] 카테고리 리스트");
        System.out.println("[ls_name] 리스트 제목순 정렬");
        System.out.println("[ls_name_desc] 리스트 역제목순 정렬");
        System.out.println("[ls_date] 리스트 날짜순 정렬");
        System.out.println("[ls_date_desc] 리스트 역날짜순 정렬");
        System.out.println("[ls_importance] 리스트 중요도순 정렬");
        System.out.println("[ls_importance_desc] 리스트 역중요도순 정렬");
        System.out.println("[ls_duedate] 리스트 마감순 정렬");
        System.out.println("[ls_duedate_desc] 리스트 역마감순 정렬");
        System.out.println("[comp] 완료 체크");
        System.out.println("[comp_cancel] 완료 체크 해제");
        System.out.println("[ls_comp] 완료 항목 리스트");
        System.out.println("[help] 도움말 - 메뉴 출력");
        System.out.println("[find] 제목&내용 검색");
        System.out.println("[find_cate] 카테고리 검색");
        System.out.println("[exit] 종료");
    }
    public static void prompt() {
    	System.out.print("\n메뉴입력 > ");
    }
}
