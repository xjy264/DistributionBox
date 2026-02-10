CREATE TABLE IF NOT EXISTS course (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  score INT,
  times VARCHAR(255),
  state TINYINT(1),
  teacher_id INT
);
