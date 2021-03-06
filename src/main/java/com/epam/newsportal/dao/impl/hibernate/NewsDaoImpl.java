package com.epam.newsportal.dao.impl.hibernate;

import com.epam.newsportal.dao.NewsDao;
import com.epam.newsportal.dto.News;
import com.epam.newsportal.dto.News_;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Repository("newsDao")
public class NewsDaoImpl extends BaseDao<News> implements NewsDao {

    @Override
    public News findNewsByUri(Date date, @NotNull String uri) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<News> criteriaQuery = builder.createQuery(News.class);
        Root<News> root = criteriaQuery.from(News.class);
        criteriaQuery.select(root).where(
                builder.equal(root.get(News_.uri), uri),
                builder.equal(root.get(News_.changeDate), date)
        );

        return session.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<News> findNewsListByPage(int offset, int limit) {
        Session session = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<News> criteriaQuery = builder.createQuery(News.class);
        criteriaQuery.from(News.class);

        TypedQuery query = session.createQuery(criteriaQuery);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }
}