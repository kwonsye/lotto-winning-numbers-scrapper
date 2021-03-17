# Lotto-winning-numbers-scrapper

- 최근부터 회차 범위에 대한 로또 당첨 번호 내역을 긁어오는 스크래퍼/크롤러
- [크롤링한 사이트](https://superkts.com/lotto/list/?)
- no DB, ORM, just api controller
- [api-document-swagger-ui](http://52.79.103.18/swagger-ui/)
- [DockerHub 에 올린 docker image](https://hub.docker.com/layers/kwonsye56/scrapping-app/latest/images/sha256:20731a42859784674d6313adf5ab37f09b538e7eb22e2f7bf4bf1f50596a2415)

## Develop Env
- `Java15`
- `IntelliJ Ultimate`

## Dependency
- server framework
  - `spring-boot-starter-web 2.4.3`
  
- etc.
  - `jsoup 1.13.1` for html crawling
  - `project lombok`
  - `org.junit.jupiter:junit-jupiter-api:5.6.0` for unit test
  - `swagger-ui:3.0.0` for api document
  
## Deploy Env
- [AWS LightSail](https://aws.amazon.com/ko/lightsail/?nc2=type_a) Amazon Linux2 instance (1GB memory, 1 core cpu, 40GB SSD)
- `DockerHub`, `Docker`

### ☕ CoffeeTime with [Disney Medley🌈](https://www.youtube.com/watch?v=lVqNI6qP1ms&list=PLwbkir_8t_gznpmEa7Dh6zm1sAs_4r_yq&index=3)
