create database eazybank;

CREATE TABLE users (
   id INT NOT NULL AUTO_INCREMENT,
   username VARCHAR(50) NOT NULL,
   password VARCHAR(500) NOT NULL,
   enabled INT NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE authorities (
   id INT NOT NULL AUTO_INCREMENT,
   username VARCHAR(50) NOT NULL,
   authority VARCHAR(50) NOT NULL,
   CONSTRAINT fk_authorities_users FOREIGN KEY(id) REFERENCES users(id),
   PRIMARY KEY (id)
);
CREATE UNIQUE INDEX ix_auth_username ON authorities (username,authority);

use eazybank;

insert ignore into `users` values (null, 'happy', '12345', '1');
insert ignore into `authorities` values (null, 'happy', 'write');
