package com.justlife.bookingcasestudy.payload.response.modelresponse;

import com.justlife.bookingcasestudy.model.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicPersonResponse {

    private String fName;
    private String lName;

    public BasicPersonResponse(Person person) {

        this.setFName(person.getFName());
        this.setLName(person.getLName());
    }
}
