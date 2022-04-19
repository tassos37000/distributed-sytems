:: temp file to run brokers

javac *.java
START "Broker1" CMD /k "java Broker1"
START "Broker2" CMD /k "java Broker2"
START "Broker3" CMD /k "java Broker3"
