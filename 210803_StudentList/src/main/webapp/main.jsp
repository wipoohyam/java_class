<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="kr.ac.green.dto.Student" %>
<%@ page import="kr.ac.green.dao.StudentDao" %>
<%
	request.setCharacterEncoding("utf-8");

	String cmd = request.getParameter("cmd");
	
	if(cmd==null){
		cmd = "list";
	}
	StudentDao dao = StudentDao.getInstance();
	String nextPage = "";
	boolean isRedirect = false;
	if(cmd.equals("list")){
		Connection con = dao.connect();
		Student[] list = dao.getAll(con);
		dao.disconnect(con);
		request.setAttribute("list", list);
		nextPage = "list";
	}else if(cmd.equals("searchList")){
		String searchby = request.getParameter("searchby");
		String searchtext = request.getParameter("searchtext");
		Student[] list = null;
		
		Connection con = dao.connect();
		if(searchby.equals("ID")){
			int sid = Integer.parseInt(searchtext);
			Student student = dao.getById(con, sid);
			if(student != null){
				list = new Student[1];
				list[0] = student;
			}else{
				list = new Student[0];
			}
		}else if(searchby.equals("Name")){
			list = dao.getByName(con, searchtext);
		}else if(searchby.equals("Class")){
			list = dao.getByClass(con, searchtext);
		}
		dao.disconnect(con);
		
		request.setAttribute("list", list);
		nextPage = "list";
	}else if(cmd.equals("input")){
		nextPage = "input";
	}else if(cmd.equals("insert")){
		isRedirect = true;
		
		String sname = request.getParameter("sName");
		String stel = request.getParameter("sTel");
		int sgrade = Integer.parseInt(request.getParameter("sGrade"));
		String sclass = request.getParameter("sClass");
		
		Student student = new Student(sname,stel,sgrade,sclass);

		Connection con = dao.connect();
		dao.insert(con, student);
		dao.disconnect(con);
		
		nextPage = "list";
	}else if(cmd.equals("modify")){
		nextPage = "modifySearch";
	}else if(cmd.equals("searchModify")){
		String searchtext = request.getParameter("searchtext");
		int sid = Integer.parseInt(searchtext);
		
		Connection con = dao.connect();
		Student student = dao.getById(con, sid);
		dao.disconnect(con);
		if(student != null){
			request.setAttribute("target", student);
			nextPage = "modify";
		}else{
			nextPage = "noData";
		}
	}else if(cmd.equals("doModify")){
		isRedirect = true;
		
		int sid = Integer.parseInt(request.getParameter("sId"));
		String sname = request.getParameter("sName");
		String stel = request.getParameter("sTel");
		int sgrade = Integer.parseInt(request.getParameter("sGrade"));
		String sclass = request.getParameter("sClass");
		
		Student student = new Student(sid, stel, sgrade, sclass);

		Connection con = dao.connect();
		dao.update(con, student);
		dao.disconnect(con);
		
		nextPage = "list"; 
	}else if(cmd.equals("delete")){
		nextPage = "deleteSearch";
	}else if(cmd.equals("searchDelete")){
		String searchtext = request.getParameter("searchtext");
		int sid = Integer.parseInt(searchtext);
		
		Connection con = dao.connect();
		Student student = dao.getById(con, sid);
		dao.disconnect(con);
		if(student != null){
			request.setAttribute("target", student);
			nextPage = "delete";
		}else{
			nextPage = "noData";
		}
	}else if(cmd.equals("doDelete")){
		isRedirect = true;
		
		int sid = Integer.parseInt(request.getParameter("sId"));

		Connection con = dao.connect();
		dao.delete(con, sid);
		dao.disconnect(con);
		
		nextPage = "list"; 
	}
	System.out.println("next:"+nextPage+"("+cmd+")");
	
	if(isRedirect){
		response.sendRedirect("main.jsp?cmd="+nextPage);
	}else{
		request.getRequestDispatcher("template.jsp?cmd="+nextPage).forward(request, response);	
	}
%>