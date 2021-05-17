CREATE DATABASE uddedb;

CREATE TABLE cities (
      id int UNSIGNED AUTO_INCREMENT,
      name varchar(255) NOT NULL,
      CONSTRAINT pk_id_cities PRIMARY KEY (id));

CREATE TABLE users (
      id int UNSIGNED AUTO_INCREMENT,
      dni varchar(255) NOT NULL,
      email varchar(255) NOT NULL,
      name varchar(255) NOT NULL,
      password varchar(255) NOT NULL,
      surname varchar(255) NOT NULL,
      user_type varchar(20) DEFAULT 'CLIENT',
      CONSTRAINT pk_id_users PRIMARY KEY (id),
      CONSTRAINT UNIQUE  unq_dni (dni),
      CONSTRAINT UNIQUE unq_email (email));

CREATE TABLE addresses(
       id int UNSIGNED AUTO_INCREMENT,
       department int(11) DEFAULT NULL,
       name varchar(255) DEFAULT NULL,
       number int(11) DEFAULT NULL,
       id_city int UNSIGNED,
       id_user int UNSIGNED,
       CONSTRAINT pk_id_addresses PRIMARY KEY (id),
       CONSTRAINT fk_id_users FOREIGN KEY (id_user) REFERENCES users (id),
       CONSTRAINT fk_id_city FOREIGN KEY (id_city) REFERENCES cities (id));

 CREATE TABLE brands (
       id int UNSIGNED AUTO_INCREMENT,
       name varchar(255) DEFAULT NULL,
       type varchar(255) DEFAULT NULL,
       CONSTRAINT pk_id_brand PRIMARY KEY (id));

CREATE TABLE models (
       id int UNSIGNED AUTO_INCREMENT,
       name varchar(255) DEFAULT NULL,
       type varchar(255) DEFAULT NULL,
       id_brand int UNSIGNED,
       CONSTRAINT pk_id_model PRIMARY KEY (id),
       CONSTRAINT fk_id_brand FOREIGN KEY (id_brand) REFERENCES brands (id));

CREATE TABLE rates (
       id int UNSIGNED AUTO_INCREMENT,
       price float NOT NULL,
       type varchar(255) NOT NULL,
       CONSTRAINT pk_id_rates PRIMARY KEY (id));

CREATE TABLE meters (
        id int UNSIGNED AUTO_INCREMENT,
        number int DEFAULT NULL,
        password varchar(255) NOT NULL,
        id_address int UNSIGNED,
        id_model int UNSIGNED,
        id_rate int UNSIGNED,
        CONSTRAINT pk_id_meters PRIMARY KEY (`id`),
        CONSTRAINT UNIQUE unq_number (number),
        CONSTRAINT fk_id_models FOREIGN KEY (id_model) REFERENCES models (id),
        CONSTRAINT fk_id_rates FOREIGN KEY (id_rate) REFERENCES rates (id),
        CONSTRAINT fk_id_addresses FOREIGN KEY (id_address) REFERENCES addresses (id));

CREATE TABLE invoices (
        id int UNSIGNED AUTO_INCREMENT,
        consumer_kw float NOT NULL,
        date_end datetime NOT NULL,
        date_initial datetime NOT NULL,
        date_invoice datetime NOT NULL,
        invoice_status varchar(20) DEFAULT 'OWED',
        number int UNSIGNED,
        total float NOT NULL,
        id_meters int UNSIGNED,
        CONSTRAINT pk_id_invoices PRIMARY KEY (id),
        CONSTRAINT fk_id_meters FOREIGN KEY (id_meters) REFERENCES meters (id));

CREATE TABLE measurements (
        id int UNSIGNED AUTO_INCREMENT,
        date datetime NOT NULL,
        id_invoice int UNSIGNED,
        kwh float NOT NULL,
        CONSTRAINT pk_id_measurements PRIMARY KEY (id));

CREATE TABLE meters_for_measurement (
        id int UNSIGNED AUTO_INCREMENT,
        id_measurement int UNSIGNED,
        id_meters int UNSIGNED,
        CONSTRAINT pk_id_meters_for_measurement PRIMARY KEY (id),
        CONSTRAINT fk_id_metes FOREIGN KEY (id_meters) REFERENCES meters (id),
        CONSTRAINT fk_id_measurement FOREIGN KEY (id_measurement) REFERENCES measurements (id));


INSERT INTO brands (name, type) VALUES ("BAW", "220V"), ("Jieli","220V");

