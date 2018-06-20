javac -d bin -cp lib/json-simple-1.1.jar:lib/gson-2.6.2.jar src/aaa/*.java
java -cp .:lib/json-simple-1.1.jar:lib/gson-2.6.2.jar:bin aaa.Program
