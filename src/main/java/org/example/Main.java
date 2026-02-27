package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    private static final String INPUT = "input.in";

    private static String formatare(String timp) {
        return timp.replace(" ", "-");
    }

    private static LocalDateTime parseTimestamp(String s) {
        return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm"));
    }

    private static String outputCale(String folder, String prefix, String examenName, String timp) {
        return "src/main/resources/" + folder + "/" + prefix + "_" + examenName + "_" + formatare(timp) + ".out";
    }

    private static void comanda(String linie, Platform platforma, String cale) throws IOException {
        String[] parti = linie.split(" \\| ");
        String comanda = parti[1];
        LocalDateTime timp = parseTimestamp(parti[0]);

        if ("create_exam".equals(comanda)) {
            platforma.creeazaExamen(parti[2], parseTimestamp(parti[3]), parseTimestamp(parti[4]));

        } else if ("submit_exam".equals(comanda)) {
            List<String> answers = new ArrayList<>(Arrays.asList(parti).subList(4, parti.length));
            platforma.trimiteExamen(parti[2], parti[3], answers, timp);
        }else if ("print_exam".equals(comanda)) {
            String outFile = outputCale(cale, "print_exam", parti[2], parti[0]);
            platforma.printExamen(parti[2], outFile);
        }else if ("add_question".equals(comanda)) {
            platforma.adaugaIntrebare(parti[2], parti[3], parti[4], Integer.parseInt(parti[5]), Double.parseDouble(parti[6]), parti[7], parti[8], timp);
        } else if ("list_questions_history".equals(comanda)) {
            String outFile = outputCale(cale, "questions_history", parti[2], parti[0]);
            platforma.istoricIntrebari(parti[2], outFile);
        } else if ("register_student".equals(comanda)) {
            platforma.registerStudent(parti[2], parti[3]);
        } else if ("show_student_score".equals(comanda)) {
            platforma.punctajStudent(parti[2], parti[3], timp);
        } else if ("generate_report".equals(comanda)) {
            String outFile = outputCale(cale, "exam_report", parti[2], parti[0]);
            platforma.arataRaport(parti[2], outFile);
        }
    }

    public static void main(String[] args) throws IOException {
        String caleTeste = args[0];

        if (args.length != 1) {
            System.out.println("Usage: java Main <test_folder>");
            return;
        }

        String cale = "src/main/resources/" + caleTeste + "/" + INPUT;

        Platform platforma= new Platform(caleTeste);
        List<String> linii = Files.readAllLines(Paths.get(cale));

        for (String linie : linii) {
            comanda(linie, platforma, caleTeste);
        }
    }
}