# Stage 1: Build Stage
FROM --platform=linux/amd64 eclipse-temurin:21-jdk-alpine AS build

# Gradle 빌드 환경 변수 설정
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.jvmargs=-Xmx1024m -Dorg.gradle.parallel=false"

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 설정 및 소스 코드 복사
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Gradle 종속성 다운로드
RUN ./gradlew dependencies --no-daemon

# 프로젝트 소스 복사 및 빌드
COPY src ./src
RUN ./gradlew bootJar --no-daemon

# Stage 2: Run Stage
# 실행 환경에서는 경량 Alpine 이미지 사용
FROM --platform=linux/amd64 eclipse-temurin:21-jre-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]
