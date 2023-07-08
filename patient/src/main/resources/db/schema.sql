CREATE TABLE patients (
  id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  birth_date DATE,
  gender VARCHAR(1),
  address VARCHAR(255),
  phone_number VARCHAR(20)
);