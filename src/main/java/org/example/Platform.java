package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Platform {
    private static final DateTimeFormatter TIMP = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");

    private final Map<String, Examen> examene = new HashMap<>();
    private final Map<String, Student> studenti = new HashMap<>();
    private String nume_cale;

    public Platform(){}
    public Platform(String nume_cale) {
        this.nume_cale = nume_cale;
    }

    public void creeazaExamen(String nume, LocalDateTime startExamen, LocalDateTime finalExamen) {
        examene.put(nume, new Examen(startExamen, finalExamen));
    }
    public void registerStudent(String nume, String grupa) {
        studenti.put(nume, new Student(nume,grupa));
    }

    public void adaugaIntrebare(String tipIntrebare, String numeExamen, String autor, int dificultate, double punctajMaxim, String text, String raspunsCorect, LocalDateTime timp) {
        Examen examenCurent  = examene.get(numeExamen);
        if (examenCurent == null) return;

        Intrebare intrebare;
        if("multiple_choice".equals(tipIntrebare)){
            intrebare = new MultipleChoiceQuestion(text, autor, timp, dificultate, punctajMaxim, Optiune.valueOf(raspunsCorect));
        } else if("open_ended".equals(tipIntrebare)){
            intrebare = new OpenEndedQuestion(text, autor, timp, dificultate, punctajMaxim, raspunsCorect);
        }else{
            intrebare = new FillInTheBlankQuestion(text, autor, timp, dificultate, punctajMaxim, raspunsCorect);
        }

        examenCurent.adaugaIntrebare(intrebare);
    }

    public void istoricIntrebari(String numeExamen, String cale) throws IOException {
        Examen examenCurent = examene.get(numeExamen);
        if (examenCurent == null) return;

        try (FileWriter f = new FileWriter(cale)) {
            for (Intrebare i : examenCurent.getIntrebariSortateDupaIstoric()) {
                f.write(i.getTimp().format(TIMP) + " | " + i.getTextIntrebare() + " | " + i.raspunsCorect(i) + " | " + i.getDificultateIntrebare() + " | " + i.getPunctajMaximIntrebare() + " | " + i.getNumeAdministrator() + "\n");
            }
        }
    }


    public void printExamen(String numeExam, String cale) throws IOException {
        Examen examenCurent = examene.get(numeExam);
        if (examenCurent == null)
            return;

        try (FileWriter f = new FileWriter(cale)) {
            for (Intrebare i : examenCurent.getIntrebariSortateDupaDificultate()) {f.write(i.getPunctajMaximIntrebare() + " | " + i.getTextIntrebare() + " | " + i.getDificultateIntrebare() + " | " + i.raspunsCorect(i) + "\n");
            }
        }
    }



    public void trimiteExamen(String numeExamen, String numeStudent, List<String> raspunsuri, LocalDateTime timp) throws IOException {
        Examen examenCurent = examene.get(numeExamen);
        Student student = studenti.get(numeStudent);
        if (examenCurent == null || student == null)
            return;

        List<Object> raspunsuriTransformate = new ArrayList<>();
        List<Intrebare> intrebariSortate = examenCurent.getIntrebariSortateDupaDificultate();

        Intrebare intrebare;
        String raspuns;
        int i=0;
        while (i < raspunsuri.size() && i < intrebariSortate.size()) {
            intrebare = intrebariSortate.get(i);
            raspuns = raspunsuri.get(i);

            if (intrebare instanceof MultipleChoiceQuestion) {
                try {
                    raspunsuriTransformate.add(Optiune.valueOf(raspuns));
                } catch (IllegalArgumentException e) {
                    raspunsuriTransformate.add(null);
                }
            } else {
                raspunsuriTransformate.add(raspuns);
            }
            i++;
        }

        try {
            examenCurent.trimiteExamen(student, raspunsuriTransformate, timp);
        } catch (ExceptieSubmisieTimp e) {
            String consolaCale = "src/main/resources/" + nume_cale + "/console.out";
            try (FileWriter consola = new FileWriter(consolaCale, true)) {
                consola.write(timp.format(TIMP) + " | Submission outside of time interval for student " + numeStudent + "\n");
            }
        }
    }

    public void punctajStudent(String numeStudent, String numeExamen, LocalDateTime timp) throws IOException {
        Examen examenCurent = examene.get(numeExamen);
        Student studentCurent = studenti.get(numeStudent);


        if (examenCurent == null || studentCurent == null)
            return;

        Double punctaj = studentCurent.obtineRezultat(examenCurent);

        if (punctaj == null)
            punctaj = 0.0;

        String cale = "src/main/resources/" + nume_cale + "/console.out";

        try (FileWriter i = new FileWriter(cale, true)) {
           i.write(timp.format(TIMP) + " | The score of " + numeStudent + " for exam " + numeExamen + " is " + String.format("%.2f", punctaj) + "\n");
        }
    }

    public void arataRaport(String numeExamen, String nume_cale) throws IOException {
        Examen examenCurent = examene.get(numeExamen);

        if (examenCurent == null)
                return;

        examenCurent.generareRaport(nume_cale);
    }
}