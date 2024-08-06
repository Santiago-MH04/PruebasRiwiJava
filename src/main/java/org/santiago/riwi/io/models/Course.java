package org.santiago.riwi.io.models;

public class Course {
        //Atributos de Course
    private int id;
    private String name;
    private int attendants;


        //Constructores de Course
    public Course() {}

        //Asignadores de atributos de Course (setters)
    public void setId(int id) {
        this.id = id;
    }
        public void setName(String name) {
            this.name = name;
        }
            public void setAttendants(int attendants) {
            this.attendants = attendants;
        }

        //Lectores de atributos de Course (getters)
    public int getId() {
        return this.id;
    }
        public String getName() {
            return this.name;
        }
            public int getAttendants() {
                return this.attendants;
            }

        //Métodos de Course
    @Override
    public String toString() {

        return "Course -> [" +
                "id: " + this.id +
                ". Name: " + this.name +
                " " + this.attendants + " attendants" + ']';
    }
}
