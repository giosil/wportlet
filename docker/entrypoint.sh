#!/bin/bash
set -e

echo "Check DB..."
$JAVA_HOME/bin/java -cp /opt/db-check-1.0.0.jar:/opt/liferay-portal/tomcat/lib/postgresql-42.7.5.jar org.dew.check.DBCheck /opt/liferay-portal/portal-setup-wizard.properties

echo "Start Liferay..."
exec /opt/liferay/tomcat/bin/catalina.sh run
