<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.ac.green.dto.Student" %>
<%
	Student student = (Student) request.getAttribute("target");
%>
<div class="contentInner">
	<div class="contents_title">
		학생정보 수정
	</div>
	<div class="wrap_information">
		<form>
			<table id="table_information">
				<tr>
					<th>ID</th>
					<td><input type="text" name="sId" value="<%= student.getSid() %>" readonly /></td>
				</tr>
				<tr>
					<th>NAME</th>
					<td><input type="text" name="sName" value="<%= student.getSname() %>" readonly /></td>
				</tr>
				<tr>
					<th>TEL</th>
					<td><input type="text" name="sTel"  value="<%= student.getStel() %>" readonly /></td>
				</tr>
				<tr>
					<th>GRADE</th>
					<td><input type="text" name="sGrade"  value="<%= student.getSgrade() %>" readonly /></td>
				</tr>
				<tr>
					<th>CLASS</th>
					<td><input type="text" name="sClass"  value="<%= student.getSclass() %>" readonly /></td>
				</tr>
				<tr>
					<td colspan="2" class="td_button">
						<input type="button" value="DELETE" onclick="doWhat(this.form,'doDelete', true)" />
						<input type="hidden" name="cmd" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>