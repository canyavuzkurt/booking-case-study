package com.justlife.bookingcasestudy.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends BaseModel{

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cleaner_appointment",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "cleaner_id"))
    private List<Cleaner> cleaners;
    private LocalDateTime start;
    private LocalDateTime end;
}
