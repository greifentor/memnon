java \
-Dspring.datasource.url=jdbc:mariadb://localhost:3306/memnon \
-Dspring.datasource.driverClassName=org.mariadb.jdbc.Driver \
-Dspring.datasource.username=memnon \
-Dspring.datasource.password=password \
-Dlogging.level.root=INFO \
-jar gui-swing/target/memnon-gui-swing-0.0.1.jar
