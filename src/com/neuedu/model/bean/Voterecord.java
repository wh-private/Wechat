package com.neuedu.model.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class Voterecord {
private int id;
//��������ĸ
private String openid;
private int cid;
private Timestamp votetime;
private Candidate candidate;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getOpenid() {
	return openid;
}
public void setOpenid(String openid) {
	this.openid = openid;
}
public int getCid() {
	return cid;
}
public void setCid(int cid) {
	this.cid = cid;
}
public Timestamp getVotetime() {
	return votetime;
}
public void setVotetime(Timestamp votetime) {
	this.votetime = votetime;
}
public Candidate getCandidate() {
	return candidate;
}
public void setCandidate(Candidate candidate) {
	this.candidate = candidate;
}

}
