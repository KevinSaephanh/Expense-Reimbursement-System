-------------------- PERMISSIONS ------------------

create role ers_manager with password 'ExpenseReimbursementSystem123';

alter role "ers_manager" with login;

grant all privileges on table ers_user_roles to ers_manager;

grant all privileges on table ers_users to ers_manager;

grant all privileges on table ers_reimbursements to ers_manager;

grant all privileges on table ers_reimbursement_type to ers_manager;

grant all privileges on table ers_reimbursement_status to ers_manager;

grant usage, select on all sequences in schema public to ers_manager;

-------------------- TABLES -----------------------

create table if not exists ers_user_roles(
	ers_user_role_id serial primary key,
	user_role varchar(10) not null
);

create table if not exists ers_users(
	user_id serial primary key,
	user_name varchar(20) unique not null,
	pass_word varchar(100) not null,
	first_name varchar(20) not null,
	last_name varchar(25) not null,
	email varchar(100) unique not null,
	ers_user_role_id integer references ers_user_roles(ers_user_role_id) not null
);

create table if not exists ers_reimbursements(
	reimb_id serial primary key,
	reimb_amount numeric(15, 2) not null,
	reimb_submitted timestamp not null,
	reimb_resolved timestamp,
	reimb_description varchar(250),
	reimb_receipt bytea,
	reimb_author integer references ers_users(user_id) not null,
	reimb_resolver integer references ers_users(user_id),
	reimb_status_id integer references ers_reimbursement_status(reimb_status_id) not null,
	reimb_type_id integer references ers_reimbursement_type(reimb_type_id) not null
);

create table if not exists ers_reimbursement_type(
	reimb_type_id serial primary key,
	reimb_type varchar(10) not null
);

create table if not exists ers_reimbursement_status(
	reimb_status_id serial primary key,
	reimb_status varchar(10) not null
);

-------------------------- INSERTS -------------------------------

insert into ers_users(user_name, pass_word, first_name, last_name, email, ers_user_role_id) values(
	'Kevin',
	'Kevin123',
	'Kevin',
	'Saephanh',
	'Kevin_Saephanh@yahoo.com',
	2
);

--------------------------- PREDEFINED FIELDS ---------------------

-- User Roles
insert into ers_user_roles(user_role) values('Employee');

insert into ers_user_roles(user_role) values('Manager');

-- Reimbursement Statuses
insert into ers_reimbursement_status(reimb_status) values('Pending');

insert into ers_reimbursement_status(reimb_status) values('Approved');

insert into ers_reimbursement_status(reimb_status) values('Denied');

-- Reimbursement types
insert into ers_reimbursement_type(reimb_type) values('Lodging');

insert into ers_reimbursement_type(reimb_type) values('Travel');

insert into ers_reimbursement_type(reimb_type) values('Food');

insert into ers_reimbursement_type(reimb_type) values('Other');
