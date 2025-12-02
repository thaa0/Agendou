###########################
# 1) STAGE : BUILD
###########################
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# Baixa dependências sem reconstruir sempre
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copia o código e gera o JAR
COPY src ./src
RUN mvn -B clean package -DskipTests

###########################
# 2) STAGE : RUNTIME
###########################
FROM eclipse-temurin:17-jre-jammy AS runtime
ENV APP_HOME=/app
WORKDIR $APP_HOME

# Copia o JAR gerado
COPY --from=build /workspace/target/*.jar app.jar

# Cria usuário não-root
RUN useradd --system --uid 10001 appuser
USER appuser

# Healthcheck via Actuator
HEALTHCHECK --start-period=60s --interval=30s --timeout=5s --retries=5 \
  CMD curl -fs ${HEALTH_URL:-http://localhost:8080/actuator/health} || exit 1

EXPOSE 8080

# Entry point que respeita perfil e opções Java
ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS} -jar ${APP_HOME}/app.jar --spring.profiles.active=${APP_PROFILE:-default}"]
