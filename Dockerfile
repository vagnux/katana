FROM ubuntu:22.10  AS build
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Additional tools
RUN apt-get update \
	&& apt-get install -y --no-install-recommends \
	curl \
	libssl-dev \
	openssl \
	nano \
	gnupg \
	cron \
	bash \
	locales \
	build-essential \
	libz-dev \
	zlib1g-dev \
	maven
#	
RUN apt-get update -y	
RUN apt reinstall ca-certificates
RUN update-ca-certificates
#RUN apt-get install -y --no-install-recommends maven openjdk-17-jdk

ENV  PATH=/usr/lib/jvm/bin:$PATH
ENV  JAVA_HOME=/usr/lib/jvm

RUN sed -i '/pt_BR.UTF-8/s/^# //g' /etc/locale.gen && \
    locale-gen
ENV LANG pt_BR.UTF-8  
ENV LANGUAGE pt_BR:  
ENV LC_ALL pt_BR.UTF-8  

RUN mkdir /spring

COPY . /spring
RUN chmod +x /spring/java.sh
RUN /spring/java.sh
RUN ls /spring
RUN cd /spring \ 
	&& mvn clean \
	&& mvn install -DskipTests
	

FROM ubuntu:22.10
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Additional tools
RUN apt-get update \
	&& apt-get install -y --no-install-recommends \
	curl \
	libssl-dev \
	openssl \
	nano \
	gnupg \
	cron \
	bash \
	locales \
	build-essential \
	libz-dev \
	zlib1g-dev \
	maven
	
RUN apt-get update -y	

RUN mkdir -p /spring
COPY java.sh /spring 	
COPY jdk /spring
RUN chmod +x /spring/java.sh
RUN /spring/java.sh
ENV  PATH=/usr/lib/jvm/bin:$PATH
ENV  JAVA_HOME=/usr/lib/jvm


RUN mkdir /app
COPY --from=build /spring/target/Katana-1.0.jar /app/Katana-1.0.jar
WORKDIR /app

ENTRYPOINT ["java","-jar","/app/Katana-1.0.jar"]
