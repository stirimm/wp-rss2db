FROM gcr.io/distroless/java21-debian12:nonroot

ADD target/wp-rss2db-1.0-SNAPSHOT-jar-with-dependencies.jar /wp-rss2db.jar

CMD [ "/wp-rss2db.jar" ]