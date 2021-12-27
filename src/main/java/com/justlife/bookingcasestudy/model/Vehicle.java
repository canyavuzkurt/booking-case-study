package com.justlife.bookingcasestudy.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vehicle extends BaseModel{

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToMany(mappedBy = "vehicle")
    @NotNull
    private Set<Cleaner> cleaners = new HashSet<>();

    public Vehicle(Driver driver) {
        this.driver = driver;
    }

    public boolean addCleaner(Cleaner cleaner) {

        //TODO migrate this 5 to properties and throw exception
        return this.cleaners.size() >= 5 ? cleaners.add(cleaner) : false;
    }
}
