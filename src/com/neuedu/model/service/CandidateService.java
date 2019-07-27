package com.neuedu.model.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.neuedu.model.bean.Giftrecord;
import com.neuedu.model.bean.Candidate;
import com.neuedu.model.bean.Images;
import com.neuedu.model.bean.Voterecord;
import com.neuedu.model.mapper.ActivityMapper;
import com.neuedu.model.mapper.CandidateMapper;
import com.neuedu.model.mapper.GiftrecordMapper;
import com.neuedu.model.mapper.ImagesMapper;
import com.neuedu.model.mapper.VoteRecordMapper;

@Service
public class CandidateService {
	@Autowired
	private CandidateMapper candidateMapper;
	@Autowired
	private ActivityMapper activityMapper;
	@Autowired
	private ImagesMapper imagesMapper;
	@Autowired
	private VoteRecordMapper voteRecordMapper;
	@Autowired
	private GiftrecordMapper giftRecordMapper;
public List<Candidate> selectCanidate(int aid,int pagesize,int pagenum){
	Map<String,Object> map=new HashMap<>();
	map.put("k_aid", aid);
	map.put("k_beginIndex", (pagenum-1)*pagesize);
	map.put("k_pagesize", pagesize);
	return candidateMapper.selectCanidate(map);
}

public List<Candidate> selectCandidateByName(String name){
	return candidateMapper.selectCandidateByName("%"+name+"%");
}
public void saveCandidate(Candidate c,MultipartFile[] upload, HttpServletRequest request) throws IllegalStateException, IOException{

//更新活动总报名
	activityMapper.updateActivityTotalPeople(c.getAid());
//存储选手信息  封面信息要从upload里取出来放到c里
	
	for(int i=0;i<upload.length;i++){
//存储选手图片信息
		//获取老名 固定语法 getOriginalFilename
		String oldName = upload[i].getOriginalFilename();
		//时间戳整个姓名
		String newName = System.currentTimeMillis()+oldName.substring(oldName.indexOf('.'));
		//实现上传  getServletContext()找到对应的tomcat下的路径   配置文件要加一段
		File file = new File(request.getServletContext().getRealPath("/img/"),newName);
		upload[i].transferTo(file);
		if(i==0){
			//普通表单里没有 的在这加上
			c.setImgurl(newName);
			c.setTickets(0);
			c.setHots(0);
			c.setGifts(0);
			c.setStatus(1);
			c.setOperator("1");
			c.setOperatorDate(new java.sql.Date(new Date().getTime()));
			candidateMapper.saveCandidate(c);
		}else{
			//可以循环
			//创建对象传进去
			Images image = new Images();
			//c里已经带回选手id
			image.setCid(c.getCid());
			image.setImgurl(newName);
			imagesMapper.saveImage(image);
		}
	}
}

public void updateCandidateHot(int cid){
	candidateMapper.updateCandidateHot(cid);
	
}

//个人面选手信息
public List<Candidate> selectCandidateById(int cid){
	 return candidateMapper.selectCandidateById(cid);
}

//投票功能
public void updateCandiAndActiTickets(int cid,int aid){
	
	//跟新活动总票书
	activityMapper.updateCandiAndActiTickets(aid);
	//更新选手总票书
	candidateMapper.updateCandidateTickets(cid);
	//更新投票记录
	Voterecord v=new Voterecord();
	v.setCid(cid);
	v.setOpenid("11");
	v.setVotetime(new Timestamp(new Date().getTime()));
	
	 voteRecordMapper.saveVoteRecord(v);
}

//排行
public List<Candidate> selectAllranking(int aid,int pagesize,int pagenum){
	Map<String,Object> map=new HashMap<>();
	map.put("k_aid", aid);
	map.put("k_beginIndex", (pagenum-1)*pagesize);
	map.put("k_pagesize", pagesize);
	return candidateMapper.selectAllranking(map);
	
}


public void selectGiftForCandidate(int aid,int cid,int gid,int giftcount){
	//更新选手礼物数
	Map<String,Object> map =new HashMap<>();
	map.put("k_cid",cid);
	map.put("k_giftcount",giftcount);
	candidateMapper.updateCandidateGift(map);
	
	//储存礼物记录
	Giftrecord ge = new Giftrecord();
	ge.setOpenid(11);
	ge.setGid(gid);
	ge.setCid(cid);
	ge.setCount(giftcount);
	ge.setGifttime(new Timestamp(new Date().getTime()));
	giftRecordMapper.saveGiftRecord(ge);
}

}
