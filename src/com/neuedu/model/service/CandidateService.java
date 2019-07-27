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

//���»�ܱ���
	activityMapper.updateActivityTotalPeople(c.getAid());
//�洢ѡ����Ϣ  ������ϢҪ��upload��ȡ�����ŵ�c��
	
	for(int i=0;i<upload.length;i++){
//�洢ѡ��ͼƬ��Ϣ
		//��ȡ���� �̶��﷨ getOriginalFilename
		String oldName = upload[i].getOriginalFilename();
		//ʱ�����������
		String newName = System.currentTimeMillis()+oldName.substring(oldName.indexOf('.'));
		//ʵ���ϴ�  getServletContext()�ҵ���Ӧ��tomcat�µ�·��   �����ļ�Ҫ��һ��
		File file = new File(request.getServletContext().getRealPath("/img/"),newName);
		upload[i].transferTo(file);
		if(i==0){
			//��ͨ����û�� ���������
			c.setImgurl(newName);
			c.setTickets(0);
			c.setHots(0);
			c.setGifts(0);
			c.setStatus(1);
			c.setOperator("1");
			c.setOperatorDate(new java.sql.Date(new Date().getTime()));
			candidateMapper.saveCandidate(c);
		}else{
			//����ѭ��
			//�������󴫽�ȥ
			Images image = new Images();
			//c���Ѿ�����ѡ��id
			image.setCid(c.getCid());
			image.setImgurl(newName);
			imagesMapper.saveImage(image);
		}
	}
}

public void updateCandidateHot(int cid){
	candidateMapper.updateCandidateHot(cid);
	
}

//������ѡ����Ϣ
public List<Candidate> selectCandidateById(int cid){
	 return candidateMapper.selectCandidateById(cid);
}

//ͶƱ����
public void updateCandiAndActiTickets(int cid,int aid){
	
	//���»��Ʊ��
	activityMapper.updateCandiAndActiTickets(aid);
	//����ѡ����Ʊ��
	candidateMapper.updateCandidateTickets(cid);
	//����ͶƱ��¼
	Voterecord v=new Voterecord();
	v.setCid(cid);
	v.setOpenid("11");
	v.setVotetime(new Timestamp(new Date().getTime()));
	
	 voteRecordMapper.saveVoteRecord(v);
}

//����
public List<Candidate> selectAllranking(int aid,int pagesize,int pagenum){
	Map<String,Object> map=new HashMap<>();
	map.put("k_aid", aid);
	map.put("k_beginIndex", (pagenum-1)*pagesize);
	map.put("k_pagesize", pagesize);
	return candidateMapper.selectAllranking(map);
	
}


public void selectGiftForCandidate(int aid,int cid,int gid,int giftcount){
	//����ѡ��������
	Map<String,Object> map =new HashMap<>();
	map.put("k_cid",cid);
	map.put("k_giftcount",giftcount);
	candidateMapper.updateCandidateGift(map);
	
	//���������¼
	Giftrecord ge = new Giftrecord();
	ge.setOpenid(11);
	ge.setGid(gid);
	ge.setCid(cid);
	ge.setCount(giftcount);
	ge.setGifttime(new Timestamp(new Date().getTime()));
	giftRecordMapper.saveGiftRecord(ge);
}

}
