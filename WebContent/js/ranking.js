$(document).ready(function(){
	
	var aid =location.href.substr(location.href.indexOf("=")+1)
	var pagesize=4;
	var pagenum=1;
	var ranknum =1;
	alert(aid);
	$.ajax({
		type:"post",
		url:"selectAllranking/"+aid+"/"+pagenum+"/"+pagesize,
		data:{},
		dataType:"json",
		success:function(data){
			pagenum++;
			console.log(data);
			if(data.length==0){
				alert("没有更多选手数据");
			}else{
				for(var i=0;i<data.length;i++){
					
					$("#div1").append(
							"<div id='div1_1' data-div='"+data[i].cid+"'>" +
							"<div class='div1_11'>" +
							"<img  src='img/"+data[i].imgurl+"' />" +
									"</div><div class='div1_12'>" +
									"<p id='p1'>"+data[i].name+","+data[i].cid+",票数:"+data[i].tickets+",礼物数:"+data[i].gifts+",宣言:"+data[i].declaration+"</p>" +
											"</div><div class='div1_13'><p id='p2'>"+ranknum+"</p></div>");
					ranknum++;
				}
			}
			
		}
	});
	
	
	$("#onel").click(function(){
		window.location.href="index.html?aid="+aid;
		
	})
	
	
	$(document).on("click","#div1_1",function(){
		var cid;
	    cid=$(this).attr("data-div");
		location.href="details.html?aid="+aid+"&cid="+cid;
		
	})
	
	
})
