@echo off

set LIFERAY_CONT_NAME=csr-liferay

rem docker pull liferay/portal:7.1.3-ga4

rem docker run --name %LIFERAY_CONT_NAME% -p 8080:8080 -d liferay/portal:7.1.3-ga4

rem docker compose -p "csr-liferay-cluster" up --detach

docker cp ./target/wportlet.war %LIFERAY_CONT_NAME%:/opt/liferay

docker exec -u root %LIFERAY_CONT_NAME% chown liferay:liferay /opt/liferay/wportlet.war

docker exec %LIFERAY_CONT_NAME% mv /opt/liferay/wportlet.war /opt/liferay/deploy/
