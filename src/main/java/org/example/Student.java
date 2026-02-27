package org.example;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private final String nume;
    private String grupa;
    private Map<Examen, Double> exameneScor = new HashMap<>();

    public Student(String nume, String grupa) {
        this.nume = nume;
        this.grupa = grupa;
    }

    public void adaugaScor(Examen examen, double scor) {
        exameneScor.put(examen, scor);
    }
    public Double obtineRezultat(Examen examen) {
        return exameneScor.get(examen);
    }

    public String getNume() { return nume; }
}
