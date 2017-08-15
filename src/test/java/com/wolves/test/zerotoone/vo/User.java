package com.wolves.test.zerotoone.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class User implements Serializable{
	
	
	private static final long serialVersionUID = 7922037410005120901L;
	private String adid;
    private String bodySystemCode;
    private String bodySystemName;
    private String cnnname;
    private String deptLeader;
    private String deptLeaderCode;
    private String deptcode;
    private String deptname;
    private String email;
    private String enname;
    private String fristLeader;
    private String fristLeaderCode;
    private String gender;
    private String id;
    private String objno;
    private String position;
    private String tel;
    private Integer state;
    private String searchName;
    private Boolean isRepeat; 
    
	public String getAdid() {
		return adid;
	}
	public void setAdid(String adid) {
		if(StringUtils.isBlank(adid) || StringUtils.equals("null", adid)){
			adid="";
		}
		this.adid = adid;
	}
	public String getBodySystemCode() {
		return bodySystemCode;
	}
	public void setBodySystemCode(String bodySystemCode) {
		if(StringUtils.isBlank(bodySystemCode) || StringUtils.equals("null", bodySystemCode)){
			bodySystemCode="";
		}
		this.bodySystemCode = bodySystemCode;
	}
	public String getBodySystemName() {
		return bodySystemName;
	}
	public void setBodySystemName(String bodySystemName) {
		if(StringUtils.isBlank(bodySystemName) || StringUtils.equals("null", bodySystemName)){
			bodySystemName="";
		}
		this.bodySystemName = bodySystemName;
	}
	public String getCnnname() {
		return cnnname;
	}
	public void setCnnname(String cnnname) {
		if(StringUtils.isBlank(cnnname) || StringUtils.equals("null", cnnname)){
			cnnname="";
		}
		this.cnnname = cnnname;
	}
	public String getDeptLeader() {
		return deptLeader;
	}
	public void setDeptLeader(String deptLeader) {
		if(StringUtils.isBlank(deptLeader) || StringUtils.equals("null", deptLeader)){
			deptLeader="";
		}
		this.deptLeader = deptLeader;
	}
	public String getDeptLeaderCode() {
		return deptLeaderCode;
	}
	public void setDeptLeaderCode(String deptLeaderCode) {
		if(StringUtils.isBlank(deptLeaderCode) || StringUtils.equals("null", deptLeaderCode)){
			deptLeaderCode="";
		}
		this.deptLeaderCode = deptLeaderCode;
	}
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		if(StringUtils.isBlank(deptcode) || StringUtils.equals("null", deptcode)){
			deptcode="";
		}
		this.deptcode = deptcode;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		if(StringUtils.isBlank(deptname) || StringUtils.equals("null", deptname)){
			deptname="";
		}
		this.deptname = deptname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(StringUtils.isBlank(email) || StringUtils.equals("null", email)){
			email="";
		}
		this.email = email;
	}
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		if(StringUtils.isBlank(enname) || StringUtils.equals("null", enname)){
			enname="";
		}
		this.enname = enname;
	}
	public String getFristLeader() {
		return fristLeader;
	}
	public void setFristLeader(String fristLeader) {
		if(StringUtils.isBlank(fristLeader) || StringUtils.equals("null", fristLeader)){
			fristLeader="";
		}
		this.fristLeader = fristLeader;
	}
	public String getFristLeaderCode() {
		return fristLeaderCode;
	}
	public void setFristLeaderCode(String fristLeaderCode) {
		if(StringUtils.isBlank(fristLeaderCode) || StringUtils.equals("null", fristLeaderCode)){
			fristLeaderCode="";
		}
		this.fristLeaderCode = fristLeaderCode;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		if(StringUtils.isBlank(gender) || StringUtils.equals("null", gender)){
			gender="";
		}
		this.gender = gender;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		if(StringUtils.isBlank(id) || StringUtils.equals("null", id)){
			id="";
		}
		this.id = id;
	}
	public String getObjno() {
		return objno;
	}
	public void setObjno(String objno) {
		if(StringUtils.isBlank(objno) || StringUtils.equals("null", objno)){
			objno="";
		}
		this.objno = objno;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		if(StringUtils.isBlank(position) || StringUtils.equals("null", position)){
			position="";
		}
		this.position = position;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		if(StringUtils.isBlank(tel) || StringUtils.equals("null", tel)){
			tel="";
		}
		this.tel = tel;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public Boolean getIsRepeat() {
		return isRepeat;
	}
	public void setIsRepeat(Boolean isRepeat) {
		this.isRepeat = isRepeat;
	}
    
    
}
