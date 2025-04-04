FROM centos:centos7

COPY jdk-8u202-linux-x64.tar.gz /opt
COPY liferay-portal-tomcat-7.4.3.125-ga125-1726242956.tar.gz /opt
COPY db-check-1.0.0.jar /opt

RUN tar -xvf /opt/jdk-8u202-linux-x64.tar.gz -C /opt
RUN tar -xvf /opt/liferay-portal-tomcat-7.4.3.125-ga125-1726242956.tar.gz -C /opt

RUN rm -f /opt/jdk-8u202-linux-x64.tar.gz
RUN rm -f /opt/liferay-portal-tomcat-7.4.3.125-ga125-1726242956.tar.gz

ENV LFR_DIR=/opt/liferay-portal
ENV LFR_LIB=/opt/liferay-portal/tomcat/lib
ENV LFR_BIN=/opt/liferay-portal/tomcat/bin

COPY setenv.sh $LFR_BIN
RUN chmod +x $LFR_BIN/setenv.sh

RUN ln -s $LFR_DIR /opt/liferay

COPY entrypoint.sh /opt/entrypoint.sh
RUN chmod +x /opt/entrypoint.sh

ENV JAVA_HOME=/opt/jdk1.8.0_202

EXPOSE 8787
EXPOSE 8080

ENTRYPOINT ["/opt/entrypoint.sh"]
