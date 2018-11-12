SET SCHEMA RBS;

DROP TABLE "USERROLE";
DROP TABLE "ROLEPRIV";
DROP TABLE "PRIV";
DROP TABLE "ROLE";
DROP TABLE "PASS";
DROP TABLE "USER";
DROP SCHEMA RBS RESTRICT;

CREATE SCHEMA RBS AUTHORIZATION RbsAdmin;

SET SCHEMA RBS;

CREATE TABLE "USER" (
	Id 			VARCHAR(36) 				CONSTRAINT pkUserId PRIMARY KEY,
	FirstName	VARCHAR(30)		NOT NULL,
	LastName	VARCHAR(30)		NOT NULL,
	Login 		VARCHAR(50) 	NOT NULL,
	Email		VARCHAR(255)	NOT NULL,
	Description VARCHAR(1000),
	Active		SMALLINT 		NOT NULL	DEFAULT 1,
	Created		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Updated		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Deleted		TIMESTAMP					DEFAULT NULL
);

CREATE TABLE "PASS" (
	Id			VARCHAR(36)					CONSTRAINT pkPassId PRIMARY KEY,
	UserId		VARCHAR(36)		NOT NULL,
	Password 	VARCHAR(50),
	ChgPassword	SMALLINT		NOT NULL	DEFAULT 1,
	Question	VARCHAR(80),
	Answer		VARCHAR(80),
	Locked		SMALLINT		NOT NULL	DEFAULT 0,
	Active		SMALLINT 		NOT NULL	DEFAULT 1,
	Created		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Updated		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Deleted		TIMESTAMP					DEFAULT NULL,
    Constraint 	fkUserId2       Foreign key (UserId) 	references "USER"
);

CREATE TABLE "ROLE" (
	Id 			VARCHAR(36) 				CONSTRAINT pkRoleId PRIMARY KEY,
	Name		VARCHAR(50)		NOT NULL,
	Description VARCHAR(1000),
	Active		SMALLINT 		NOT NULL	DEFAULT 1,
	Created		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Updated		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Deleted		TIMESTAMP					DEFAULT NULL
);

CREATE TABLE "PRIV" (
	Id 			VARCHAR(36) 				CONSTRAINT pkPrivId PRIMARY KEY,
	Name		VARCHAR(50)		NOT NULL,
	Description VARCHAR(1000),
	Category	VARCHAR(50),
	CanChange	SMALLINT		NOT NULL	DEFAULT 1,
	CanDelete	SMALLINT		NOT NULL	DEFAULT 1,
	Active		SMALLINT 		NOT NULL	DEFAULT 1,
	Created		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Updated		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Deleted		TIMESTAMP					DEFAULT NULL
);

CREATE TABLE "USERROLE" (
	Id 			VARCHAR(36) 				CONSTRAINT pkUserRoleId PRIMARY KEY,
	UserId		VARCHAR(36)		NOT NULL,
	RoleId		VARCHAR(36)		NOT NULL,
	Active		SMALLINT 		NOT NULL	DEFAULT 1,
	Created		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Updated		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Deleted		TIMESTAMP					DEFAULT NULL,
    Constraint 	fkUserId5 		Foreign key (UserId) 	references "USER",
    Constraint 	fkRoleId5 		Foreign key (RoleId) 	references "ROLE"
);

CREATE TABLE "ROLEPRIV" (
	Id 			VARCHAR(36) 				CONSTRAINT pkRolePrivId PRIMARY KEY,
	RoleId		VARCHAR(36)		NOT NULL,
	PrivId		VARCHAR(36)		NOT NULL,
	Active		SMALLINT 		NOT NULL	DEFAULT 1,
	Created		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Updated		TIMESTAMP					DEFAULT CURRENT_TIMESTAMP,
	Deleted		TIMESTAMP					DEFAULT NULL,
	Constraint	fkRoldId6		Foreign key (RoleId)	references "ROLE",
	Constraint	fkPrivId6		Foreign key (PrivId)	references "PRIV"
);

INSERT INTO "USER" (Id, FirstName, LastName, Login, Email, Description)
	VALUES ('UGD', 'Greg', 'Doud', 'gpdoud', 'gpdoud@hotmail.com', 'First test user');

INSERT INTO "USER" (Id, FirstName, LastName, Login, Email, Description)
	VALUES ('UCD', 'Cindy', 'Doud', 'cjdoud', 'cjdoud@hotmail.com', 'Second test user');

INSERT INTO "USER" (Id, FirstName, LastName, Login, Email, Description)
	VALUES ('UND', 'Nick', 'Doud', 'nsdoud', 'nsdoud@hotmail.com', 'Third test user');

INSERT INTO "USER" (Id, FirstName, LastName, Login, Email, Description)
	VALUES ('UKD', 'Ken', 'Doud', 'kmdoud', 'kmdoud@hotmail.com', 'Fourth test user');

INSERT INTO "ROLE" (Id, Name, Description)
	VALUES ('RSA', 'Admin', 'System Administrator');

INSERT INTO "ROLE" (Id, Name, Description)
	VALUES ('RUS', 'User', 'Normal User');

INSERT INTO "PRIV" (Id, Name, Category, Description, CanChange, CanDelete)
	VALUES ('PSA', 'SuperAdmin', 'Internal', 'All access', 0, 0);

INSERT INTO "PRIV" (Id, Name, Category, Description, CanChange, CanDelete)
	VALUES ('PCR', 'CanRead', 'Internal', 'Can Read', 0, 0);

INSERT INTO "PRIV" (Id, Name, Category, Description, CanChange, CanDelete)
	VALUES ('PCW', 'CanWrite', 'Internal', 'Can Write', 0, 0);

	