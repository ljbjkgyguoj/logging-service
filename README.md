Перед запуском приложения обновите креды базы данных в следующих файлах:

- src/main/resources/application.properties
- src/main/resources/db/liquibase.properties

После примените миграцию, выполнив команду:
```
mvn liquibase:update
```
Запуск тестов:
```
mvn test
```

После запуска приложение можно тестировать при помощи сваггера
http://localhost:8080/logging-service/swagger-ui/index.html#/

логи можно посмотреть в файле src/main/resources/log/application.log

