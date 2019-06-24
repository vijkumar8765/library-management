CREATE TABLE book (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    author VARCHAR(500) NOT NULL,
    published_date DATE NOT NULL,
    price FLOAT NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);