cd ..
mvn clean install -Pproduction -Dmaven.test.skip
cd gui-web/
java -Dserver.port=8082 -Dcube.url=http://localhost:8080/cube -jar target/counter-ws-gui-1.1.0.jar
