package com.raguzf.roommatch.repository;

import java.util.Collection;
import org.springframework.dao.DataAccessException;
import com.raguzf.roommatch.model.Gender;
import com.raguzf.roommatch.model.Role;
import com.raguzf.roommatch.model.User;

public interface UserRepository {
   
    User findById(Integer id) throws DataAccessException;
    Collection<User> findByRole(Role role) throws DataAccessException;
    void save(User user) throws DataAccessException;
    Collection<User> findAll() throws DataAccessException;
    void delete(User user) throws DataAccessException;

    Collection<User> findByFirstName(String firstName) throws DataAccessException;
    Collection<User> findByLasttName(String lastName) throws DataAccessException;
    User findByUsername(String username) throws DataAccessException;
    Collection<User> findByGender(Gender gender) throws DataAccessException;

    
}
