package com.neuedu.model.mapper;

import com.neuedu.model.bean.Activity;

public interface ActivityMapper{ 

     //�޸Ļ�ȶ�
	public void updateActivityAccess(int aid); 


    public Activity selectActivityByid(int aid);
      //�������µ��ܱ�����
    public void updateActivityTotalPeople(int aid);
    
    //���»��Ʊ��
    public void updateCandiAndActiTickets(int aid);
    
    
    
    
}