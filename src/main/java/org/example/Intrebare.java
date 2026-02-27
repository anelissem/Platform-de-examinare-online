package org.example;
import java.time.LocalDateTime;

public abstract class Intrebare implements Notabil{
    private String textIntrebare;
    private String numeAdministrator;
    private LocalDateTime timp;
    private int dificultateIntrebare;
    private double punctajMaximIntrebare;

    public Intrebare(){}

    public String getTextIntrebare() {
        return textIntrebare;
    }
    public void setTextIntrebare(String textIntrebare) {
        this.textIntrebare = textIntrebare;
    }

    public String getNumeAdministrator() {
        return numeAdministrator;
    }
    public void setNumeAdministrator(String numeAdministrator) {
        this.numeAdministrator = numeAdministrator;
    }

    public LocalDateTime getTimp() {
        return timp;
    }
    public void setTimp(LocalDateTime timp) {
        this.timp = timp;
    }
    public int getDificultateIntrebare() {
        return dificultateIntrebare;
    }

    public void setDificultateIntrebare(int dificultateIntrebare) {
        this.dificultateIntrebare = dificultateIntrebare;
    }
    public double getPunctajMaximIntrebare() {
        return punctajMaximIntrebare;
    }

    public Intrebare(String text, String autor, LocalDateTime timp, int dificultate, double punctajMaxim){
        this.textIntrebare = text;
        this.numeAdministrator = autor;
        this.timp = timp;
        this.dificultateIntrebare = dificultate;
        this.punctajMaximIntrebare = punctajMaxim;
    }

    public String raspunsCorect(Intrebare intrebare) {
        if (intrebare instanceof MultipleChoiceQuestion) {
            return ((MultipleChoiceQuestion) intrebare).getRaspunsCorect().name();
        } else if (intrebare instanceof OpenEndedQuestion) {
            return ((OpenEndedQuestion) intrebare).getRaspunsCorect();
        } else if (intrebare instanceof FillInTheBlankQuestion) {
            return ((FillInTheBlankQuestion) intrebare).getRaspunsCorect();
        }
        return "";
    }
    public abstract <T> Corectitudine checkAnswer(T raspuns);
}
