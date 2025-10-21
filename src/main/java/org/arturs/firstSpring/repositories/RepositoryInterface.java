package org.arturs.firstSpring.repositories;

import java.util.Optional;

import org.arturs.firstSpring.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryInterface extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByNameAndPassword(String name, String password);

}
