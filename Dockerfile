FROM ubuntu:16.04

# Install dependencies
RUN apt-get update && \
apt-get install -y git build-essential curl wget software-properties-common

# Install JDK 8
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/ && \
    rm -rf /var/cache/oracle-jdk8-installer;
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

ENV MAVEN_VERSION 3.6.3

RUN curl -fsSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

RUN mkdir -p /app/quartz-scheduler

COPY ./target/quartz-scheduler-0.0.1-SNAPSHOT.jar  /app/quartz-scheduler/quartz-scheduler-0.0.1-SNAPSHOT.jar

COPY ./entrypoint.sh  /app/quartz-scheduler/entrypoint.sh

WORKDIR /app/quartz-scheduler

RUN chmod 755 /app/quartz-scheduler/entrypoint.sh

EXPOSE 8080

CMD ["/app/quartz-scheduler/entrypoint.sh"]