CREATE SCHEMA LOGS;
CREATE TABLE LOGS.LOG_TABLE (audit_id number(10) primary key, trace_id varchar(20), application_name varchar(100),
error_message varchar(4000), class_Name varchar(1000),log_level varchar(100), update_tmstmp date);