init-dev:
	sudo make nginx
	sudo make up-dev
init-prod:
	sudo make nginx
	sudo make up-prod
nginx:
	sudo chmod a+x ./nginx-conf.sh
	sudo nginx-conf.sh
up-dev:
	sudo docker-compose -f docker-compose.dev.yml --build -d 
up-prod:
	sudo docker-compose -f docker-compose.prod.yml --build -d 
