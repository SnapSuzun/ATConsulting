var currentPage = 0;
var recordsOnPage = 10;
var data;

function TestJava()
{
	var login = document.getElementById("login").value();
	var pass = document.getElementById("password").value();
	var ajax = new XMLHttpRequest();
	ajax.open('GET', 'http://127.0.0.1:8080/WebTest/login?username=' + login + '&password=' + pass, false);
	ajax.send();
}

function getDataFromServer () {
	var ajax = new XMLHttpRequest();
	ajax.open('GET', 'http://smart-route.ru:8100/adapter-web/rest/dictionary/c580d006-f86f-4bdd-84be-b51de6f626c6', false);
	ajax.send();
	var response = ajax.responseText;
	return response;
}
function parseData(){
	var json = getDataFromServer();;
	var data = JSON.parse(json);
	var arr = [];
	arr = data.documents;
	return arr;
}
function setStrings(){
	data = parseData();
	sortData();
	printData();
}

function printData()
{
	var j = 0;
	for (var i = recordsOnPage * currentPage; i < (recordsOnPage * currentPage + recordsOnPage) && i < data.length; i++, j++) {
		var id_s = "row" + j;
		var b = document.getElementById(id_s);
		a = document.createElement('p');
		a.id = id_s;
		a.innerHTML = (i+1) + ") " + data[i].fio + ", " + data[i].gender + ", " + data[i].birthDate + ", " + data[i].phone;
		var parent = document.getElementById("content3");
		if(b != null) parent.replaceChild(a,b);
		else parent.appendChild(a);
	};
	
	for(; j < recordsOnPage; j++)
	{
		var id_s = "row" + j;
		var b = document.getElementById(id_s);
		if(b != null)
		{
			b.parentNode.removeChild(b);
		}
		else break;
	}
}

function sortData(){
	
	data.sort(function(obj1, obj2)
	{
		if (obj1.fio < obj2.fio) return -1;
  		if (obj1.fio > obj2.fio) return 1;
  			return 0;
  	});
	
}

function searchValue(){
	currentPage = 0;
	data = parseData();
	
	sortData();
	
	var str = document.getElementById('textEdit1').value;
	str = str.toLowerCase();
	if(str.length == 0)
	{
		setStrings();
		return;
	}
	var array = [];
	for (var i = 0, j=0; i < data.length; i++) {
		var buff = data[i].fio.toLowerCase();
		if (buff.indexOf(str) != -1) {
			array[j] = data[i];
			j++;
		};
	}
	data = array;
	printData();
}

function previousPage(){
	if(currentPage == 0)
	{
		return;
	}
	var str = document.getElementById('textEdit1').value;
	currentPage--;
	if(str.length == 0)
		setStrings();
	else printData();
}

function nextPage(){
	if(recordsOnPage * (currentPage+1) > data.length)
	{
		return;
	}
	currentPage++;
	var str = document.getElementById('textEdit1').value;
	if(str.length == 0)
		setStrings();
	else printData();
}