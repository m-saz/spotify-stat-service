FROM eclipse-temurin:11-jdk-alpine as build
WORKDIR /workspace/app
COPY . .
RUN ./gradlew build --no-daemon
RUN mkdir -p build/libs/dependencies && (cd build/libs/dependencies; jar -xf ../*.jar)

FROM eclipse-temurin:11-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/libs/dependencies
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT exec java ${JAVA_OPTS} -cp app:app/lib/* no.group.spotifystatservice.SpotifyStatServiceApplication