<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="contentInner contentDelete">
	<div class="contents_title">학생정보 삭제</div>
	<div class="input_row">
		<form>
			<div class="search left">
				<label for="search_by">검색 : </label>
				<select name="searchby"
					id="search_by">
					<option value="ID">ID</option>
					<option value="Name">Name</option>
					<option value="Class">Class</option>
				</select>
				<label for="search_text">내용 : </label>
				<input type="text" name="searchtext" id="search_text" autocomplete="off" />
				<input type="button" value="검색" onclick="doSearch(this.form, 'searchDelete')" />
				<input type="hidden" name="cmd" />
			</div>
		</form>
	</div>
</div>
