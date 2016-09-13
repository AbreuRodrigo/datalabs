var creationYear = 2016;
var currentYear = new Date().getFullYear();

$(function(){
	fixCopyrightYear();
});

function fixCopyrightYear() {	
	var copyright = $("footer p#copyright");	
	copyright.text("\xA9 Copyright DataLab " + (currentYear > creationYear ? (creationYear + " - " + currentYear) : creationYear) + " " + copyright.text());
}