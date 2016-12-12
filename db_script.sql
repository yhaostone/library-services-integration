-- drop databases if exists

drop database if exists book_db;
drop database if exists student_db;

-- create databases
create database student_db;
create database book_db;

-- create table in student db

use student_db;

-- drop tables if exist
drop table if exists student;

CREATE TABLE student (
  student_id int NOT NULL AUTO_INCREMENT,
  full_name varchar(30) NOT NULL,
  pin int(4) NOT NULL, 
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- insert rows in tables
insert into student (full_name,pin)
values('student1',1111);

insert into student(full_name,pin)
values('student2',2222);

insert into student(full_name,pin)
values('student3',3333);

insert into student(full_name,pin)
values('student4',4444);





use book_db;

-- drop tables if exist
drop table if exists book;
drop table if exists borrow_details;

CREATE TABLE book (
  book_id int NOT NULL AUTO_INCREMENT,
  book_title varchar(300) NOT NULL,
  author_list varchar(300) NOT NULL,
  isbn_10 varchar(10) NOT NULL,
  isbn_13 varchar(13) NOT NULL,
  publisher_name varchar(100) NOT NULL,
  publish_date varchar(20) NOT NULL, 
  book_status varchar(30) NOT NULL CHECK (book_status IN ('avaliable','on loan','back order')),  
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- insert rows in tables
insert into book(book_title,author_list,isbn_10,isbn_13,publisher_name,publish_date,book_status)
values('book1','author1',1111111111,1111111111111,'publisher1','1-11-1111','available');

insert into book(book_title,author_list,isbn_10,isbn_13,publisher_name,publish_date,book_status)
values('book2','author2',2222222222,2222222222222,'publisher2','2-22-2222','available');

insert into book(book_title,author_list,isbn_10,isbn_13,publisher_name,publish_date,book_status)
values('book3','author3',3333333333,3333333333333,'publisher3','3-33-3333','on loan');

insert into book(book_title,author_list,isbn_10,isbn_13,publisher_name,publish_date,book_status)
values('book4','author4',4444444444,4444444444444,'publisher4','4-44-4444','back order');

insert into book(book_title,author_list,isbn_10,isbn_13,publisher_name,publish_date,book_status)
values('book5','author5',5555555555,5555555555555,'publisher5','5-55-5555','on loan');

insert into book(book_title,author_list,isbn_10,isbn_13,publisher_name,publish_date,book_status)
values('book6','author6',6666666666,6666666666666,'publisher6','6-66-6666','on loan');

insert into book(book_title,author_list,isbn_10,isbn_13,publisher_name,publish_date,book_status)
values('book7','author7',7777777777,7777777777777,'publisher7','7-77-7777','back order');

insert into book(book_title,author_list,isbn_10,isbn_13,publisher_name,publish_date,book_status)
values('book8','author8',8888888888,8888888888888,'publisher8','8-88-8888','back order');

CREATE TABLE borrow_details (
  student_id int NOT NULL,
  book_id int NOT NULL, 
  borrow_status varchar(15) NOT NULL CHECK (borrow_status IN ('borrowed','requested')),  
  FOREIGN KEY (student_id) REFERENCES student_db.student (student_id),
  FOREIGN KEY (book_id) REFERENCES book (book_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- insert rows in tables
insert into borrow_details
values(1,5,'borrowed');

insert into borrow_details
values(1,6,'borrowed');

insert into borrow_details
values(1,7,'requested');

insert into borrow_details
values(1,8,'requested');

insert into borrow_details
values(3,3,'borrowed');

insert into borrow_details
values(4,4,'requested');


