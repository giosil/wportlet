#!/bin/bash
set -e

echo "Wait 10s..."
sleep 10

echo "Start Liferay..."
exec /opt/liferay/tomcat/bin/catalina.sh run
