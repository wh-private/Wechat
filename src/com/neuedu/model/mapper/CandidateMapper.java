package com.neuedu.model.mapper;

import java.util.List;
import java.util.Map;

import com.neuedu.model.bean.Candidate;

public interface CandidateMapper {
public List<Candidate> selectCanidate(Map<String,Object> map);
		
public List<Candidate> selectCandidateByName(String name);

public void saveCandidate(Candidate c);
//�����ȶ�
public void updateCandidateHot(int cid);
//������Ϣ����
public List<Candidate> selectCandidateById(int cid);

//����ѡ��Ʊ��
public void updateCandidateTickets(int cid);

//���н���
public List<Candidate> selectAllranking(Map<String,Object> map);
//������
public void updateCandidateGift(Map<String,Object> map);
}
