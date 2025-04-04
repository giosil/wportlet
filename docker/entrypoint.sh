#!/bin/bash
set -e

echo "Wait 5s..."
sleep 5

echo "Start Liferay..."
exec /opt/liferay/tomcat/bin/catalina.sh run
