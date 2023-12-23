package com.example.TeamWorkAnalyzer.models;

import java.util.Date;

public class TeamWork {
    private Long empId;
    private Long projectId;
    private Date startDate;
    private Date endDate;

    public TeamWork(Long empId, Long projectId, Date startDate, Date endDate) {
        this.empId = empId;
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "TeamWork{" +
                "empId=" + empId +
                ", projectId=" + projectId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
