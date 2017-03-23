if (js == null || js == undefined) {var js = {};}
if (js.util == null || js.util == undefined) {js.util = {};}
if (JSUtil == null || JSUtil == undefined) {var JSUtil = js.util;}

//只允许输入数字
//兼容IE , Firefox 
//author:AllenZhang
js.util.numberAllowed = function(e) {
	var key = window.event ? e.keyCode : e.which;
	//IE
	if (window.event) {
		if (key < 48 || key > 57) {
			if (e.preventDefault != null && typeof(e.preventDefault) == 'function') {
				e.preventDefault ();
			} else {
				e.returnValue = false;
			}
		}
	}
	//FireFox
	else {
		if ((key != 0) && (key != 8) && (key < 48 || key > 57)) {
			e.preventDefault();
		}
	}
}
//只允许输入数字和小数点
js.util.numberAndPointAllowed = function(e) {
	var key = window.event ? e.keyCode : e.which;
	//IE
	if (window.event) {
		if ((key < 48 || key > 57) && key != 46) {
			if (e.preventDefault != null && typeof(e.preventDefault) == 'function') {
				e.preventDefault ();
			} else {
				e.returnValue = false;
			}
		}
	}
	//FireFox
	else {
		if ((key != 0) && (key != 8) && (key != 46) && (key < 48 || key > 57)) {
			e.preventDefault();
		}
	}
}
//不允许输入空格
js.util.notAllowSpace = function(e) {
	var key = window.event ? e.keyCode : e.which;
	//IE
	if (window.event) {
		if (key == 32) {
			if (e.preventDefault != null && typeof(e.preventDefault) == 'function') {
				e.preventDefault ();
			} else {
				e.returnValue = false;
			}
		}
	}
	//FireFox
	else {
		if (key == 32) {
			e.preventDefault();
		}
	}
}
//生成变化参数
js.util.getVariableParameter = function () {
	var theday = new Date();
	var theyear = theday.getFullYear();
	var themonth = theday.getMonth()+1;
	var thedays = theday.getDate();
	var thehours = theday.getHours();
	var theminutes = theday.getMinutes();
	var theseconds = theday.getSeconds();
	var customRandom = Math.random();
	return (theyear+"-"+themonth+"-"+thedays+"-"+thehours+"-"+theminutes+"-"+theseconds+"-"+customRandom);
}


// var reg = /^[_]+$/i;                            //只输入下划线
// //var reg_num = /^[0-9a-zA-Z]{1,10}$/;          //只输入数字和字母的正则（1到10位）
// var reg_num = /^[0-9a-zA-Z]+$/i;                //只输入数字和字母的正则
// //var reg_chinese = /^[\u4e00-\u9fa5]{1,10}$/;  //只输入汉字的正则（1到10位）
// var reg_chinese = /^[\u4e00-\u9fa5]+$/i;        //只输入汉字的正则

//判断一个字符串是否为字母、数字、下划线、汉字或其组合
//Author: AllenZhang
js.util.isChineseOrNumberOrLetter = function (value) {
	var result = true;
	if (value == null || value == undefined) {
		result = false;
	}
	else if (value.value == "" || value.replace(/^\s+|\s+$/g,"").length == 0) {
		result = false;
	}
	else {
		var reg = /^[_]+$/i;
		var reg_numOrLetter = /^[0-9a-zA-Z]+$/i;
		var reg_chinese = /^[\u4e00-\u9fa5]+$/i;
		for (var k = 0 ; k < value.length ; k ++ ) {
			var tempValue = value.charAt(k);
			if (!(reg.test(tempValue) || reg_numOrLetter.test(tempValue) || reg_chinese.test(tempValue))) {
				result = false;
				break;
			}
		} 
	}
	return result;
}

//文件下载
js.util.downloadFile = function(file,newFileName,absolutePath) {
	if (newFileName) {
		if (absolutePath) {
			window.self.location.href = ctx+'/fileDownload?file='+encodeURI(file)+'&newFileName='+encodeURI(newFileName)+'&absolutePath=1';
		} else {
			window.self.location.href = ctx+'/fileDownload?file='+encodeURI(file)+'&newFileName='+encodeURI(newFileName);
		}
	} else {
		if (absolutePath) {
			window.self.location.href = ctx+'/fileDownload?file='+encodeURI(file)+'&absolutePath=1';
		} else {
			window.self.location.href = ctx+'/fileDownload?file='+encodeURI(file);
		}
	}
}

// 获取绝对位置方法 开始 (2013-05-09 修改，修改内容：增加了window参数;处理框架iframe上，中，下结构的特殊情况,原始版本位于：jquery_toolbox.js) **********************
var isIE=navigator.userAgent.toLowerCase().indexOf("msie")!=-1;   
var isIE6=navigator.userAgent.toLowerCase().indexOf("msie 6.0")!=-1;   
var isIE7=navigator.userAgent.toLowerCase().indexOf("msie 7.0")!=-1&&!window.XDomainRequest;   
var isIE8=!!window.XDomainRequest;   
var isGecko=navigator.userAgent.toLowerCase().indexOf("gecko")!=-1;
var isQuirks=document.compatMode=="BackCompat";   
  
