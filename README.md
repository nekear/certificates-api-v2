### Running docker
1. Initialize RSA keys:
```
1. cd src/main/resources/keys
2. openssl genpkey -algorithm RSA -out app.key -pkeyopt rsa_keygen_bits:2048
3. openssl rsa -pubout -in app.key -out app.pub
```

2. Then, simply run:
```bash
docker-compose up
```

### Rolling back the database using Liquibase
1. Add maven dependency
```xml
<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-maven-plugin</artifactId>
    <version>4.26.0</version>
</dependency>
```
2. Add Maven plugin
```xml
<plugin>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-maven-plugin</artifactId>
    <version>4.26.0</version>
    <dependencies>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.1.0</version>
        </dependency>
    </dependencies>
</plugin> 
```
3. Run command
```bash
mvnw liquibase:rollback \ 
  -Dliquibase.changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml \ 
  -Dliquibase.url=jdbc:mysql://localhost:3306/certificates_db \ 
  -Dliquibase.username=certificates_user \ 
  -Dliquibase.password=certificates \ 
  -Dliquibase.rollbackCount=1 \ 
  -f pom.xml
```

> Note: if dependency is present, Spring Boot will fail instantiating the liquibase bean by throwing the `AbstractArgumentCommandStep` exception.
