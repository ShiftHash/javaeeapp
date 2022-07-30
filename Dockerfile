FROM airhacks/wildfly-ping
MAINTAINER EV
COPY target/HelloWorld-1.0-SNAPSHOT.war ${DEPLOYMENT_DIR}