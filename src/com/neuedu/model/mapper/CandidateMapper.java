package com.neuedu.model.mapper;

import java.util.List;
import java.util.Map;

import com.neuedu.model.bean.Candidate;

public interface CandidateMapper {
public List<Candidate> selectCanidate(Map<String,Object> map);
		
public List<Candidate> selectCandidateByName(String name);

public void saveCandidate(Candidate c);
//个人热度
public void updateCandidateHot(int cid);
//个人信息界面
public List<Candidate> selectCandidateById(int cid);

//更新选手票数
public void updateCandidateTickets(int cid);

//排行界面
public List<Candidate> selectAllranking(Map<String,Object> map);
//礼物数
public void updateCandidateGift(Map<String,Object> map);
}
