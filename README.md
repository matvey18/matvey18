- 👋 Hi, I’m @matvey18
- 👀 I am interested in learning microcontroller programming.
- 🌱 I work as a forklift driver and my hobby is electronics and programming.
- 💞️ Я хочу сотрудничать с компаниеей по разработки програмного обеспечения для роботов.
- 📫 How to get to me? Write to the mail.

<!---
matvey18/matvey18 is a ✨ special ✨ repository because its `README.md` (this file) appears on your GitHub profile.
You can click the Preview link to take a look at your changes.
--->

## Android Audiobook App

Проект сгенерирован в каталоге `AudiobookApp`. Это Android-приложение (Kotlin, Jetpack Compose, Media3/ExoPlayer) для воспроизведения аудиокниг с фоновым сервисом.

Как запустить:
- Откройте папку `/workspace/AudiobookApp` в Android Studio (Arctic Fox или новее).
- При первом открытии Android Studio подтянет Gradle и зависимости (в проекте используется Version Catalog).
- Запустите конфигурацию приложения `app` на эмуляторе или устройстве.

Особенности:
- Фоновое воспроизведение через `MediaSessionService` (`AudioService`).
- UI на Jetpack Compose с базовыми кнопками "Пуск"/"Пауза" и списком демо-треков.
- Media3/ExoPlayer для стабильного аудио и уведомлений.

Дальшие шаги:
- Подключить вашу библиотеку аудиокниг и метаданные (обложка, автор, глава).
- Добавить сохранение прогресса прослушивания (персистентное позиционирование по главам).
- Реализовать управление с гарнитуры/блокировки (media buttons).
- Локальные файлы: добавить SAF/permissions и сканирование медиатеки.
