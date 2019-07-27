package com.neuedu.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.neuedu.model.bean.Candidate;
import com.neuedu.model.service.CandidateService;

@Controller
public class CandidateController {
	@Autowired
	private CandidateService candidateservice;
    
	@RequestMapping("selectCandidate/{aid}/{pagenum}/{pagesize}")
    @ResponseBody
 //查4个
 public List<Candidate> selectCandidate(@PathVariable int aid,@PathVariable int pagesize,@PathVariable int pagenum){
	List<Candidate>	list= candidateservice.selectCanidate(aid, pagesize, pagenum);
		 return list;
 }

//根据名字模糊查询
	@RequestMapping("selectCandidateByName/{name}")
	@ResponseBody
	public List<Candidate> selectCandidateByName(@PathVariable String name){
		System.out.println("name="+name);
		return candidateservice.selectCandidateByName(name);
	}



//@RequestParam用于接收多个图片
	@RequestMapping("saveCandidate")
	@ResponseBody
	//入参request目的是让后边调用,找文件路径
	public String saveCandidate(HttpServletRequest request,Candidate c,@RequestParam MultipartFile[] upload)throws IllegalStateException, IOException{
		System.err.println("aid的值======================="+c.getAid());
		System.err.println("cid的值======================="+c.getCid());
		candidateservice.saveCandidate(c, upload, request);
		return "{\"result\":\"报名成功\"}";
	}
	
	
	//个人界面热度   ResourceMapping地址带参数cid  入参@pathVariable解析cid
	@RequestMapping("updateCandidateHot/{cid}")
	@ResponseBody
	public String updateCandidateHot(@PathVariable int cid){
		System.out.println("jinlai le ");
		candidateservice.updateCandidateHot(cid);
		return "{\"result\":\"更新成功\"}";
		
	}
      //个人面选手信息
	@RequestMapping("selectCandidateById/{cid}")
	@ResponseBody
	public List<Candidate> selectCandidateById(@PathVariable int cid){
		
		return candidateservice.selectCandidateById(cid);
		
		
	}

  //投票功能
	@RequestMapping("voteCandidate/{aid}/{cid}")
	@ResponseBody
	public String voteCandidate(@PathVariable int cid,@PathVariable int aid){
		candidateservice.updateCandiAndActiTickets(cid, aid);
		
		return "{\"result\":\"投票成功\"}";
	}

//查询排行
	@RequestMapping("selectAllranking/{aid}/{pagenum}/{pagesize}")
	@ResponseBody
     public List<Candidate> selectAllranking(@PathVariable int aid,@PathVariable int pagenum,@PathVariable int pagesize){
		
		
		return candidateservice.selectAllranking(aid, pagesize, pagenum);
		
		
	}

//送礼物  
	@RequestMapping("selectGiftForCandidate/{aid}/{gid}/{cid}/{giftcount}")
	@ResponseBody
	public String selectGiftForCandidate(@PathVariable int aid,@PathVariable int cid,@PathVariable int gid,@PathVariable int giftcount){
		candidateservice.selectGiftForCandidate(aid,cid,gid,giftcount);
		System.out.println(aid+","+cid+","+gid+","+giftcount);
		return "{\"results\":\"增送成功\"}";
		
	}


}
