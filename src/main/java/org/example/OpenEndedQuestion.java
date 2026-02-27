package org.example;

import java.time.LocalDateTime;

public class OpenEndedQuestion extends Intrebare {
    private String raspunsCorect;

    public OpenEndedQuestion(String textIntrebare, String numeAdministrator, LocalDateTime timp, int dificulatate, double punctajMaximIntrebare, String raspunsCorect) {
        super(textIntrebare, numeAdministrator, timp, dificulatate, punctajMaximIntrebare);
        this.raspunsCorect = raspunsCorect;
    }

    public String getRaspunsCorect() {
        return raspunsCorect;
    }

    public void setRaspunsCorect(String raspunsCorect) {
        this.raspunsCorect = raspunsCorect;
    }

    public <T> Corectitudine checkAnswer(T raspuns) {
        if (!(raspuns instanceof String))
            return Corectitudine.WRONG;

        String answer = (String) raspuns;

        if (answer.equals(raspunsCorect))
            return Corectitudine.CORRECT;

        int len1 = answer.length();
        int len2 = raspunsCorect.length();
        int maxim = (int) Math.ceil(len2 * 0.3);
        int modul = Math.abs(len1 - len2);

        boolean ok = raspunsCorect.contains(answer) || answer.contains(raspunsCorect);

        if (modul <= maxim && ok) {
            return Corectitudine.PARTIAL;
        }

        return Corectitudine.WRONG;
    }

    public double punctaj(Corectitudine scor) {
        switch (scor) {
            case CORRECT: return 1.0;
            case PARTIAL: return 0.7;
            case WRONG: return 0.0;
            default: return 0.0;
        }
    }
}