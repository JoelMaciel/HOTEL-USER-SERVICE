CREATE TABLE users (
    user_id CHAR(36) NOT NULL,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(40) NOT NULL,
    description VARCHAR(255),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id)
);
