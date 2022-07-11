# java grpc demo
## run server/client base on code
```
make run-server
make run-client
```

## clean build
```
make clean
```

## build docker image
```
make build-server-image
make build-client-image
```

## run server/client docker containor
```
make run-docker-server
make run-docker-client
```

## push docker image to moqi harbor
```
make push-image
```

## deploy demo in moqi k8s lab
```
make deploy-k8s
```

## delete demo from moqi k8s lab
```
make clean-k8s
```

## print the log for client in k8s lab
```
make client-log
```