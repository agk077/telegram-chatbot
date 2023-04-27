package ru.liga.tinderserver.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.liga.tinderserver.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
