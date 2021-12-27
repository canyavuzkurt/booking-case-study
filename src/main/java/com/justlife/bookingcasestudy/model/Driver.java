package com.justlife.bookingcasestudy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Driver extends Person{

    @OneToOne(mappedBy = "driver", fetch = FetchType.LAZY)
    private Vehicle vehicle;

    public Driver(String fName, String lName) {
        super(fName, lName);
    }
}
