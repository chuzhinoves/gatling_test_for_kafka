FROM openjdk:8
COPY sbt /sbt/
RUN /bin/bash -c "sbt/bin/sbt"
COPY gatling-kafka /gatling-kafka/
RUN /bin/bash -c "cd /gatling-kafka; \
/sbt/bin/sbt assembly"
ENV KAFKAPLUG=/gatling-kafka
ENV GATLING=/gatling-charts-highcharts-bundle-3.5.1/
ENV JARNAME=target/scala-2.13/gatling-kafka-assembly-*.jar
ENV LIBPATH=lib
COPY gatling-charts-highcharts-bundle-3.5.1 /gatling-charts-highcharts-bundle-3.5.1/
RUN /bin/bash -c "cp ${KAFKAPLUG}/${JARNAME} ${GATLING}/${LIBPATH}"
WORKDIR ${GATLING}/bin

#RUN sbt
#CMD /bin/bash -c "./gatling.sh"