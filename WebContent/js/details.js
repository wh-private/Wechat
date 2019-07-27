$(document).ready(function(){
	//截取路径中?后面的内容
	var str = location.href.substr(location.href.indexOf('?')+1);
	var arr = str.split('&');
	var aid;
	var cid;
	//截取aid,cid
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
			alert("选手热度");
			console.log(data);
		}
	});
	
	
	//初始化：查询选手信息
	alert("选手信息")
		$.ajax({
			url:'selectCandidateById/'+cid,
			type:'post',
			data:{},
			dataType:'json',
			success:function(data){
				
				console.log(data);
				$("#imgurl").attr("src","img/"+data[0].imgurl);
				$("#cname").html(data[0].name);
				$("#cid").html(data[0].cid);
				$("#tickets").html(data[0].tickets);
				$("#hots").html(data[0].hots);
				$("#gifts").html(data[0].gifts);
				$("#cdeclaration").html(data[0].declaration);
				var images = data[0].images;
				//不知道有几个关联图片,循环   路径是tomcat下的路径
				if(images.length>0){
					for(var i=0;i<images.length;i++){
						$("#div2").append("<img src='img/"+images[i].imgurl+"' >")
					}
				}
		}
	});
	
	//回首页
	$("#gohome").click(function(){
		location.href="index.html?aid="+aid;
	})
	
	//投票功能
	$("#vote").click(function(){
		$.ajax({
			url:'voteCandidate/'+cid+"/"+aid,
			type:'post',
			data:{},
			dataType:'json',
			success:function(data){
				console.log(data);
				$("#tickets").html(parseInt($("#tickets").html())+1);
				alert("投票成功");
			}
		});
	})
	
	
	$("#gift").click(function(){
		window.location.href="gift.html?aid="+aid+"&cid="+cid
		
		
	})
})








