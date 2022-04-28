:: temp file to run brokers

:: Edit the next line, and enter your path
set path = C:\Users\user\Downloads\tika-app-2.3.0.jar

javac *.java -classpath C:\Users\user\Downloads\tika-app-2.3.0.jar
START "Broker1" CMD /k "java Broker1 -classpath " %path%
START "Broker2" CMD /k "java Broker2 -classpath " %path%
START "Broker3" CMD /k "java Broker3 -classpath " %path%

START "Client1" CMD /k "java Client1 -classpath " %path%

