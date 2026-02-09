CREATE TABLE IF NOT EXISTS student_course (
  student_id INT NOT NULL,
  course_id INT NOT NULL,
  PRIMARY KEY (student_id, course_id)
);
