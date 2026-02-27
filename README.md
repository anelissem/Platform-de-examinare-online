
# Platformă de examinare online – Tema 2 POO

## Introducere
Acest proiect constă în implementarea unei platforme simplificate de examinare online, dezvoltată în limbajul **Java**, având ca scop aplicarea și consolidarea conceptelor fundamentale de **Programare Orientată pe Obiect**.

Aplicația procesează comenzi citite din fișiere text și permite administrarea examenelor, a întrebărilor asociate acestora, a studenților participanți și a evaluării automate a răspunsurilor.

---

## Funcționalități
- Crearea și gestionarea examenelor cu intervale de timp prestabilite
- Adăugarea și administrarea întrebărilor de tip:
  - Open-ended
  - Fill-in-the-blank
  - Multiple-choice
- Afișarea istoricului întrebărilor unui examen
- Afișarea structurii finale a examenului, sortată conform cerințelor
- Înregistrarea studenților în sistem
- Trimiterea examenelor și evaluarea automată a răspunsurilor
- Tratarea situațiilor excepționale privind trimiterea examenelor
- Afișarea scorurilor și generarea rapoartelor finale

---

## Descrierea fiecărei clase

### 📌 Main.java
Responsabilă de citirea fișierului de input, parsarea comenzilor și declanșarea acțiunilor corespunzătoare.

**Metode principale:**
- `comanda()` – primește o linie din fișierul de input, parsează comanda, deleagă sarcinile către metodele din `Platform.java` și construiește calea pentru fișierele de output.
- `main()` – primește ca argument fișierul de input, îl citește și oferă funcționalitatea programului prin crearea obiectului `Platform`, care va gestiona comenzile citite.

**Metode auxiliare:**
- `formatare()`
- `parseTimestamp()`
- `outputCale()`  
Utilizate pentru parsare, formatare și generarea căilor pentru directoarele de output.

---

### 📌 Platform.java
Gestionează comenzile primite și tratează excepțiile apărute în timpul lucrului cu fișiere (IO), precum și alte excepții specifice aplicației.

---

### 📌 Examen.java
Modelează un examen prin gestionarea întrebărilor.  
Se ocupă de tratarea excepției `ExcepțieSubmisieTimp.java` și implementează metodele cerute în enunțul temei.

---

### 📌 Intrebare.java și subclasele sale
Modelează diferite tipuri de întrebări:
- `OpenEndedQuestion`
- `MultipleChoiceQuestion`
- `FillInTheBlankQuestion`

---

### 📌 Enums
- `Corectitudine.java`
- `Opțiune.java`  

Utilizate pentru evaluarea răspunsurilor și gestionarea punctajelor asociate acestora.

---

### 📌 Notabil.java
Interfață care permite implementarea mecanismului de punctare a întrebărilor.

---

### 📌 Student.java
Modelează un student și ține evidența punctajului obținut.

---
 ## Colecții și motivația alegerii lor

### 📌 Platform.java – HashMap
În clasa `Platform.java` am utilizat colecția `HashMap` atât pentru gestionarea examenelor, cât și a studenților, datorită eficienței ridicate.

- Operațiile de **adăugare** și **căutare** (cele mai frecvente) au complexitate **O(1)**.
- O alternativă ar fi fost `TreeSet`, însă aceasta oferă complexitate **O(log N)** pentru aceleași operații.
- Nu a fost necesară păstrarea instanțelor sortate.
- Atât examenele, cât și studenții sunt identificați prin mai multe atribute, ceea ce face `HashMap` o alegere potrivită.

---
### 📌 Student.java – HashMap
În clasa `Student.java` am utilizat colecția `HashMap` pentru a gestiona scorurile obținute la examene de către studenți, deoarece trebuia ca această colecție să
rețină atât numele examenului, cât și scorul obținut de student la acel examen, nefiind necesară o anumită ordonare, iar prin `HashMap` gestionează eficient cătarea scorului
obținut de student, folosind ca cheie numele examenului, care e unică.

---

### 📌 Examen.java – TreeSet (întrebări sortate)
Pentru gestionarea întrebărilor dintr-un examen am utilizat colecția `TreeSet`, pentru a le menține **sortate permanent**.

