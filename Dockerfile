FROM tomcat:10.1-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY src/main/webapp /usr/local/tomcat/webapps/ROOT
COPY src/main/java /tmp/src
COPY src/main/webapp/WEB-INF/lib /tmp/lib

RUN mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/classes && \
    javac -cp "/usr/local/tomcat/lib/*:/tmp/lib/*" \
    -d /usr/local/tomcat/webapps/ROOT/WEB-INF/classes \
    $(find /tmp/src -name "*.java")

EXPOSE 8080

CMD ["catalina.sh", "run"]