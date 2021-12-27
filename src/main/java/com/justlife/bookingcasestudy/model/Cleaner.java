package com.justlife.bookingcasestudy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cleaner extends Person{

    @ManyToOne
    private Vehicle vehicle;

    public Cleaner(String fName, String lName, Vehicle vehicle) {
        super(fName, lName);
        this.vehicle = vehicle;
    }
}
