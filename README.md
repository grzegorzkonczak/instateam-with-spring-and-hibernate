# Instateam with Spring and Hibernate
Project team management web application using Spring with Hibernate.

Project initial files (like html mockups and initial css styling) where provided by project idea creators (https://teamtreehouse.com).

Project was meant to follow a set of requirements. Base requirements were as follows:

1. Project contains at least four packages for application code:
  - web or controller
  - dao
  - service
  - model or entity
2. Role class is present with all fields, getters, setters, and a default constructor
3. Collaborator class is present with all fields, getters, setters, and a default constructor
4. Project class is present with all fields, getters, setters, and a default constructor
5. JPA annotations added to all model classes, allowing for persistance of all required fields to database tables
6. Proper collection mapping is used for:
  - Collaborator.role
  - Project.rolesNeeded
  - Project.collaborators
7. DAO layer includes interfaces and implementations with Hibernate code
8. Service layer includes interfaces and implementations that leverage DAO layer
9. Controllers access data via the service layer
10. Controllers for Role, Collaborator and Project has all methods necessary for for adding, viewing and editing given entity
11. Correct views for all controller methods are created using Thymeleaf
12. All controller methods that return view names add the necessary data to ModelMap
13. All POST methods correctly redirect
14. Projects, roles and collaborators cannot be created or edited without providing data for required fields
15. Views contain no broken links
16. All views are present in the application:
  - Project index view
  - Project detail view
  - Project role assign view
  - Project create/edit view
  - Role index view
  - Role detail view
  - Role create/edit view
  - Collaborator index view
  - Collaborator detail view
  - Collaborator create/edit view
  
Additional requirements (also implemented):

1. Project class includes a start date that is properly mapped to a database column
2. Project, roles and collaborators can be deleted, and all related relationships are removed to preserve data integrity
3. Common DAO implementation code is extracted to an abstract class that the DAO implementations extend
4. Project index view includes start date
5. Projects are sorted chronologically by start date


To check my other work please go to:

- https://github.com/grzegorzkonczak/instateam-with-spring-and-hibernate - Project team management web application using Spring with Hibernate.
- https://github.com/grzegorzkonczak/todo-api-with-spark - REST API for "TODO" application using Spark framework
- https://github.com/grzegorzkonczak/analyze-public-data-with-hibernate - Console application for managing Countries data using Hibernate and H2 file database.
- https://github.com/grzegorzkonczak/countries-of-the-world-with-spring - Spring web application that displays information about 5 countries
- https://github.com/grzegorzkonczak/personal-blog - Simple web blog application built using Spark Framework
- https://github.com/grzegorzkonczak/Soccer-League-Organizer - Console based soccer team management application
- https://github.com/grzegorzkonczak/how_many_in_jar_game - Console based implementation of "How many in jar" game
