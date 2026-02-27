# BONUS – Catalog de note și medii finale

## Idee generală

O funcționalitate utilă care poate fi adăugată platformei este introducerea unei clase noi, numită **Catalog**, care să se ocupe de gestionarea notelor finale ale studenților, pe baza rezultatelor obținute la mai multe examene.

Scopul acestei clase este să ofere o imagine de ansamblu asupra performanței fiecărui student, nu doar pe un singur examen, ci pe întreaga activitate academică.

---

## Îmbunătățiri

Clasa `Catalog` adaugă:
- calcularea automată a mediei finale;
- ponderarea diferită a examenelor;
- posibilitatea de a estima ce notă este necesară la un examen viitor;
- listări și filtrări avansate ale studenților.

---

## Reguli de calcul pentru procentaje

Fiecare examen contribuie diferit la media finală, în funcție de tipul predominant de întrebări:

- **OpenEndedQuestions** – 50%
- **FillInTheBlankQuestions** – 30%
- **MultipleChoiceQuestions** – 20%

Procentajele sunt ajustate dinamic în funcție de numărul total de examene susținute de student, astfel încât suma lor să fie mereu 100%.

---

## Clasa `Catalog`

### Atribute

- `Map<String, StudentRecord>` – reține informațiile despre fiecare student:
    - numele;
    - media finală curentă;
    - numărul de examene date;
    - examenele susținute și ponderile acestora.
- `List<Exam>` – lista tuturor examenelor existente în platformă.

---

### Metode principale
-
  -o metodă care apelată returnează nota (Double) pe care ar trebui să o ia un student la următorul examen pentru a obține o anumită medie finală/ notă finală, aceasta are ca parmetri: tip (majoritar OpenEndedQuestions etc), un Double nota finală dorită
  - o metodă care listează/afișează studenții în funcție de medie/nota finală (la momentul actual), în caz de egalitate, sortează alfabetic dupe nume 
  - o metodă care listează notele finale ale studentițiilor dintr-o grupă/a tuturor studențiilor cu numele X/ cu cel puțin y examene date (primește ca parametru un String grupa sau un String numele sau un Int numărul de examene - genericitate)
  - o metodă care primesțe ca parametri: un obiect de tip Examen, un Double procentaj ce reprezintă procentajul pe care nota obținută la acel examen îl reprezintă din nota finală (altă metodă va calcula procentajul),
    metoda va modifica dinamic procentajul celorlalte examene deja inregistrate pentru acel student ;
  - o metoda ce calculează procentajul: se va baza pe o formula calculată pe baza numărului total de examene înregistrate pentru acel student și prioritatea examenului calculată astfel:
  majoritar FillInTheBlankQuestions 30%, majoritar OpenEndedQuestions 50%, majoritar MultipleChoiceQuestions 20% din nota finala (100 puncte) ;

---
## Comenzi noi adăugate

Pentru a putea folosi această funcționalitate, pot fi adăugate următoarele comenzi:

- `timestamp | listare_catalog |`
- `timestamp | afiseaza_catalog_criteriu | <criteriu> |`
- `timestamp | nota_necesara | <numeStudent> | <notaDorita> |`

Acestea sunt interpretate de clasa `Platform`, care apelează metodele corespunzătoare din `Catalog`.

---

