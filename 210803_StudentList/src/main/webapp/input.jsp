<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="contentInner">
	<div class="contents_title">
		학생정보 입력
	</div>
	<div class="wrap_information">
		<form>
			<table id="table_information">
				<tr>
					<th>NAME</th>
					<td><input type="text" name="sName" title="NAME" /></td>
				</tr>
				<tr>
					<th>TEL</th>
					<td><input type="text" name="sTel" title="TEL" /></td>
				</tr>
				<tr>
					<th>GRADE</th>
					<td><input type="text" name="sGrade" title="GRADE" /></td>
				</tr>
				<tr>
					<th>CLASS</th>
					<td><input type="text" name="sClass" title="CLASS" /></td>
				</tr>
				<tr>
					<td colspan="2" class="td_button">
						<input type="button" value="INSERT" onclick="doWhat(this.form, 'insert', true)" />
						<input type="hidden" name="cmd" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>