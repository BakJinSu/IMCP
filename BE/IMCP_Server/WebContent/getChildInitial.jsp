<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Parent.ParentDAO" %>
<%
	request.setCharacterEncoding("UTF-8");
	String childKey = request.getParameter("chlidKey");    //  아이 식별값
	
	ParentDAO parentDAO = new ParentDAO();

	out.clear();
	out.print(parentDAO.getChildInitial(childKey));
	out.flush();
%>
