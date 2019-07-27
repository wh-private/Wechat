$(document).ready(function(){
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
	
	
	//根据活动查礼物信息
	$.ajax({
		url:'selectGiftsByAid/'+aid,
		type:'post',
		data:{},
		dataType:'json',
		success:function(data){
			console.log(data);
			for(var i=0;i<data.length;i++){
				$("#div1").append("<div data-id='"+data[i].gid+"' class='g_data'><img src='img/"+data[i].imgurl+"' /><p>"+data[i].giftname+"</p><p>点数:+"+data[i].point+"</p><p>价钱："+data[i].price+"</p></div>")
			}
		}
	});
	//送的时候点的效果
	var gid;
	$(document).on("click",".g_data",function(){
		//jQuery选择器   $("[href='#']") 所有herf的属性的值等于"#"的元素
		$("[data-id='"+gid+"']").css("border","solid 1px grey");
		gid=$(this).attr("data-id");
		//jQuery单独改某个属性
		$(this).css("border","solid 1px blue")
	    //点一下input清空
		$("#giftcount").val("")
		
		
	})
	//给选手送   活动选手礼物id,礼物数量
	
	$("#gift").click(function(){
		if(gid==0 || $("#giftcount").val()==""){
			alert("请选择礼物极其数量")
			}else{
				alert("进入else")
			$.ajax({
			url:'selectGiftForCandidate/'+aid+"/"+cid+"/"+gid+"/"+$("#giftcount").val(),
			type:'post',
			data:{},
			dataType:'json',
			success:function(data){
				
				console.log(data);
				alert("送礼物成功")
			}
		});
		}
		
		
		
	})
	
	
	//回首页
	$("#gohome").click(function(){
		
		location.href="index.html?aid="+aid;
		
		
	})
})