package org.example;
import java.time.LocalDateTime;

public class ExceptieSubmisieTimp extends Exception{
    private LocalDateTime timp;
    private String numeStudent;

    public ExceptieSubmisieTimp(LocalDateTime timp, String numeStudent) {
        this.timp = timp;
        this.numeStudent = numeStudent;
    }

    public ExceptieSubmisieTimp() {}

    public String toString(){
        return timp + " | Submission outside of time interval for student " + numeStudent;
    }
}
