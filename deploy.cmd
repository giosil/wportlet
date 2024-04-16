@echo off

set LIFERAY_CONT_NAME=csr-liferay

docker cp ./target/wportlet.war %LIFERAY_CONT_NAME%:/opt/liferay

docker exec -u root %LIFERAY_CONT_NAME% chown liferay:liferay /opt/liferay/wportlet.war

docker exec %LIFERAY_CONT_NAME% mv /opt/liferay/wportlet.war /opt/liferay/deploy/
