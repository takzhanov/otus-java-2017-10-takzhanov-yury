# Домашние задания для [курса "Разработчик Java"](https://otus.ru/lessons/razrabotchik-java/) в [Otus.ru](https://otus.ru/)

Группа 2017-10

### Студент

Yury Takzhanov (Юрий Такжанов)

takzhanov@gmail.com

takzhanov@restream.rt.ru

---

[Примеры для курса](https://github.com/vitaly-chibrikov/otus_java_2017_10)

---

[Ссылка на этот репозиторий](https://github.com/takzhanov/otus-java-2017-10-takzhanov-yury)

ДЗ-09: myORM

Создайте в базе таблицу с полями:
id bigint(20) NOT NULL auto_increment
name varchar(255)
age int(3)

Создайте абстрактный класс DataSet. Поместите long id в DataSet.
Добавьте класс UserDataSet (с полями, которые соответствуют таблице) унаследуйте его от DataSet.

Напишите Executor, который сохраняет наследников DataSet в базу и читает их из базы по id и классу.

<T extends DataSet> void save(T user){…}
<T extends DataSet> T load(long id, Class<T> clazz){…}

---

ДЗ-10: Hibernate ORM

На основе предыдущего ДЗ (myORM):
1. Оформить решение в виде DBService (interface DBService, class DBServiceImpl, UsersDAO, UsersDataSet, Executor)
2. Не меняя интерфейс DBSerivice сделать DBServiceHibernateImpl на Hibernate.
3. Добавить в UsersDataSet поля:
адресс (OneToOne)
class AddressDataSet{
private String street;
}
и телефон* (OneToMany)
class PhoneDataSet{
private String number;
}
Добавить соответствущие датасеты и DAO.

4.** Поддержать работу из пункта (3) в myORM

---

ДЗ-11: my cache engine

Напишите свой cache engine с soft references.
Добавьте кэширование в DBService из заданий myORM или Hibernate ORM

---

ДЗ-12: Веб сервер

Встроить веб сервер в приложение из ДЗ-11.
Сделать админскую страницу, на которой админ должен авторизоваться и получить доступ к параметрам и состоянию кэша.

---

ДЗ-13: WAR

Собрать war для приложения из ДЗ-12.
Создавать кэш и DBService как Spring beans, передавать (inject) их в сервлеты.
Запустить веб приложение во внешнем веб сервере.
