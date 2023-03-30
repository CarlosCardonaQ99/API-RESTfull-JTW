
CREATE TABLE usuarios (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  PRIMARY KEY (username)
);

INSERT INTO usuarios (username, password) VALUES
('carlos', 'password');

