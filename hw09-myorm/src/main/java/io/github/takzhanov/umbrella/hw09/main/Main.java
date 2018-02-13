package io.github.takzhanov.umbrella.hw09.main;

/**
 * ДЗ-09: myORM
 * <p>
 * Создайте в базе таблицу с полями:
 * id bigint(20) NOT NULL auto_increment
 * name varchar(255)
 * age int(3)
 * <p>
 * Создайте абстрактный класс DataSet. Поместите long id в DataSet.
 * Добавьте класс UserDataSet (с полями, которые соответствуют таблице) унаследуйте его от DataSet.
 * <p>
 * Напишите Executor, который сохраняет наследников DataSet в базу и читает их из базы по id и классу.
 * <p>
 * <T extends DataSet> void save(T user){…}
 * <T extends DataSet> T load(long id, Class<T> clazz){…}
 */
public class Main {
}
