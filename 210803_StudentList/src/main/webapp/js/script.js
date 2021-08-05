/*
	script.js
 */

function doWhat(){
	var formObj = arguments[0];
	
	var pass = true;
	var notChk = ["BUTTON", "HIDDEN"];
	if(arguments[2]){
		for(var i=0;pass && i<formObj.length; i++){
			console.log(formObj[i]);
			if(notChk.indexOf(formObj[i].type.toUpperCase())<0){
				var inputValue = formObj[i].value.trim();
				if(!inputValue.length){
					pass = false;
					alert("입력 누락 : "+ formObj[i].title);
					formObj[i].focus();
				}
			}
		}
	}
	if(pass){
		formObj.cmd.value = arguments[1];
		formObj.submit();
	}
}
function doSearch(){
	var formObj = arguments[0];
	var pass = true;
	var searchby = formObj[0].value;
	var searchtext = formObj[1].value;
	console.log(searchtext);
	if(searchby == "ID"){
		if (!isNumber(searchtext)){
			pass = false;
			alert("ID는 숫자로만 입력!!")
		}
	}else if(searchby == "Name"){
		if(searchtext.length < 2){
			pass = false;
			alert("Name은 2글자 이상 입력!!");
		}
	}
	if(pass){
		formObj.cmd.value = arguments[1];
		formObj.submit();
	}
}

function isNumber(n) {
	return !isNaN(parseFloat(n)) && !isNaN(n - 0)
	}
