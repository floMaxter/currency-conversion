# Currency-conversion
A REST API for describing currencies and exchange rates. Allows you to view and edit lists of currencies and exchange rates, and calculate the conversion of arbitrary amounts from one currency to another.

#### Prerequisites: Prerequisites: [Java 21](https://jdk.java.net/21/), [Maven](https://maven.apache.org/), [JDBC](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/), [SQL-Lite](https://sqlite.org/), HTML/CSS for frontend

## Description
The application was implemented as part of [Sergey Zhukov's roadmap](https://zhukovsd.github.io/java-backend-learning-course/projects/simulation/).

The application is implemented on a layered MVC(S) architecture. Servlets act as controllers, queries to the database are made using JDBC, and the database is SQL-Lite.

The application contains one main page:
![Home page](images/main_page.png)

## Getting started
### 1. Clone the repository
```shell
git clone https://github.com/floMaxter/currency-conversion
cd currency-conversion
```
or download zip archive
https://github.com/floMaxter/currency-conversion/archive/refs/heads/main.zip

### 2. Assemble it .a war file using Maven
**Note:** The Maven Wrapper is already present in the project, so use the following commands to build

* Linux/macOS:
```bash
./mvnw clean package
```
* Windows
```shell
.\mvnw clean package
```

**Note:** Ready.The war file will be located in the `target/`

### 3. Expand it `.war` in Tomcat
* Copy `target/currency-conversion.war` to the `webapps` of Tomcat
* Run Tomcat
- Linux/maOS
```shell
./bin/startup.sh
```
- Windows
```shell
.\bin\startup.bat
```

### 4. Open app in browser
`http://localhost:8080/currency-conversion/`