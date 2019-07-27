$(document).ready(function(){
	//截取路径中?后面的内容
	var str = location.href.substr(location.href.indexOf('?'+1));
	var arr = str.split('&');
	var aid;
	var cid;
	for(var i=0;i<arr.length;i++){
		if(arr[i].indexOf('aid')>-1){
			aid = arr[i].substr(arr[i].indexOf('=')+1);
		}
		if(arr[i].indexOf('cid')>-1){
			cid = arr[i].substr(arr[i].indexOf('=')+1);
		}
	}
	
	//初始化：更改选手的热度
	$.ajax({
		url:'updateCandidateHot/'+cid,
		type:'post',
		data:{},
		dataType:'json',
		success:function(data){
			console.log(data);
		}
	});
	
	
	//初始化：查询选手信息
		$.ajax({
			url:'selectCandidateById/'+cid,
			type:'post',
			data:{},
			dataType:'json',
			success:function(data){
				console.log(data);
				
		}
	});
	
	
	
})
