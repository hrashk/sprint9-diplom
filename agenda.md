## Часть 1. Юнит тесты
* только 4 класса должны быть покрыты юнит тестами на > 70%
* как покрыть тестами enum

## Часть 2. API тесты
* доки API могут отставать от реализации
* часть ручек может быть закрыта авторизацией, а часть - открыта, и это нормально. 
  Например, список товаров - возможно публичная информация. 
  На реальных проектах стоит уточнить у разработчика и аналитика.

## Часть 3. Селениум и кросс-браузерное тестирование
* скроллинг страницы бургеров
* получение токена авторизации из Local Storage браузера после логина
* удаление юзера через rest assured: передать токен в header'е
* юзера следует удалять даже в негативных кейсах, т.к. система может создавать невалидных юзеров из-за бага
* mvn clean test -Dbrowser=yandex
* в коде использовать фабрику или @Rule / JUnit 5 : @ExtendsWith
* скачать нужные версии драйвера в отдельные папки:
  * свежие версии: https://googlechromelabs.github.io/chrome-for-testing/
  * для яндекс браузера искать подходящую версию через REST API:
    https://github.com/GoogleChromeLabs/chrome-for-testing#json-api-endpoints
* инициализировать через сервис
  * рабочий код: https://disk.yandex.ru/d/2a_r4NGoQgClYQ
  * документация: https://chromedriver.chromium.org/getting-started#h.p_ID_181
  * все пути до бинарников драйвера и браузера нужно вытащить в системные переменные, а лучше в конфиг файлы
* попробовать WebdriverManager (не поддерживает некоторые платформы и браузеры)


## Портфолио++
* Инфраструктура/DevOps: прикрутить CI/CD
  * GitLab
  * GitHub + CircleCI/TravisCI/...
  * GitHub Actions
  * Jenkins (найти хостинг) 
* Docker
  * JUnit: eclipse-temurin
  * RestAssured
  * [Selenium Grid](https://github.com/SeleniumHQ/docker-selenium)
  * Selenoid
* (желательно) Selenide
* (желательно) JUnit 5

https://gist.github.com/hrashk/15fae8919686f414972adfde50b5b670
