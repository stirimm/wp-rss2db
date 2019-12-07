FROM openjdk:8-jdk

RUN mkdir /app

ADD target/wp-rss2db-1.0-SNAPSHOT-jar-with-dependencies.jar /app/wp-rss2db.jar

CMD [ "java", "-jar", "/app/wp-rss2db.jar" ]