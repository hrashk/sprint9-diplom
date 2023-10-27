## Темы
* только 4 класса должны быть покрыты юнит тестами на > 70%
* как покрыть тестами enum
* доки API могут отставать от реализации
* часть ручек может быть закрыта авторизацией, а часть - открыта, и это нормально. 
  Например, список товаров - возможно публичная информация. 
  На реальных проектах стоит уточнить у разработчика и аналитика.
* скроллинг страницы бургеров
* получение токена авторизации из Local Storage браузера после логина
* кросс-браузерное тестирование:
  * tests + CI/CD or local
  * mvn clean test -Dbrowser=yandex
  * в коде использовать фабрику или @Rule / JUnit 5 : @ExtendsWith
  * WebDriver инициализация
    * скачать нужные версии драйвера в отдельные папки: https://chromedriver.storage.googleapis.com/index.html 
    * инициализировать через сервис: https://chromedriver.chromium.org/getting-started#h.p_ID_181
    * попробовать WebdriverManager (не поддерживает некоторые браузеры)


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
