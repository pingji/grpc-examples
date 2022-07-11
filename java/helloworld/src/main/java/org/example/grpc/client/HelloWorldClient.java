package org.example.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.net.InetAddress;
import org.example.grpc.helloworld.GreeterGrpc;
import org.example.grpc.helloworld.HelloReply;
import org.example.grpc.helloworld.HelloRequest;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloWorldClient {
    private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

    // private static final String defaultName = "world";

    public static void main(String[] args) throws Exception {
        InetAddress id = InetAddress.getLocalHost();  
        String hostname = id.getHostName();  
        String podname = getEnv("POD_NAME", hostname);
        String domain = getEnv("SERVER_DOMAIN", "localhost");
        String port = getEnv("SERVER_PORT", "50051");
        String target = String.format("%s:%s", domain, port);
        logger.log(Level.INFO, "conn target: {0}", target);


        // Create a communication channel to the server, known as a Channel.
        // Channels are thread-safe and reusable.
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();

        try {
            GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(channel);
            

            while (true) {
                HelloRequest request = HelloRequest.newBuilder().setName(podname).build();
                HelloReply response;
                try {
                    response = blockingStub.sayHello(request);
                } catch (StatusRuntimeException e) {
                    logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
                    return;
                }
                logger.info("Greeting: " + response.getMessage());
                Thread.sleep(1000);
            }
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }

    }
    private static String getEnv(String name, String defaultVal) {
        String val = System.getenv(name);
        if(val == null) {
            val = defaultVal;
        }
        return val;
    }

}
