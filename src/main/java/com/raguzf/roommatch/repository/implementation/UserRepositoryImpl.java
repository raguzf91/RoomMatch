package com.raguzf.roommatch.repository.implementation;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.raguzf.roommatch.model.Gender;
import com.raguzf.roommatch.model.Role;
import com.raguzf.roommatch.model.User;
import com.raguzf.roommatch.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public User findById(Integer id) throws DataAccessException {
        try {
            TypedQuery<User> query = this.em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception ex) {
            logger.error("Error occurred while finding user by id: {}", id, ex);
            throw new RuntimeException("Error occurred while finding user by id", ex);
        }
    }

    @Override
    public Collection<User> findByRole(Role role) throws DataAccessException {
        try {
            TypedQuery<User> query = this.em.createQuery("SELECT u FROM User u JOIN u.role r WHERE r.name = :roleName", User.class);
            query.setParameter("roleName", role.name());
            return query.getResultList();
        } catch (Exception ex) {
            logger.error("Error occurred while finding users by role: {}", role, ex);
            throw new RuntimeException("Error occurred while finding users by role", ex);
        }
    }

    @Override
    public void save(User user) throws DataAccessException {
        try {
            if (user.getId() == null) {
                this.em.persist(user);
            } else {
                this.em.merge(user);
            }
        } catch (Exception ex) {
            logger.error("Error occurred while saving user: {}", user, ex);
            throw new RuntimeException("Error occurred while saving user", ex);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<User> findAll() throws DataAccessException {
        try {
            Query query = this.em.createQuery("SELECT user FROM User user", User.class);
            return query.getResultList();
        } catch (Exception ex) {
            logger.error("Error occurred while finding all users", ex);
            throw new RuntimeException("Error occurred while finding all users", ex);
        }
    }

   

    @Override
    public void delete(User user) throws DataAccessException {
        try {
            this.em.remove(this.em.contains(user) ? user : this.em.merge(user));
        } catch (Exception ex) {
            logger.error("Error occurred while deleting user: {}", user, ex);
            throw new RuntimeException("Error occurred while deleting user", ex);
        }
    }

    /**
     * TODO 
     */

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findByFirstName(String firstName) throws DataAccessException {
        try {
            Query query = this.em.createQuery("SELECT u FROM User u WHERE u.firstName = :firstName", User.class);
            query.setParameter("firstName", firstName + "%");
            return query.getResultList();
        } catch (Exception ex) {
            logger.error("Error occurred while finding the first name: {}", firstName, ex);
            throw new RuntimeException("Error occured while querying for first name", ex);
        }
    }

    /**
     * TODO 
     */

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findByLasttName(String lastName) throws DataAccessException {
        try {
            Query query = this.em.createQuery("SELECT u FROM User u WHERE u.lastName = :lastName", User.class);
            query.setParameter("firstName", lastName + "%");
            return query.getResultList();
        } catch (Exception ex) {
            logger.error("Error occurred while finding the last name: {}", lastName, ex);
            throw new RuntimeException("Error occured while querying for last name", ex);
        }
    }

    /**
     * TODO 
     */

    @Override
    public User findByUsername(String username) throws DataAccessException {
       try {
            TypedQuery<User> query = this.em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (Exception ex) {
            logger.error("Error occurred while finding user by id: {}", username, ex);
            throw new RuntimeException("Error occurred while finding user by id", ex);
        }
    }

    /**
     * TODO 
     */

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findByGender(Gender gender) throws DataAccessException {
        try {
            Query query = this.em.createQuery("SELECT u FROM User u WHERE u.gender = gender:", User.class);
            query.setParameter("gender", gender);
            return query.getResultList();
        } catch (Exception ex) {
            logger.error("Error occurred while finding users with specified gender: {}", gender, ex);
            throw new RuntimeException("Error occurred while finding users by gender", ex);
        }
    }




}
    

