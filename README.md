# Простой архиватор

Ключи:
-Dmode=encode/decode : упаковать/распаковать
-Dalgo=simple/extended: простой/улучшенный алгоритмы
-Dfile=путь_к_файлу

Сборка:
    ./gradlew clean build
    
Запуск (пример для простого алгоритма):
- архивация: java -jar -Dmode=encode -Dfile=/opt/X11/etc/X11/dummy.conf -jar ./build/libs/archiver-1.0.jar
- распаковка: java -jar -Dmode=decode -Dfile=./dummy.conf.archive -jar ./build/libs/archiver-1.0.jar

