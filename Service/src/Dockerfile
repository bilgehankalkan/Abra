FROM node:6-alpine AS build-env

WORKDIR /app 

COPY . .

RUN npm install 

EXPOSE 80

CMD [ "npm","start" ]