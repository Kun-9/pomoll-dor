# 이미지 베이스 설정
FROM openjdk:11

# 서버 시간과 일치시키기 위서 tzdata 설치
RUN apt-get update && apt-get install -y tzdata

# 타임존 서울 설정
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Jar 파일 설정
ARG JAR_FILE=build/libs/pomondor-0.0.1-SNAPSHOT.jar

# Jar 파일 컨테이너에 복사
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]