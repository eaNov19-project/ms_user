clean:
	mvn clean

install: clean
	mvn install

build: install
	docker build -t islamahmad/eaproj-userms:1.0.13 .

login:
	docker login

push: build login
	docker push islamahmad/eaproj-userms:1.0.13



