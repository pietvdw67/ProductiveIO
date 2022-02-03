Start springboot from console
  mvn spring-boot:run

SQL's

-- Daily Detail
select * from dailydetail;
delete from  dailydetail;
select sum(countamount) from dailydetail where machineid =1 and countdate = '2022-01-15'

-- Schedule
select * from schedule;
update schedule set rundate = '2022-01-15'

-- Dailyhistory
select * from dailyhistory;
delete from dailyhistory;
insert into dailyhistory (machineid,countdate,countamount) values (1,'2022-01-13',4133)
SELECT * from dailyhistory where machineid = 1 order by countdate desc