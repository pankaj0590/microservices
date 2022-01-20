# microservices
 Prerequisite  -  Tech Stack - Java 8 version, Maven 3 and above , RabbitMQ,Zipkin, Docker.
Spirng Boot and Cloud for Microservices
Microservice repository has multiple services.
User MS has multiple functionality like  api validation,user registration , login , Web secutity , Authentication and Authorization, Feign Client for microservice communication , 
feign exception handling, Hystrix for fallback response if respactive service is down/UnAvailable, Zipkin and Sleuth for distibuted logging.
i have implimented authorization only for secured apis. when any request routed via gateway which content Authorization header
then gateway pass authorization header to corresponded microservice. Respacetive microservice has Authorization filter which extract id from jwt token.
API Gateway( Spring Cloud gateway) has JWT token validation and microservices routing details.

