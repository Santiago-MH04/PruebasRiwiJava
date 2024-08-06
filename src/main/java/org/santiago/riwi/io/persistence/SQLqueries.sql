create database RiwiAcademyDB;
use RiwiAcademyDB;

create table if not exists students(
	`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(90) NOT NULL,
    `last_name` VARCHAR(90) NOT NULL,
    `email` VARCHAR(45) UNIQUE NOT NULL,
    `status` ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'
);

create table if not exists courses(
	`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(30) UNIQUE NOT NULL,
    `attendants` INT DEFAULT 0	-- If a course has no attendants, can be removed
);

create table if not exists inscriptions(
	`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	`course_id` INT,
    `student_id` INT,	-- A student can only be signed into three courses, tops
    FOREIGN KEY (`course_id`) references courses(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`student_id`) references students(`id`) ON DELETE CASCADE
    -- CHECK (COUNT(`student_id`) >= 3)	-- Let's try and see if it works like this, I don't think it will work. Nonetheless, it's all functioning in Java
);

create table if not exists marks(
	`course_id` INT,
    `student_id` INT,
    `mark` INT NOT NULL DEFAULT 0,	-- As long as a partial mark has not been upgraded, it is 0 until notice
    CHECK (`mark` >= 0 AND `mark` <= 100),	-- So it can throw an error if the user sets the mark outside of this range
    FOREIGN KEY (`course_id`) references courses(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`student_id`) references students(`id`) ON DELETE CASCADE
);

INSERT INTO students (`name`, `last_name`, `email`, `status`) VALUES ('Viola', 'Ingilson', 'vingilson0@bloomberg.com', 'INACTIVE');
INSERT INTO students (`name`, `last_name`, `email`, `status`) VALUES ('Fern', 'Diben', 'fdiben1@macromedia.com', 'ACTIVE');
INSERT INTO students (`name`, `last_name`, `email`, `status`) VALUES ('Rutledge', 'Marcum', 'rmarcum2@apple.com', 'ACTIVE');
INSERT INTO students (`name`, `last_name`, `email`, `status`) VALUES ('Wayland', 'Ridd', 'wridd3@godaddy.com', 'INACTIVE');
INSERT INTO students (`name`, `last_name`, `email`, `status`) VALUES ('Nomi', 'Mawd', 'nmawd4@buzzfeed.com', 'ACTIVE');

INSERT INTO courses (`name`, `attendants`) VALUES ('Music', 10);
INSERT INTO courses (`name`, `attendants`) VALUES ('Photography', 5);
INSERT INTO courses (`name`, `attendants`) VALUES ('Drawing', 15);
INSERT INTO courses (`name`, `attendants`) VALUES ('Mathematics', 20);
INSERT INTO courses (`name`, `attendants`) VALUES ('Latin', 4);
INSERT INTO courses (`name`, `attendants`) VALUES ('Sports', 50);
INSERT INTO courses (`name`, `attendants`) VALUES ('Java programming', 20);
INSERT INTO courses (`name`, `attendants`) VALUES ('Backend development', 20);
INSERT INTO courses (`name`, `attendants`) VALUES ('Logic', 13);
INSERT INTO courses (`name`, `attendants`) VALUES ('Economics', 14);

INSERT INTO inscriptions (course_id, student_id) VALUES (1, 1);
INSERT INTO inscriptions (course_id, student_id) VALUES (2, 2);
INSERT INTO inscriptions (course_id, student_id) VALUES (3, 3);
INSERT INTO inscriptions (course_id, student_id) VALUES (4, 4);
INSERT INTO inscriptions (course_id, student_id) VALUES (5, 5);
INSERT INTO inscriptions (course_id, student_id) VALUES (6, 3);
INSERT INTO inscriptions (course_id, student_id) VALUES (7, 4);
INSERT INTO inscriptions (course_id, student_id) VALUES (8, 1);

INSERT INTO marks (course_id, student_id, mark) VALUES (1, 1, 10);
INSERT INTO marks (course_id, student_id, mark) VALUES (2, 4, 40);
INSERT INTO marks (course_id, student_id, mark) VALUES (3, 5, 50);
INSERT INTO marks (course_id, student_id, mark) VALUES (4, 2, 25);
INSERT INTO marks (course_id, student_id, mark) VALUES (5, 3, 30);
INSERT INTO marks (course_id, student_id, mark) VALUES (6, 1, 60);
INSERT INTO marks (course_id, student_id, mark) VALUES (7, 4, 100);
INSERT INTO marks (course_id, student_id, mark) VALUES (8, 2, 80);
INSERT INTO marks (course_id, student_id, mark) VALUES (9, 3, 90);
INSERT INTO marks (course_id, student_id, mark) VALUES (10, 5, 70);






