package treemas.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import treemas.backend.Model.User;



//this is used for calling the UserRepository whenever you need to// 
@Repository
public interface UserRepository extends JpaRepository<User, String>{

    boolean existsByNik(String nik);
    User findByNik(String nik);
    String findSaltByNik(String nik);
    String findPasswordByNik(String nik);
}