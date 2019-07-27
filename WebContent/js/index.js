$(document).ready(function(){
	//解析活动id 取出id
	var aid =location.href.substr(location.href.indexOf("=")+1)

$.ajax({
		url:"updateActivityAccess/"+aid,
		type:"post",
	    data:{}	,
	    dateType:"json",
	    success:function(data){
	    	console.log(data)
	    	
	    }
})
//查活动基本信息   
//提出来成全局变量
var end;
var taskid
$.ajax({
		url:"selectActivityByid/"+aid,
		type:"post",
	    data:{}	,
	    dateType:"json",
	    success:function(data){
	    	console.log(data)
	    	//付给属性值attr,给标签用html,输入框用val!!!
	    	$("#imgurl").attr("src","img/"+data.imgurl);
	    	$("#totalpeople").html(data.totalpeople);
	    	$("#totalticket").html(data.totalticket);
	    	$("#totalaccess").html(data.totalaccess);
	    	//-----------------------------------------------	
	    	//活动开始,结束时间
	    	var begin=data.begintime;
	    	end=data.endtime
	    	var now=new Date();
	    	
	    	
	    	
	    	//比较时间  系统时间小于开始时间则活动未开始
	    	if(now<begin){
	    		$("#status").html("活动未开始")
	    		$("#countdown").html("活动开始时间为"+begin)
	    		
	    	}else if(now>end){
	    		$("#status").html("活动以结束")
	    		$("#countdown").html("0时0分0秒")
	    	}else{
	    		$("#status").html("活动正在进行")
	    		//倒计时,定时器 当前时间与end比较 小就倒计时
	    		taskid=setInterval(countdown,1000);
	    		
	    	}
	    	
	    }
})

//每过一秒比一次,now小与end就接着执行方法
function countdown(){
		var now =new Date();
		if(now>=end){	
			$("#status").html("活动已结束");
			clenarInterval(taskid)
		
		}else{
	//计算两个时间间隔的毫秒数 实体类里变了timestamp类型,毫秒数
			//看%后剩下的是
		var c=end-now;
		var seconds=parseInt(c/1000%60);
		var minutes=parseInt(c/1000/60%60);
		var hour =parseInt(c/1000/60%60)
		var days =parseInt(c/1000/60/60%24)
		$("#countdown").html(days+"天"+hour+"时"+minutes+"分钟"+seconds+"秒")
		}
		
	}

//查询指定活动的选手信息，第一页  4个选手
var pagesize=4;
var pagenum=1;
loadCandidates();


function loadCandidates(){
	$.ajax({
		url:'selectCandidate/'+aid+"/"+pagenum+"/"+pagesize,
		type:'post',
		data:{},
		dataType:'json',
		success:function(data){
			pagenum++;
			console.log(data);
			if(data.length==0){
				alert("没有更多选手数据");
			}else{
				for(var i=0;i<data.length;i++){
					var cdata = "<div class='c_data' data-id='"+data[i].cid+"'><img src='img/"+data[i].imgurl+"' /><div>"+data[i].name+","+data[i].cid+"号,"+data[i].tickets+"票</div></div>";
					$("#div2_3").append(cdata);
				}
			}
		}
	});
}


//点击加载更多查询下一页数据
$("#div2_4").click(function(){
	loadCandidates();
})

//搜索功能
$("#btn_search").click(function(){
	var inputName = $("#search_txt").val();
	if(inputName==""){
		alert("请输入您要查询的条件");
	}else{
		alert("进入ajax")
		$.ajax({
			url:'selectCandidateByName/'+inputName,
			type:'post',
			data:{},
			dataType:'json',
			success:function(data){
				console.log(data);
				$("#div2_3").empty();
				for(var i=0;i<data.length;i++){
					var cdata = "<div class='c_data' data-id='"+data[i].cid+"'><img src='img/"+data[i].imgurl+"' /><div>"+data[i].name+","+data[i].cid+"号,"+data[i].tickets+"票</div></div>";
					$("#div2_3").append(cdata);
				}
				//隐藏点击加载更多
				$("#div2_4").hide();
			}
		});
	}
})


//点击我要报名和下放导航的报名  都进入报名页面
$("#btn_signup").click(function(){
	window.location.href="signUp.html?aid="+aid;
})

$("#signUp_bottom").click(function(){
	window.location.href="signUp.html?aid="+aid;
})

$(document).on("click",".c_data",function(){
	//元素加自定义属性,取名data-加业wu=”值”       上面把值传入一个名   
	//上面是拼串回来的class,这里用this,才能知道是哪个.注意要$转成jQuery对象
	
	var cid=$(this).attr("data-id")
	location.href="details.html?aid="+aid+"&cid="+cid;
	
	alert(cid)
})
	
$("#ranking").click(function(){
	window.location.href="ranking.html?aid="+aid;
	
})


})
