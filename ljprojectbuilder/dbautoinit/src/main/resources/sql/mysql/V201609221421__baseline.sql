CREATE TABLE ATTRIBUTE
(
   ID bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
   TYPE varchar(255) NOT NULL,
   DESCRIPTION varchar(255),
   MAXIMUM integer,
   MINIMUM integer,
   NAME varchar(255) NOT NULL,
   NULLABLE boolean NOT NULL,
   PATTERN varchar(255)
)
;
CREATE UNIQUE INDEX ATTRIBUTE_INDEX ON ATTRIBUTE(ID);


CREATE TABLE DOMAIN
(
   ID bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
   DESCRIPTION varchar(255),
   NAME varchar(255) NOT NULL,
   PROJECT_ID bigint NOT NULL
)
;

CREATE TABLE DOMAIN_ATTRIBUTE
(
   DOMAINENTITY_ID bigint NOT NULL,
   ATTRIBUTES_ID bigint NOT NULL
)
;
CREATE TABLE PROJECT
(
   ID bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
   DESCRIPTION varchar(255),
   PACKAGEPREFIX varchar(100) NOT NULL,
   TARGETPATH varchar(100),
   TITLE varchar(100) NOT NULL,
   TEMPLATE_ID bigint NOT NULL
)
;
CREATE TABLE TEMPLATE
(
   ID bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
   LOCATION varchar(100) NOT NULL,
   PREFIX varchar(100) NOT NULL,
   TITLE varchar(100) NOT NULL,
   BRANCH varchar(100)
)
;

CREATE UNIQUE INDEX DOMAIN_INDEX ON DOMAIN(ID);

ALTER TABLE DOMAIN
ADD CONSTRAINT FK_DOMAIN_PROJECT
FOREIGN KEY (PROJECT_ID)
REFERENCES PROJECT(ID);

CREATE INDEX DOMAIN_PROJECT_INDEX ON DOMAIN(PROJECT_ID);

ALTER TABLE DOMAIN_ATTRIBUTE
ADD CONSTRAINT FK_D_A_ATTRIBUTE
FOREIGN KEY (ATTRIBUTES_ID)
REFERENCES ATTRIBUTE(ID);

ALTER TABLE DOMAIN_ATTRIBUTE
ADD CONSTRAINT FK_D_A_DOMAIN
FOREIGN KEY (DOMAINENTITY_ID)
REFERENCES DOMAIN(ID);

CREATE UNIQUE INDEX D_A_ATTRIBUTE_INDEX ON DOMAIN_ATTRIBUTE(ATTRIBUTES_ID);
CREATE INDEX D_A_DOMAIN_INDEX ON DOMAIN_ATTRIBUTE(DOMAINENTITY_ID);

ALTER TABLE PROJECT
ADD CONSTRAINT FK_PROJECT_TEMPLATE
FOREIGN KEY (TEMPLATE_ID)
REFERENCES TEMPLATE(ID);

CREATE INDEX PROJECT_TEMPLATE_INDEX ON PROJECT(TEMPLATE_ID);
CREATE UNIQUE INDEX PROJECT_INDEX ON PROJECT(ID);
CREATE UNIQUE INDEX TEMPLATE_INDEX ON TEMPLATE(ID);