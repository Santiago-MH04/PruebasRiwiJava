package org.santiago.riwi.io.models;

public class Student {
        //Atributos de Student
    private int id;
    private String name;
    private String lastName;
    private String email;

    private Status status;

    private int insAvailable;
    private static final int MAX_INSCRIPTIONS = 3;



        //Constructores de Student
    public Student() {}

        //Asignadores de atributos de Student (setters)
    public void setId(int id) {
        this.id = id;
    }
        public void setName(String name) {
            this.name = name;
        }
            public void setLastName(String lastName) {
                this.lastName = lastName;
            }
                public void setEmail(String email) {
                    this.email = email;
                }
                    public void setStatus(Status status) {
                        this.status = status;
                    }
                        public void calculateInsAvailable(int inscription) {    //Para verificar que un estudiante no tenga más de tres cursos matriculados
                            this.insAvailable = MAX_INSCRIPTIONS - inscription;
                        }

        //Lectores de atributos de Student (getters)
    public int getId() {
        return this.id;
    }
        public String getName() {
            return this.name;
        }
            public String getLastName() {
                return this.lastName;
            }
                public String getEmail() {
                    return this.email;
                }
                    public Status getStatus() {
                        return this.status;
                    }


        //Métodos de Student
    @Override
    public String toString() {
        return "Student -> [" +
                "id: " + this.id +
                ". Name: " + this.name +
                ". Last name: " + this.lastName +
                ". Email: " + this.email +
                ". Status: " + this.status + ']';
    }
}
