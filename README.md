# Txt2Xlsx

A Java-based Excel report generator I made for my mom for CBSE/Class 10 and 12 student results. 😁

---
## Features

* Master sheet containing all student data
* Subject-wise statistics
* Percentage range distribution
* Top 10 Science students
* Top 10 Commerce students
* Packaged as a Windows executable (.exe)

## Technologies Used

* Java
* Apache POI
* Maven
* Launch4j
---
## How to Run :
### Option 1 — Run the EXE
Double click:

```text
Txt2Xlsx.exe
```

The generated Excel file will appear in the project folder.

### Option 2 — Compile with Maven

#### Build Instructions:

Compile with Maven from the root directory of project using:

```bash
mvn package
```

The runnable JAR will be generated inside:

```
target/
```
You can then execute this command to run the JAR file:
```
java -jar .\target\Txt2Xlsx-1.0-SNAPSHOT.jar  
```

---

## Project Structure

```text
src/main/java/
├── Main.java
├── ExcelGenerator.java
├── Parser.java
├── TxtReader.java
├── Student.java
└── Subject.java
```

---
## Input
I've added a `sample.txt` file to the repo, this program can only parse text files in the format given below. This is the format used by CBSE.
```
12345678   M PLACEHOLDER NAME                               301     041     042     043     083              A1 A2 A1    PASS   092  A1 060  B2 062  B2 073  B1 065  C2   
                                                                      
87654321   F RANDOM NAME                                    301     041     042     043     083              A1 A2 A1    PASS   097  A1 080  A2 075  A2 067  B2 080  B2        

```
## Output

Generated Excel workbook contains:

* Master
* Subject PI & Avg
* Percentage Range Dist
* Top 10 Science
* Top 10 Commerce

---