package com.neuedu.model.mapper;

import com.neuedu.model.bean.Activity;

public interface ActivityMapper{ 

     //修改活动热度
	public void updateActivityAccess(int aid); 


    public Activity selectActivityByid(int aid);
      //报名更新的总报名数
    public void updateActivityTotalPeople(int aid);
    
    //更新活动总票数
    public void updateCandiAndActiTickets(int aid);
    
    
    
    
}