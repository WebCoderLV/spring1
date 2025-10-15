package org.arturs.firstSpring.repositories;

import org.arturs.firstSpring.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryInterface extends JpaRepository<UserModel, Long> {

}
