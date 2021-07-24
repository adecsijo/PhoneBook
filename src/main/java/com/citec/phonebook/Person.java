package com.citec.phonebook;

import javafx.beans.property.SimpleStringProperty;

public class Person {
    

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty phone;

    public Person(String lastName, String firstName, String phone) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone = new SimpleStringProperty(phone);
    }
    
    public Person() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.phone = new SimpleStringProperty("");
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    
    public String getLastName() {
        return lastName.get();
    }
    
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhone() {
        return phone.get();
    }
    
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}
