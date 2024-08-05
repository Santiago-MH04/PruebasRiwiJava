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
);

create table if not exists marks(
	`course_id` INT,
    `student_id` INT,
    `mark` INT NOT NULL DEFAULT 0 -- As long as a partial mark has not been upgraded, it is 0 until notice
);