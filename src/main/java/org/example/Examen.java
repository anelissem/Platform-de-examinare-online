package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Examen {
    private String denumireExamen;
    private LocalDateTime startExamen;
    private LocalDateTime finalExamen;

    private TreeSet<Intrebare> intrebariSortateDupaDificultate;
    private final List<Intrebare> intrebariSortateDupaIstoric = new ArrayList<>();
    private final Map<Student, Double> studentiPunctaj = new TreeMap<>(Comparator.comparing(Student::getNume));

    public String getDenumireExamen() { return denumireExamen; }
    public LocalDateTime getStartExamen() { return startExamen; }
    public LocalDateTime getFinalExamen() { return finalExamen; }
    public Map<Student, Double> getStudentiPunctaj() { return studentiPunctaj;}

    public void setDenumireExamen(String denumireExamen) { this.denumireExamen = denumireExamen; }
    public void setStartExamen( LocalDateTime startExamen) { this.startExamen = startExamen; }
    public void setFinalExamen( LocalDateTime finalExamen) { this.finalExamen = finalExamen; }

    public Examen(){}
    public Examen(LocalDateTime startExamen, LocalDateTime finalExamen) {
        this.startExamen = startExamen;
        this.finalExamen = finalExamen;
        this.intrebariSortateDupaDificultate = new TreeSet<>(Comparator.comparingInt(Intrebare::getDificultateIntrebare).thenComparing(Intrebare::getTextIntrebare).thenComparing(intrebare -> System.identityHashCode(intrebare)));
    }


    public void adaugaIntrebare(Intrebare intrebare) {
        intrebariSortateDupaIstoric.add(intrebare);
        intrebariSortateDupaDificultate.add(intrebare);
    }

    public List<Intrebare> getIntrebariSortateDupaDificultate() {
        return new ArrayList<>(intrebariSortateDupaDificultate);
    }
    public List<Intrebare> getIntrebariSortateDupaIstoric() {
        List<Intrebare> listaSortata = new ArrayList<>(intrebariSortateDupaIstoric);
        listaSortata.sort(Comparator.comparing(Intrebare::getTimp).thenComparing(Intrebare::getNumeAdministrator));
        return listaSortata;
    }

    public  <T> T getIndex(List<T> lista, int index) {
        if (index >= 0 && index < lista.size()) {
            return lista.get(index);
        }
        return null;
    }

    public void trimiteExamen(Student student, List<?> raspunsuri, LocalDateTime timp) throws ExceptieSubmisieTimp {
        List<Intrebare> intrebariSortate = getIntrebariSortateDupaDificultate();
        double punctajTotal = 0;

        if (timp.isBefore(startExamen) || timp.isAfter(finalExamen)) {
            throw new ExceptieSubmisieTimp(timp, student.getNume());
        }

        Intrebare intrebareCurenta;
        int i;

        for (i = 0; i < intrebariSortate.size(); i++) {
            Object raspuns = getIndex(raspunsuri, i);
            intrebareCurenta = intrebariSortate.get(i);
            Corectitudine corect  = intrebareCurenta.checkAnswer(raspuns);

            double procentaj = intrebareCurenta.punctaj(corect);
            punctajTotal += intrebareCurenta.getPunctajMaximIntrebare() * procentaj;
        }

        studentiPunctaj.put(student, punctajTotal);
        student.adaugaScor(this, punctajTotal);
    }



    public void generareRaport(String filePath) throws IOException {
        try (FileWriter f = new FileWriter(filePath)) {
            for (Map.Entry<Student, Double> i : studentiPunctaj.entrySet()) {
                double scor = i.getValue();
                Student student = i.getKey();
                f.write(student.getNume() + " | " + String.format("%.2f", scor) + "\n");
            }
        }
    }
}