- Întrebările sunt ordonate după **dificultate**, iar în caz de egalitate, **alfabetic după conținut**.
- Sortarea constantă este necesară deoarece metoda `generareRaport()` poate fi apelată oricând.
- Utilizarea unui `ArrayList` ar fi fost ineficientă, deoarece ar fi necesitat sortări repetate:
  - fie la fiecare inserare,
  - fie la fiecare apel al metodei.
- `TreeSet` asigură inserarea în **O(log N)**, menținând automat elementele sortate.
- A fost implementat un `Comparator` personalizat, ușor de realizat, bazat pe compararea de valori `int` și `String`.
- La nivel conceptual, implementarea a fost gândită inițial ca un **arbore AVL**, iar echivalentul acestuia în Java este `TreeSet`.

---

### 📌 Examen.java – ArrayList (istoric întrebări)
Pentru istoricul întrebărilor am utilizat colecția `ArrayList`.

- Nu era necesar ca întrebările să fie sortate constant.
- Criteriul de comparare se baza pe **timestamp-uri** și `String-uri`, ceea ce nu justifica utilizarea unui `TreeSet`.
- A fost importantă **ordinea de inserare**, nu sortarea, deoarece istoricul trebuia păstrat exact așa cum s-au desfășurat evenimentele.

---

### 📌 Examen.java – TreeMap (punctaje studenți)
Punctajele studenților au fost gestionate folosind colecția `TreeMap`.

- Studenții sunt identificați atât după **nume**, cât și după **punctaj**.
- A fost necesară menținerea unei **ordonări alfabetice permanente**.
- `TreeMap` oferă această sortare implicit, fiind astfel cea mai potrivită alegere.

---
## Alte aspecte ale implementării

### Genericitate
Am folosit tipuri generice acolo unde a fost nevoie de flexibilitate.

- Metoda `checkAnswer(T raspuns)` din clasele de întrebări (`Intrebare`, `MultipleChoiceQuestion` etc.) este generică, deoarece răspunsurile pot fi de tipuri diferite (`String`, `Optiune`).
- În clasa `Examen`, metoda `getIndex(List<T> lista, int index)` este generică și permite obținerea unui element dintr-o listă indiferent de tipul acesteia.

---

### Input / Output
Pentru partea de I/O am lucrat doar cu fișiere.

- Comenzile sunt citite din fișierul `input.in` în `Main.java`.
- Rapoartele sunt scrise în fișiere folosind `FileWriter` (raport examene, istoric întrebări și output-ul pentru consolă).
- Fișierele de output sunt organizate în foldere separate pentru fiecare test.
- Pentru denumirea fișierelor am folosit timestamp-uri de forma `dd-MM-yyyy-HH-mm`, pentru a evita suprascrierea acestora.

---

### Excepții
Am tratat atât excepțiile standard, cât și unele definite de mine.

- `ExceptieSubmisieTimp` este o excepție personalizată, aruncată atunci când un examen este trimis în afara timpului permis.
- Excepțiile de tip I/O sunt tratate folosind `try-with-resources`.
- La întrebările de tip *multiple choice* pot apărea erori la conversia răspunsurilor, motiv pentru care am tratat `IllegalArgumentException`.

---

### Principii OOP
Implementarea respectă conceptele de bază din programarea orientată pe obiect.

- **Abstractizare**: clasa abstractă `Intrebare` conține comportamentul comun tuturor tipurilor de întrebări.
- **Încapsulare**: toate atributele sunt private și accesate prin getteri și setteri.
- **Moștenire**: clasele `MultipleChoiceQuestion`, `OpenEndedQuestion` și `FillInTheBlankQuestion` extind clasa `Intrebare`.
- **Polimorfism**:
  - metoda `checkAnswer()` este suprascrisă în fiecare subclasă;
  - calculul punctajului diferă în funcție de tipul întrebării.
- **Interfață**: `Notabil` definește modul de calcul al punctajului.
- **Compoziție**: clasa `Examen` conține colecții de `Intrebare` și `Student`.


---

## Rulare
Aplicația se rulează specificând ca argument numele testului:
```bash
java Main <nume_test>
