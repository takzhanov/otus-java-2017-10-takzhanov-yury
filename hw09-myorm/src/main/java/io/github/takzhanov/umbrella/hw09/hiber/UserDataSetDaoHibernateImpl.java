package io.github.takzhanov.umbrella.hw09.hiber;

import io.github.takzhanov.umbrella.hw09.domain.DataSet;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw09.myorm.DataSetDao;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDataSetDaoHibernateImpl implements DataSetDao {
    private Session session;

    public UserDataSetDaoHibernateImpl(Session session) {
        this.session = session;
    }

    public UserDataSet readByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.where(builder.equal(from.get("name"), name));
        Query<UserDataSet> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    @Override
    public <T extends DataSet> void insert(T dataSet) {
        session.save(dataSet);
    }

    @Override
    public <T extends DataSet> T findById(long id, Class<T> clazz) {
        return session.load(clazz, id);
    }

    @Override
    public <T extends DataSet> List<T> findAll(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).list();
    }
}
