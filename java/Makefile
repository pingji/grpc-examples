GREETER_SERVER_BIN = java_greeter_server
GREETER_CLIENT_BIN = java_greeter_client
GREETER_SERVER_IMAGE = $(GREETER_SERVER_BIN):1.0
GREETER_CLIENT_IMAGE = $(GREETER_CLIENT_BIN):1.0
GREETER_SERVER_DOCKERFILE = $(PWD)/docker/server/Dockerfile
GREETER_CLIENT_DOCKERFILE = $(PWD)/docker/client/Dockerfile

# MVN = "mvn"
MVN = mvn -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true

run-server:
	cd helloworld && \
	$(MVN) -DskipTests package exec:java -Dexec.mainClass=org.example.grpc.server.HelloWorldServer

run-client:
	cd helloworld && \
	$(MVN) -DskipTests package exec:java -Dexec.mainClass=org.example.grpc.client.HelloWorldClient

clean-build:
	cd helloworld && \
	$(MVN) clean

build-server-image:
	docker rmi $(GREETER_SERVER_BIN):1.0
	docker build -f $(GREETER_SERVER_DOCKERFILE) -t $(GREETER_SERVER_IMAGE) .

build-client-image:
	docker rmi $(GREETER_CLIENT_BIN):1.0
	docker build -f $(GREETER_CLIENT_DOCKERFILE) -t $(GREETER_CLIENT_IMAGE) .

run-docker-server:
	docker run -it --rm --net=host $(GREETER_SERVER_IMAGE)

run-docker-client:
	docker run -it --rm --net=host $(GREETER_CLIENT_IMAGE)

push-image:
	docker tag $(GREETER_SERVER_IMAGE) harbor.internal.moqi.ai/pingji/$(GREETER_SERVER_IMAGE)
	docker push harbor.internal.moqi.ai/pingji/$(GREETER_SERVER_IMAGE)
	docker tag $(GREETER_CLIENT_IMAGE) harbor.internal.moqi.ai/pingji/$(GREETER_CLIENT_IMAGE)
	docker push harbor.internal.moqi.ai/pingji/$(GREETER_CLIENT_IMAGE)

deploy-k8s:
	kubectl apply -f deploy/service.yml
	kubectl apply -f deploy/deployment-server.yml
	kubectl apply -f deploy/envoy-sidecar-configmap.yml
	kubectl apply -f deploy/deployment-client.yml

clean-k8s:
	kubectl delete -f deploy/service.yml
	kubectl delete -f deploy/deployment-server.yml
	kubectl delete -f deploy/envoy-sidecar-configmap.yml
	kubectl delete -f deploy/deployment-client.yml

clean: clean-build

client-log:
	kubectl get pods | grep greeter-client | awk  '{print $$1}' | xargs -I{} kubectl logs -f {} 