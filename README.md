### Traceability with Spring Sleuth

![traceability](img/traceability-microservices-arch.png)



#### DOCKER

##### Jaeger

```docker run -d --name jaeger   -e COLLECTOR_ZIPKIN_HTTP_PORT=9411   -p 5775:5775/udp   -p 6831:6831/udp   -p 6832:6832/udp   -p 5778:5778   -p 16686:16686   -p 14268:14268   -p 9411:9411   jaegertracing/all-in-one:1.7```

URL: [http://localhost:16686](http://localhost:16686)

![jaeger](img/jaeger-screenshot.png)

##### Zipkin

```docker run -d  -p 9411:9411  openzipkin/zipkin ```

URL: [http://localhost:9411/zipkin](http://localhost:9411/zipkin)

![zipkin](img/zipkin-screenshot.png)

### Spring Cloud Sleuth

https://cloud.spring.io/spring-cloud-sleuth/1.2.x/multi/multi_spring-cloud-sleuth.html
