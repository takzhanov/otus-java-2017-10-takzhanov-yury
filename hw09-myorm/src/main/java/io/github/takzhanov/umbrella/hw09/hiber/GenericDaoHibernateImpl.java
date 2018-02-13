package io.github.takzhanov.umbrella.hw09.hiber;

import io.github.takzhanov.umbrella.hw09.common.GenericDao;
import io.github.takzhanov.umbrella.hw09.domain.DataSet;
import org.hibernate.Session;

public class GenericDaoHibernateImpl implements GenericDao {
    private Session getSession() {
        return null;
    }

    @Override
    public <T extends DataSet> void save(T dataSet) {
        getSession().update(dataSet);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return getSession().get(clazz, id);
    }
}

