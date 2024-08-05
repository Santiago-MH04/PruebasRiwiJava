package org.santiago.riwi.io.models;

public class Mark {
        //Atributos de Mark
    private Student student;
    private Course course;
    private int mark;

        //Constructores de Mark
    public Mark() {}

        //Asignadores de atributos de Mark (setters)
    public void setStudent(Student student) {
        this.student = student;
    }
        public void setCourse(Course course) {
            this.course = course;
        }
            public void setMark(int mark) {
                if(mark >= 0 && mark <= 100){
                    this.mark = mark;
                } else {
                    System.out.println("You cannot grade a student with such a mark at this school");
                }
            }

        //Lectores de atributos de Mark (getters)
    public Student getStudent() {
        return this.student;
    }
        public Course getCourse() {
            return this.course;
        }
            public int getMark() {
                return this.mark;
            }

        //Métodos de Mark

    @Override
    public String toString() {
        return "Grade -> [" +
                "Student: " + this.student.getName() + " " + this.student.getLastName() +
                ". Course: " + this.course.getName() +
                ". Mark: " + this.mark +
                ']';
    }
}
