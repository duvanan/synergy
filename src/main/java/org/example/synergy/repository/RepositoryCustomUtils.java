/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class RepositoryCustomUtils {

    /**
     * Executes a SELECT query and returns a list of results.
     *
     * @param sql                the SQL query string
     * @param transactionManager the transaction manager
     * @return list of queried results
     */
    public <T> List<T> getResultList(String sql, JpaTransactionManager transactionManager) {
        return getResultList(sql, null, null, transactionManager);
    }

    /**
     * Executes a SELECT query with parameters and returns a list of results.
     *
     * @param sql                the SQL query string
     * @param parameters         the query parameters
     * @param transactionManager the transaction manager
     * @return list of queried results
     */
    public <T> List<T> getResultList(String sql, Map<String, Object> parameters,
            JpaTransactionManager transactionManager) {
        return getResultList(sql, parameters, null, transactionManager);
    }

    /**
     * Executes a SELECT query with a result set mapping and returns a list of results.
     *
     * @param sql                  the SQL query string
     * @param resultSetMappingName the result set mapping name
     * @param transactionManager   the transaction manager
     * @return list of queried results
     */
    public <T> List<T> getResultList(String sql, String resultSetMappingName,
            JpaTransactionManager transactionManager) {
        return getResultList(sql, null, resultSetMappingName, transactionManager);
    }

    /**
     * Executes a SELECT query with optional parameters and a result set mapping.
     *
     * @param sql                  the SQL query string
     * @param parameters           the query parameters
     * @param resultSetMappingName the result set mapping name
     * @param transactionManager   the transaction manager
     * @return list of queried results
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getResultList(String sql, Map<String, Object> parameters, String resultSetMappingName,
            JpaTransactionManager transactionManager) {
        log.debug("Executing SQL: {}", sql);
        log.debug("With parameters: {}", parameters);

        try (EntityManager entityManager =
                Objects.requireNonNull(transactionManager.getEntityManagerFactory()).createEntityManager()) {
            Query query = createQuery(sql, parameters, resultSetMappingName, entityManager);
            return query.getResultList();
        }
    }

    /**
     * Executes a SELECT query and returns a single result.
     *
     * @param sql                the SQL query string
     * @param transactionManager the transaction manager
     * @return the single result
     */
    public <T> T getSingleResult(String sql, JpaTransactionManager transactionManager) {
        return getSingleResult(sql, null, null, transactionManager);
    }

    /**
     * Executes a SELECT query with parameters and returns a single result.
     *
     * @param sql                the SQL query string
     * @param parameters         the query parameters
     * @param transactionManager the transaction manager
     * @return the single result
     */
    public <T> T getSingleResult(String sql, Map<String, Object> parameters, JpaTransactionManager transactionManager) {
        return getSingleResult(sql, parameters, null, transactionManager);
    }

    /**
     * Executes a SELECT query with a result set mapping and returns a single result.
     *
     * @param sql                  the SQL query string
     * @param resultSetMappingName the result set mapping name
     * @param transactionManager   the transaction manager
     * @return the single result
     */
    public <T> T getSingleResult(String sql, String resultSetMappingName, JpaTransactionManager transactionManager) {
        return getSingleResult(sql, null, resultSetMappingName, transactionManager);
    }

    /**
     * Executes a SELECT query with optional parameters and a result set mapping.
     *
     * @param sql                  the SQL query string
     * @param parameters           the query parameters
     * @param resultSetMappingName the result set mapping name
     * @param transactionManager   the transaction manager
     * @return the single result
     */
    @SuppressWarnings("unchecked")
    public <T> T getSingleResult(String sql, Map<String, Object> parameters, String resultSetMappingName,
            JpaTransactionManager transactionManager) {
        log.debug("Executing SQL: {}", sql);
        log.debug("With parameters: {}", parameters);

        try (EntityManager entityManager =
                Objects.requireNonNull(transactionManager.getEntityManagerFactory()).createEntityManager()) {
            Query query = createQuery(sql, parameters, resultSetMappingName, entityManager);
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Creates a Query instance based on SQL string, mapping name, and parameters.
     *
     * @param sql                  the SQL query string
     * @param parameters           the query parameters
     * @param resultSetMappingName the result set mapping name
     * @param entityManager        the entity manager
     * @return the new Query instance
     */
    private Query createQuery(String sql, Map<String, Object> parameters, String resultSetMappingName,
            EntityManager entityManager) {
        Query query = Optional.ofNullable(resultSetMappingName)
            .filter(StringUtils::isNotEmpty)
            .map(name -> entityManager.createNativeQuery(sql, name))
            .orElseGet(() -> entityManager.createNativeQuery(sql));

        query.unwrap(org.hibernate.query.Query.class).setReadOnly(true);

        Optional.ofNullable(parameters).ifPresent(params -> params.forEach(query::setParameter));
        return query;
    }

    @SuppressWarnings("rawtypes")
    private List<String> getReturnAliasColumns(NativeQuery query) {
        String sqlQuery = query.getQueryString();
        sqlQuery = sqlQuery.replace("\n", " ");
        sqlQuery = sqlQuery.replace("\t", " ");
        int numOfRightPythis = 0;
        int startPythis = -1;
        int endPythis = 0;
        boolean hasRightPythis = true;
        while (hasRightPythis) {
            char[] arrStr = sqlQuery.toCharArray();
            hasRightPythis = false;
            int idx = 0;
            for (char c : arrStr) {
                if (idx > startPythis) {
                    if ("(".equalsIgnoreCase(String.valueOf(c))) {
                        if (numOfRightPythis == 0) {
                            startPythis = idx;
                        }
                        numOfRightPythis++;
                    } else if (")".equalsIgnoreCase(String.valueOf(c)) && numOfRightPythis > 0) {
                        numOfRightPythis--;
                        if (numOfRightPythis == 0) {
                            endPythis = idx;
                            break;
                        }
                    }
                }
                idx++;
            }
            if (endPythis > 0) {
                sqlQuery = sqlQuery.substring(0, startPythis) + " # " + sqlQuery.substring(endPythis + 1);
                hasRightPythis = true;
                endPythis = 0;
            }
        }

        return aliasColumns(sqlQuery);
    }

    private List<String> aliasColumns(String sqlQuery) {
        List<String> aliasColumns = new ArrayList<>();
        String[] arrStr = sqlQuery.substring(0, sqlQuery.toUpperCase().indexOf(" FROM ")).split(",");
        for (String str : arrStr) {
            String[] temp = str.trim().split(" ");
            String alias = temp[temp.length - 1].trim();
            if (alias.contains(".")) {
                alias = alias.substring(alias.lastIndexOf('.') + 1).trim();
            }
            if (alias.contains(",")) {
                alias = alias.substring(alias.lastIndexOf(',') + 1).trim();
            }
            if (alias.contains("`")) {
                alias = alias.replace("`", "");
            }
            if (!aliasColumns.contains(alias)) {
                aliasColumns.add(alias);
            }
        }
        return aliasColumns;
    }

    /**
     * Executes an UPDATE/INSERT/DELETE query with parameters
     *
     * @param sql                SQL query string
     * @param parameters         Query parameters
     * @param transactionManager Transaction manager
     */
    public void executeUpdate(String sql, Map<String, Object> parameters, JpaTransactionManager transactionManager) {
        log.debug("Executing batch update SQL: {}", sql);

        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = Objects.requireNonNull(transactionManager.getEntityManagerFactory())
                .createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createNativeQuery(sql);
            if (parameters != null) {
                parameters.forEach(query::setParameter);
            }

            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    log.error("Error during transaction rollback", rollbackEx);
                }
            }
            throw e;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                try {
                    entityManager.close();
                } catch (Exception closeEx) {
                    log.error("Error closing entity manager", closeEx);
                }
            }
        }
    }
}
