create table tb_dept
(
    dept_id      bigint null,
    dept_name    varchar(30) null,
    staff        int null,
    tel          varchar(50) null,
    deleted      bit null,
    version      int null,
    gmt_create   datetime null,
    gmt_modified datetime null
);

create table tb_stu_sub_relation
(
    id     int unsigned null,
    stu_id int null,
    sub_id int null,
    score  int null
);

create table tb_student
(
    stu_id   int null,
    stu_name varchar(30) null
);

create table tb_subject
(
    sub_id   int null,
    sub_name varchar(30) null
);

create table tb_user
(
    user_id      bigint null,
    user_name    varchar(30) null,
    age          int null,
    email        varchar(50) null,
    dept_id      bigint null,
    deleted      bit null,
    version      int null,
    gmt_create   datetime null,
    gmt_modified datetime null
);

