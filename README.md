# spotify-stat-service
HTTP API for songs statistics

Для построения Docker образа использовать команду `docker build -t spotify-stats:latest .` находясь в корне проекта

Для запуска приложения в Docker контейнере использовать команду `docker run --rm -p 8080:8080 spotify-stats:latest`

Также можно просто запустить приложение с помощью `./gradlew bootRun` из корня проекта

P.S. я не вполне уверен как должен выглядеть результат запроса `?colname=year&year=n`, поэтому отдельно этот случай не обрабатывал.
