# Temel Java çalışma zamanı ortamını tanımlar
FROM openjdk:17-jdk-alpine

# Uygulama içinde kullanılacak ortam değişkenlerini dinamikleştirmek için bir argüman belirtiyoruz
ARG JAR_FILE=target/FixTrackBackend-0.0.1-SNAPSHOT.jar

# Çalışma dizinini ayarla
WORKDIR /app

# Maven tarafından oluşturulan jar dosyasını çalışma dizinine kopyala
COPY ${JAR_FILE} app.jar

# Uygulamanızı başlatmak için ENTRYPOINT komutunu kullanıyoruz
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Yayını dışa açılması için 8080 portunu açıyoruz
EXPOSE 8080