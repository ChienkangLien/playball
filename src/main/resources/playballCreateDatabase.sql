drop schema if exists `playball`;
create database `playball`;
use `playball`;

drop table if exists `USER`; 
create table `USER` (
	`ID` int not null auto_increment comment '使用者編號' primary key,
	`EMAIL` varchar(50) not null comment '使用者信箱' unique,
    `PASSWORD` varchar(400) not null comment '使用者信箱',
    `FOULS` int not null default 0 comment '違規次數'
) comment '會員資料表';

/* insert into USER
	(`EMAIL`,`PASSWORD`)
VALUES
	('test@test','123456'); */

drop table if exists `ADMIN`;  
create table `ADMIN` (
`ID` int not null auto_increment comment '管理員編號' primary key,
`ACCOUNT` varchar(20) not null comment '管理員帳號' unique,
`PASSWORD` varchar(200) not null comment '管理員密碼'
)comment '後臺管理員';

/* insert into ADMIN
	(`ACCOUNT`,`PASSWORD`)
VALUES
	('root','password'); */

CREATE TABLE `RESERVATIONS` (
  `ID` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `USER_ID` int NOT NULL,
  `BOOK_DATE` date NOT NULL,
  `TIME_SLOT` varchar(50) NOT NULL,
  `STATUS` varchar(50) NOT NULL default '未到',
  `BOOK_ORDER` varchar(50) NOT NULL,
  `SEND_MAIL` int NOT NULL default 0,
  foreign key (`USER_ID`) references `USER` (`ID`)
);

/* insert into RESERVATIONS
	(`USER_ID`,`BOOK_DATE`,`TIME_SLOT`,`BOOK_ORDER`)
VALUES
	
    ('1','2023-04-01','下午','候補3'),
    ('1','2023-04-01','下午','候補4'),
    ('1','2023-04-01','下午','候補5'),
    ('1','2023-04-01','下午','正取5'),
    ('1','2023-04-01','下午','正取6'),
    ('1','2023-04-01','下午','正取7'),
    ('1','2023-04-01','下午','正取8'),
    ('1','2023-04-01','下午','正取9'),
    ('1','2023-04-01','下午','正取10'),
    ('1','2023-04-01','下午','正取11'),
    ('1','2023-04-01','下午','正取12'),
    ('1','2023-04-01','下午','正取13'),
    ('1','2023-04-01','下午','正取14'),
    ('1','2023-04-01','下午','正取15'),
    ('1','2023-04-01','下午','正取16'),
    ('1','2023-04-01','下午','正取17'),
    ('1','2023-04-01','下午','正取18'),
    ('1','2023-04-01','下午','候補1'),
    ('1','2023-04-01','下午','候補2'); */