package com.wan.sys.pojo;

import javax.persistence.Column;

import com.wan.sys.common.BaseEntity;

public class AreaBean extends BaseEntity{

     private Long areaTypeId;
     private Long parentAreaId;
     private String areaName;
     private Long areaNumber;
     private String areaCode;
     private String isLeaf;
     private Long nodeLevel;
     private Long sortRank;
     private String status;
     private Long operators;
     private String opTime;
     private String areaIndexId;
     private String codeMark;
     private String areaGrade;
     private String areaDesc;
     
    public AreaBean() {
    }

    public Long getAreaTypeId() {
        return this.areaTypeId;
    }
    
    public void setAreaTypeId(Long areaTypeId) {
        this.areaTypeId = areaTypeId;
    }
    

    public Long getParentAreaId() {
        return this.parentAreaId;
    }
    
    public void setParentAreaId(Long parentAreaId) {
        this.parentAreaId = parentAreaId;
    }
    

    public String getAreaName() {
        return this.areaName;
    }
    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    

    public Long getAreaNumber() {
        return this.areaNumber;
    }
    
    public void setAreaNumber(Long areaNumber) {
        this.areaNumber = areaNumber;
    }

    public String getAreaCode() {
        return this.areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public String getIsLeaf() {
        return this.isLeaf;
    }
    
    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }
    
    public Long getNodeLevel() {
        return this.nodeLevel;
    }
    
    public void setNodeLevel(Long nodeLevel) {
        this.nodeLevel = nodeLevel;
    }
    
    public Long getSortRank() {
        return this.sortRank;
    }
    
    public void setSortRank(Long sortRank) {
        this.sortRank = sortRank;
    }
    
    @Column(name="STATUS", length=3)

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Long getOperators() {
        return this.operators;
    }
    
    public void setOperators(Long operators) {
        this.operators = operators;
    }

    public String getOpTime() {
        return this.opTime;
    }
    
    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }
    
    public String getAreaIndexId() {
        return this.areaIndexId;
    }
    
    public void setAreaIndexId(String areaIndexId) {
        this.areaIndexId = areaIndexId;
    }
    
    public String getCodeMark() {
        return this.codeMark;
    }
    
    public void setCodeMark(String codeMark) {
        this.codeMark = codeMark;
    }
    
    public String getAreaGrade() {
        return this.areaGrade;
    }
    
    public void setAreaGrade(String areaGrade) {
        this.areaGrade = areaGrade;
    }
    
    public String getAreaDesc() {
        return this.areaDesc;
    }
    
    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
    }
   








}