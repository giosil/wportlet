function JRPC(s){
	this.urlEndPoint=s;this.authUserName=null;this.authPassword=null;this.callId=0;
}
JRPC.prototype.upgradeValues=function(obj){
	var matches,useHasOwn={}.hasOwnProperty ? true : false;
	for(var key in obj){
	    if(!useHasOwn || obj.hasOwnProperty(key)){
	        if(typeof obj[key]=='string') {
	            if(matches=obj[key].match(/(\d\d\d\d)-(\d\d)-(\d\d)T(\d\d):(\d\d):(\d\d)\.(\d\d\d)./)){
	                obj[key]=new Date(0);
	                if(matches[1])obj[key].setUTCFullYear(parseInt(matches[1]));
	                if(matches[2])obj[key].setUTCMonth(parseInt(matches[2])-1);
	                if(matches[3])obj[key].setUTCDate(parseInt(matches[3]));
	                if(matches[4])obj[key].setUTCHours(parseInt(matches[4]));
	                if(matches[5])obj[key].setUTCMinutes(parseInt(matches[5]));
	                if(matches[6])obj[key].setUTCSeconds(parseInt(matches[6]));
	                if(matches[7])obj[key].setUTCMilliseconds(parseInt(matches[7]));
	            }
	            else if(matches=obj[key].match(/^\/Date\((\d+)\)\/$/)){
	                obj[key]=new Date(parseInt(matches[1]));
	            }
	        }
	        else if(obj[key] instanceof Object){
	        	this.upgradeValues(obj[key]);
	        }
	    }
	}
}
JRPC.prototype.beforeExecute=function(m){}
JRPC.prototype.afterExecute=function(m){}
JRPC.prototype.execute=function(methodName,params,successHandler,exceptionHandler){
	this.callId++;
	var request,postData;
	request={version:"2.0",method:methodName,id:this.callId};
	if(params)request.params=params;
	postData=JSON.stringify(request);
	var xhr=null;
	if(window.XMLHttpRequest)
	    xhr=new XMLHttpRequest();
	else
	if(window.ActiveXObject){
	    try{xhr=new ActiveXObject('Msxml2.XMLHTTP');}catch(err){xhr=new ActiveXObject('Microsoft.XMLHTTP');}
	}
	this.beforeExecute(methodName);
	var _this=this;
	xhr.open('POST',this.urlEndPoint,true,this.authUserName,this.authPassword);
	xhr.setRequestHeader('Content-Type','application/json');
	xhr.setRequestHeader('Accept','application/json');
	xhr.send(postData);
	xhr.onreadystatechange=function(){
	    if(xhr.readyState==4){
	    	_this.afterExecute(methodName);
	        var response=JSON.parse(xhr.responseText);
	        if(response.error !== undefined){
	        	if(typeof exceptionHandler=='function'){
	            	exceptionHandler(response.error);
	        	}
	        }
	        else
	        if(typeof successHandler=='function'){
	            var result=response.result;
	            _this.upgradeValues(result);
	            successHandler(result);
	        }
	    }
	};
}
jrpc=new JRPC("/demo/rpc");