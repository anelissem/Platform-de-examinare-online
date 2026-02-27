package org.example;

import java.time.LocalDateTime;

public class MultipleChoiceQuestion extends Intrebare {
    private Optiune raspunsCorect;

    public Optiune getRaspunsCorect() { return raspunsCorect; }

    public MultipleChoiceQuestion() {}

    public MultipleChoiceQuestion(String textIntrebare, String numeAdministrator, LocalDateTime timp, int dificulatate, double punctajMaximIntrebare, Optiune raspunsCorect) {
        super(textIntrebare, numeAdministrator, timp, dificulatate, punctajMaximIntrebare);
        this.raspunsCorect = raspunsCorect;
    }
    public <T> Corectitudine checkAnswer(T raspuns) {

        if (raspuns instanceof Optiune) {
            if(raspunsCorect.equals(raspuns))
                return Corectitudine.CORRECT;
            else
                return Corectitudine.WRONG;
        }
        return Corectitudine.WRONG;
    }

    public double punctaj(Corectitudine scor) {
        if(scor.equals(Corectitudine.CORRECT))
            return 1.0;
        else
            return 0.0;
    }
}