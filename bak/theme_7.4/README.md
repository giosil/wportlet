# Run Liferay 7.4 on docker

docker pull liferay/portal:7.4.3.112-ga112

docker run --name ised-liferay -p 8080:8080 -d liferay/portal:7.4.3.112-ga112

# Run Liferay theme generator on docker

docker pull node

docker volume create --name prova-theme -o type=none -o o=bind -o device=C:\prj\push\theme_7.4\prova-theme

docker run --name yeoman-node --mount source=prova-theme,target=/home/yeoman/prova-theme -it node /bin/bash
	
	npm install -g gulp
	
	npm install -g yo@4.3.1
	
	npm install -g generator-liferay-theme
	
	adduser yeoman
	
	chown -R yeoman:yeoman /home/yeoman
	
	su - yeoman
	
	yo liferay-theme
	
	cd prova-theme
	
	gulp deploy

# Download war theme

docker cp yeoman-node:/home/yeoman/prova-theme/dist/prova-theme.war prova-theme.war

# Deploy theme

docker exec -w /home/yeoman/prova-theme -u yeoman yeoman-node gulp deploy

docker cp ./prova-theme/dist/prova-theme.war ised-liferay:/opt/liferay

docker exec -u root ised-liferay chown liferay:liferay /opt/liferay/prova-theme.war

docker exec ised-liferay mv /opt/liferay/prova-theme.war /opt/liferay/deploy/

