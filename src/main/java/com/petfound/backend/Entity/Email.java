// Java Program to Illustrate EmailDetails Class
package com.petfound.backend.Entity;

// Importing required classes
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor

// Class
public class Email {

    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
    private String usage;
}

