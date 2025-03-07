envoy-configmap:
	kubectl apply -f envoy-proxy/envoy-configmap.yml

create-envoy-deployment:
	kubectl apply -f envoy-proxy/envoy-deployment.yml

create-envoy-service:
	kubectl apply -f envoy-proxy/envoy-service.yml

create-client-envoy-deployment:
	kubectl apply -f envoy-proxy/deployment-client.yml

client-envoy-log:
	kubectl get pods -n grpc-lb-example | grep greeter-client-envoy | awk  '{print $$1}' | xargs -I{} kubectl logs -f  {} -n  grpc-lb-example

clean-envoy:
	kubectl delete -n grpc-lb-example configmap envoy-config 
	kubectl delete -n grpc-lb-example deployment envoy
	kubectl delete -n grpc-lb-example service envoy

