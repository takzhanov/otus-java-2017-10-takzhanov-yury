package io.github.takzhanov.umbrella.hw09.hiber;

import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw09.domain.AddressDataSet;
import io.github.takzhanov.umbrella.hw09.domain.PhoneDataSet;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.function.Function;

public class DbServiceHibernateImpl implements DbService {
    private final SessionFactory sessionFactory;

    public DbServiceHibernateImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./test");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "sa");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    public DbServiceHibernateImpl(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    public void saveUser(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDaoHibernateImpl dao = new UserDataSetDaoHibernateImpl(session);
            dao.insert(dataSet);
        }
    }

    @Override
    public UserDataSet loadUser(long id) {
        return runInSession(session -> {
            UserDataSetDaoHibernateImpl dao = new UserDataSetDaoHibernateImpl(session);
            return dao.findById(id, UserDataSet.class);
        });
    }

    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            UserDataSetDaoHibernateImpl dao = new UserDataSetDaoHibernateImpl(session);
            return dao.readByName(name);
        });
    }

    @Override
    public List<UserDataSet> loadAllUsers() {
        return runInSession(session -> {
            UserDataSetDaoHibernateImpl dao = new UserDataSetDaoHibernateImpl(session);
            return dao.findAll(UserDataSet.class);
        });
    }

    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    @Override
    public String getMetaData() {
        return null;
    }

    @Override
    public int prepareTables() {
        return 0;
    }

    @Override
    public int dropTables() {
        return 0;
    }

    @Override
    public void close() {
        shutdown();
    }
}

