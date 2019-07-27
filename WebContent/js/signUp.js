$(document).ready(function(){
	//获取从index里获取的aid
	var aid =location.href.substr(location.href.indexOf("=")+1);
	alert(aid);
	//获取隐藏控件
	$("#aid").val(aid);
	var files=[];
	$("#add").change(function(){
		/*当前上传控件          所有图片数组          图片转化成64字符集,放在图片src属性中即可*/
		var file=this.files[0];
		//数组加图
		files.push(file)
		//文件预览
		//1. 创建一个filereader对象
		var fileReader = new FileReader();
		//2. 定义filereader的onload方法（ base64字符串）
		//append(<img src="base64字符串">)
		fileReader.onload = function(e)
		{
			var base64 = e.target.result;
			$("#photoes").append('<img class="addedphoto" src="'+base64+'"/>');
		}
		//3. 读
		fileReader.readAsDataURL(file); 
		
	})
	

	//报名,有文件有元素  form上传文件不能像以前一样调方法序列化上传
	//是普通的获取,没图片啥的,这个有图,用特殊的
	$("#btn_signUpNew").click(function(){
		//单击后获得form对象,普通元进,图片等文件进不来
		var formData = new FormData(document.getElementById("myform"));
		//元素放进去,再把图(files)放进去
		for(var i=0;i<files.length;i++){
			formData.append("upload",files[i]);
		}
	//取名,放值,值是图片
	//新加属性2个属性,对于特殊的表单元素(含文件),默认值是false.可提交文件
		/*contentType:false,
		processData:false,*/
		alert("jinru");
		$.ajax({
			url:"saveCandidate",
			type:"post",
			data:formData,
			dataType:"json",
			contentType:false,
			processData:false,
			success:function(data){
				
				alert("报名成功")
			}
			
		})
	
})

//回首页
	$("#gohome").click(function(){
		location.href="index.html?aid="+aid;
		
	})

	$("#details").click(function(){
		location.href="ranking.html?aid="+aid;
	})
})
