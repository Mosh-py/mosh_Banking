


CREATE TABLE IF NOT EXISTS userLogInDto (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL
);



CREATE TABLE IF NOT EXISTS account (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    username VARCHAR(50) NOT NULL,
    balance INT NOT NULL
);



