package org.santiago.riwi.io.models;

public class Inscription {
        //Atributos de Inscription
    private int id;
    private Course course;
    private Student student;

        //Constructores de Inscription
    public Inscription() {}

        //Asignadores de atributos de Inscription (setters)
    public void setId(int id) {
        this.id = id;
    }
        public void setCourse(Course course) {
            this.course = course;
        }
            public void setStudent(Student student) {
                this.student = student;
            }


        //Lectores de atributos de Inscription (getters)
    public int getId() {
        return this.id;
    }
        public Course getCourse() {
            return this.course;
        }
            public Student getStudent() {
                return this.student;
            }

        //Métodos de Inscription
    @Override
    public String toString() {
        return "Inscription -> [" +
                "id: " + Integer.toBinaryString(this.id) +
                ". Course: " + this.course.getName() +
                ". Student: " + this.student.getName() + " " + this.student.getLastName() +
                ']';
    }
}
