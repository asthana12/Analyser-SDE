package com.cse.iitj.application;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(schema = "LOGS", name = "LOG_TABLE")
public class LogDbObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Integer auditId;
    @Column(name = "trace_id")
    private String traceId;
    @Column(name = "log_level")
    private String logLevel;
    @Column(name = "update_tmstmp")
    private Date updateTmstmp;
    @Column(name = "application_name")
    private String applicationName;
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "class_Name")
    private String className;

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public Date getUpdateTmstmp() {
        return updateTmstmp;
    }

    public void setUpdateTmstmp(Date updateTmstmp) {
        this.updateTmstmp = updateTmstmp;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
