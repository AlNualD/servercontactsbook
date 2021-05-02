# Книга контактов
## Способы запуска программы 
* 1 вариант  
Посетить https://servercontactsbook.herokuapp.com/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
Приложение развернуто, подключено к небольшой БД с данными 
* 2 вариант  
Запустить build/libs/servercontactsbook-1.0.0.jar который автоматически подключится к удаленной БД из варианта выше 
* 3 вариант  
Выделить БД PostgresSQL и пользователя с правом создавать БД  
переопределить в файле src/main/resources/application.properties  
spring.datasource.url=jdbc:postgresql://<хост вервера>:5432/<название БД>  
spring.datasource.username=  
spring.datasource.password=  
spring.jpa.hibernate.ddl-auto=create-drop  
сборка/запуск через gradle
