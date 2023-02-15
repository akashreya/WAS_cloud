drop database was; 

--creating database;
create database was;

-- using the database
use was;


--creating tables
create table DESIGNATION (DESIGNATION_ID integer not null, DESIGNATION varchar(50), IS_MANAGER bit not null, primary key (DESIGNATION_ID));
create table EMPLOYEE (EMPLOYEE_ID varchar(8) not null, NAME varchar(50) not null, LAST_MODIFIED_DATE datetime, DESIGNATION_ID integer, primary key (EMPLOYEE_ID));
create table SEAT (SEAT_NUMBER varchar(11) not null, EXTENSION_NUMBER varchar(6) unique, IS_MANAGER_SEAT bit, EMPLOYEE_ID varchar(8) unique, primary key (SEAT_NUMBER));
alter table EMPLOYEE add index FK75C8D6AE842FB1C (DESIGNATION_ID), add constraint FK75C8D6AE842FB1C foreign key (DESIGNATION_ID) references DESIGNATION (DESIGNATION_ID);
alter table SEAT add index FK26C6059854B178 (EMPLOYEE_ID), add constraint FK26C6059854B178 foreign key (EMPLOYEE_ID) references EMPLOYEE (EMPLOYEE_ID);

--insert statements to create data
insert into designation values(1,'Progammer Analyst', false);
insert into designation values(2,'Software Engineer', false);
insert into designation values(3,'Senior Engineer', false);
insert into designation values(4,'Senior Software Engineer', false);
insert into designation values(5,'Delivery Manager', true);
insert into designation values(6,'Project Manager', true);
insert into designation values(7,'Senior Project Manager', true);
insert into designation values(8,'Techinal Manager', true);
insert into designation values(9,'Senior Architect', false);
insert into designation values(10,'Program Architect', true);
insert into designation values(11,'Program Director', true);