//跨Iframe计算绝对坐标
js.util.getPositionCrossIframe = function(element,win,popLevel) {
	popLevel = popLevel || 2;//需要去除的级别（从top开始）
	var levelsY = new Array ();//循环iframe的Y坐标
	if (typeof element !== "object") {
		alert("温馨提示：参数需要对象，而不是对象的Id，请确认！");
		return {x:0,y:0,h:0,w:0};
	}
	var position = JSUtil.getPositionInIframe(element);
	win = win || window;
	var scrollLeft, scrollTop;
	while(win != win.parent){
		if(win.frameElement){
			var positionInner = JSUtil.getPositionInIframe(win.frameElement);
			position.x += positionInner.x;
			position.y += positionInner.y;
			levelsY.push(positionInner.y);
		}
		scrollLeft = Math.max(win.document.body.scrollLeft, win.document.documentElement.scrollLeft);
		scrollTop = Math.max(win.document.body.scrollTop, win.document.documentElement.scrollTop);
		position.x -= scrollLeft;
		position.y -= scrollTop;
		win = win.parent;
	}
	position.h = element.offsetHeight;
	position.w = element.offsetWidth;
	position.y += position.h;
	try {
		var _win_height = parent.getCustomWinHeight();
		if (position.y * 2 > _win_height) {
			position.y = position.y - position.h - 210 - 36;
		}
	}
	catch(e) {
	}
	//处理需要去除的级别的Y坐标
	if (popLevel > 0 && levelsY.length >0) {
		for (var i = levelsY.length-1; i>=0; i--) {
			position.y = position.y - levelsY[i];
			popLevel--;
			if (popLevel == 0) {
				break;
			}
		}
	}
	return position;
};   
  
//不跨Iframe计算绝对坐标（一般不直接使用，计算位置请使用：getPositionCrossIframe函数）
js.util.getPositionInIframe = function(element){
	if (typeof element !== "object") {
		alert("温馨提示：参数需要对象，而不是对象的Id，请确认！");
		return {x:0,y:0,h:0,w:0};
	}
	var k=element.ownerDocument;
	if(element.parentNode===null||element.style.display=="none"){
		return false;
	}
	var l=null;
	var j=[];
	var g;
	if(element.getBoundingClientRect){
		g=element.getBoundingClientRect();
		var c=Math.max(k.documentElement.scrollTop,k.body.scrollTop);
		var d=Math.max(k.documentElement.scrollLeft,k.body.scrollLeft);
		var b=g.left+d-k.documentElement.clientLeft;
		var a=g.top+c-k.documentElement.clientTop;
		if(isIE){
			b--;
			a--;
		}
		return {x:b,y:a,h:element.offsetHeight,w:element.offsetWidth};
	}
	else{   
		if(k.getBoxObjectFor){   
			g=k.getBoxObjectFor(m);   
			var h=(element.style.borderLeftWidth)?parseInt(element.style.borderLeftWidth):0;   
			var f=(element.style.borderTopWidth)?parseInt(element.style.borderTopWidth):0;   
			j=[g.x-h,g.y-f];    
		}   
	}   
	if(element.parentNode){
		l=element.parentNode;
    }
    else{   
		l=null;
	}   
	while(l&&l.tagName!="BODY"&&l.tagName!="HTML"){   
		j[0]-=l.scrollLeft;   
		j[1]-=l.scrollTop;   
		if(l.parentNode){   
		l=l.parentNode;
		}
		else{   
			l=null;
		}   
	}
	return{x:j[0],y:j[1],h:element.offsetHeight,w:element.offsetWidth};
 };   
// 获取绝对位置方法 结束 *******************************************************************************************************************************

//即时时间显示
function clockon(){
	var now = new Date();
	var year = now.getYear();
	var month = now.getMonth();
	var date = now.getDate();
	var day = now.getDay();
	var hour = now.getHours();
	var minu = now.getMinutes();
	var sec = now.getSeconds();
	var week;
	month = month+1;
	if(month<10)month="0"+month;
	if(date<10)date="0"+date;
	if(hour<10)hour="0"+hour;
	if(minu<10)minu="0"+minu;
	if(sec<10)sec="0"+sec;
	var arr_week = new Array("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
	week = arr_week[day];
	var time = "";
	time = year+"年"+month+"月"+date+"日"+hour+"时"+minu+"分"+sec+"秒";
	if(navigator.userAgent.indexOf("Firefox")>0){
	time = (year+1900)+"年"+month+"月"+date+"日"+hour+"时"+minu+"分"+sec+"秒";
	document.getElementById('contentDate').innerHTML=time;
	}
	else{
	time = year+"年"+month+"月"+date+"日"+hour+"时"+minu+"分"+sec+"秒";
	document.getElementById('contentDate').innerHTML=time;
	}
	var timer = setTimeout("clockon()",200);
}