CREATE TABLE srv_registration.regs_account (
	account_id bigint auto_increment NOT NULL,
	account_name varchar(100) NOT NULL,
	account_email varchar(100) NOT NULL,
	account_password text NOT NULL,
	account_phone varchar(15) NOT NULL,
	account_credit_card varchar(100) NULL,
	account_card_cvv varchar(100) NULL,
	account_card_exp DATE NULL,
	account_card_name varchar(100) NULL,
	account_status varchar(50) NULL,
	created_at DATETIME NOT NULL,
	updated_at DATETIME NULL,
	CONSTRAINT regs_account_pk PRIMARY KEY (account_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;


CREATE TABLE srv_registration.regs_otp (
	otp_id int(11) auto_increment NOT NULL,
	otp_email varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
	otp_code varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
	created_at datetime NOT NULL,
	updated_at datetime DEFAULT NULL NULL,
	otp_expired_at datetime DEFAULT NULL NULL,
	CONSTRAINT regs_otp_pk PRIMARY KEY (otp_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

CREATE TABLE srv_registration.regs_payment (
	payment_id int(11) auto_increment NOT NULL,
	account_id bigint(20) NOT NULL,
	payment_total int(11) DEFAULT NULL NULL,
	created_at datetime NOT NULL,
	updated_at datetime DEFAULT NULL NULL,
	payment_status varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL,
	payment_subscription_id int(11) NOT NULL,
	CONSTRAINT regs_otp_pk PRIMARY KEY (payment_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

CREATE TABLE srv_registration.regs_subs_detail (
	subs_detail_id int(11) auto_increment NOT NULL,
	subscription_id int(11) NOT NULL,
	subs_detail_desc text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL,
	subs_detail_duration int(11) DEFAULT NULL NULL,
	CONSTRAINT regs_subs_detail_pk PRIMARY KEY (subs_detail_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

CREATE TABLE srv_registration.regs_subs_member (
	subs_member_id int(11) auto_increment NOT NULL,
	subscription_id int(11) NOT NULL,
	subs_member_status char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL,
	subs_member_session int(11) DEFAULT NULL NULL,
	subs_member_account_id bigint(20) NOT NULL,
	created_at datetime DEFAULT NULL NULL,
	updated_at datetime DEFAULT NULL NULL,
	CONSTRAINT regs_subs_member_pk PRIMARY KEY (subs_member_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

CREATE TABLE srv_registration.regs_subs_schedule (
	subs_schedule_id int(11) auto_increment NOT NULL,
	subscription_id int(11) NOT NULL,
	subs_schedule varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL,
	CONSTRAINT regs_subs_schedule_pk PRIMARY KEY (subs_schedule_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

CREATE TABLE srv_registration.regs_subscription (
	sub_id int(11) auto_increment NOT NULL,
	sub_menu varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
	sub_price int(11) NOT NULL,
	sub_duration int(11) DEFAULT NULL NULL,
	CONSTRAINT regs_subscription_pk PRIMARY KEY (sub_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

CREATE TABLE srv_registration.roles (
	id int(11) auto_increment NOT NULL,
	name enum('ROLE_ADMIN','ROLE_USER') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL,
	CONSTRAINT roles_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

CREATE TABLE srv_registration.user_roles (
	user_id bigint(20) NOT NULL,
	role_id int(11) DEFAULT NULL NULL,
	CONSTRAINT user_roles_pk PRIMARY KEY (user_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

CREATE TABLE srv_registration.refreshtoken (
	id bigint(20) auto_increment NOT NULL,
	expiry_date datetime(6) DEFAULT NULL NULL,
	token varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL NULL,
	user_id bigint(20) DEFAULT NULL NULL,
	CONSTRAINT refreshtoken_pk PRIMARY KEY (id)
)

ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

INSERT INTO srv_registration.regs_subscription
(sub_id, sub_menu, sub_price, sub_duration)
VALUES(1, 'Overweight dan Obesity', 70000, 8);
INSERT INTO srv_registration.regs_subscription
(sub_id, sub_menu, sub_price, sub_duration)
VALUES(2, 'Menjaga Kebugaran', 50000, 8);

INSERT INTO srv_registration.roles
(id, name)
VALUES(1, 'ROLE_ADMIN');
INSERT INTO srv_registration.roles
(id, name)
VALUES(2, 'ROLE_USER');

INSERT INTO srv_registration.regs_subs_detail
(subs_detail_id, subscription_id, subs_detail_desc, subs_detail_duration)
VALUES(1, 1, 'Skipping', 30);
INSERT INTO srv_registration.regs_subs_detail
(subs_detail_id, subscription_id, subs_detail_desc, subs_detail_duration)
VALUES(2, 1, 'Barbel', 20);
INSERT INTO srv_registration.regs_subs_detail
(subs_detail_id, subscription_id, subs_detail_desc, subs_detail_duration)
VALUES(3, 2, 'Lari', 60);
INSERT INTO srv_registration.regs_subs_detail
(subs_detail_id, subscription_id, subs_detail_desc, subs_detail_duration)
VALUES(4, 2, 'dumbell', 30);

INSERT INTO srv_registration.regs_subs_schedule
(subs_schedule_id, subscription_id, subs_schedule)
VALUES(1, 1, 'Senin');
INSERT INTO srv_registration.regs_subs_schedule
(subs_schedule_id, subscription_id, subs_schedule)
VALUES(NULL, 1, 'Kamis');
INSERT INTO srv_registration.regs_subs_schedule
(subs_schedule_id, subscription_id, subs_schedule)
VALUES(3, 2, 'Rabu');
INSERT INTO srv_registration.regs_subs_schedule
(subs_schedule_id, subscription_id, subs_schedule)
VALUES(4, 2, 'Sabtu');

