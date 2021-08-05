<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="kr.ac.green.dto.Student" %>
<div class="contentInner contentList">
	<div class="contents_title">학생 리스트</div>
	<div class="input_row">
		<form>
			<div class="search">
				<label for="search_by">검색 : </label>
				<select name="searchby" id="search_by">
					<option value="ID">ID</option>
					<option value="Name">Name</option>
					<option value="Class">Class</option>
				</select>
				<label for="search_text">내용 : </label>
				<input type="text" name="searchtext" autocomplete="off" id="search_text" />
				<input type="button" value="검색" onclick="doSearch(this.form, 'searchList')" />
				<input type="hidden" name="cmd" />
			</div>
		</form>
	</div>
	<div class="wrap_studentList">
		<table id="table_studentList">
			<thead>
				<tr>
					<th id="tId">ID</th>
					<th id="tName">Name</th>
					<th id="tTel">Tel</th>
					<th id="tGrade">Grade</th>
					<th id="tClass">Class</th>
				</tr>
			</thead>
			<tbody>
			<%
				Student[] list = (Student[]) request.getAttribute("list");
				System.out.println(list.length);
				if(list == null || list.length == 0){
			%>
				<tr>
					<td colspan="5">no data</td>
				</tr>
			<%
				}else{
					for(int i=0;i<list.length;i++){
						Student student = list[i];
			%>
				<tr>
					<td><%=student.getSid() %></td>
					<td><%=student.getSname() %></td>
					<td><%=student.getStel() %></td>
					<td><%=student.getSgrade() %></td>
					<td><%=student.getSclass() %></td>
				</tr>
			<%
					}
				}
			%>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5" class="rows">
						<%= list.length %> row(s)
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
