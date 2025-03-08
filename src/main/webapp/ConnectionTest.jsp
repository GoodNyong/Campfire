<%@page import="util.JDBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>연결 테스트</title>
</head>
<body>
<%
	JDBConnect jdbc1 = new JDBConnect(application);
	jdbc1.close();

	
	/*MemberDao dao = new MemberDao(application);
	Member m = dao.selectMember("asdf");
	System.out.println(m);
	
	/* BoardDao dao = new BoardDao(application);
	Map<String, String> map = new HashMap<>();
	
	map.put("searchField", "title");
	map.put("searchWord", "제목2");
	
	int cnt = dao.selectCount(map);
	out.print(cnt);
	
	out.println("<br>");
	ArrayList<Board> blist = dao.selectList(map);
	out.println(blist); */
	
	/* MemberDao dao = new MemberDao(application);
	int cnt = dao.selectCount("asdf");
	out.println(cnt);
	
	dao.close(); */
%>
</body>
</html>