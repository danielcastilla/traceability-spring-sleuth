### Traceability with Spring Sleuth

Prueba de concepto para mostrar el comportamiento en un modelo de microservicios con los siguientes aspectos:

- Trazabilidad con Spring Sleuth, Zipkin y Jaeger
- Monitorización con Prometheus y Grafana

El modelo de microservicios utilizado en la prueba es muy simple. El ms1 recibirá un número decimal. Se lo enviará al ms2. Este a su vez al ms3 y este finalizará enviándole el decimal al ms4. Cada uno de ellos devolvera el decimal transformándolo a binario(ms2), octal(ms3) y hexadecimal(ms4).

El proposito de la prueba no es tener una funcionalidad compleja sino generar mensajes suficientes entre los microservicios para estudiar el comportamiento de estos en las herramientas de monitorización y trazabilidad.
 

![traceability](img/traceability-microservices-arch.png)

- Spring Boot 2.0.1
- Spring Cloud Finchley.SR2
- Zipkin 
- Jaeger 1.7

#### DOCKER

Install Jaeger image

```docker run -d -e COLLECTOR_ZIPKIN_HTTP_PORT=9411   -p 5775:5775/udp   -p 6831:6831/udp   -p 6832:6832/udp   -p 5778:5778   -p 16686:16686   -p 14268:14268   -p 9411:9411   jaegertracing/all-in-one:1.7```

URL: [http://localhost:16686](http://localhost:16686)

![jaeger](img/jaeger-screenshot.png)

Install Zipkin image

```docker run -d  -p 9411:9411  openzipkin/zipkin ```

URL: [http://localhost:9411/zipkin](http://localhost:9411/zipkin)

![zipkin](img/zipkin-screenshot.png)


Install Prometheus image

``` docker run --name prometheus -d -p 127.0.0.1:9090:9090 prom/prometheus ```


### Spring Cloud Sleuth

https://cloud.spring.io/spring-cloud-sleuth/1.2.x/multi/multi_spring-cloud-sleuth.html


#### Swagger Console

http://localhost:[port]/swagger-ui.html


