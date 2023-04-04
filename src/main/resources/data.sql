CREATE TABLE IF NOT EXISTS rol (
  id INT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL
);

INSERT INTO rol (id, nombre) VALUES (1, 'ROLE_ADMIN');

CREATE TABLE IF NOT EXISTS usuarios (
  id INT PRIMARY KEY,
  edad INT,
  email VARCHAR(255) NOT NULL,
  name VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  rol_id INT NOT NULL,
  FOREIGN KEY (rol_id) REFERENCES rol (id)
);

INSERT INTO usuarios (id, edad, email, name, password, rol_id)
VALUES (1, 25, 'cardonac187@gmail.com', 'Carlos', '123', 1);