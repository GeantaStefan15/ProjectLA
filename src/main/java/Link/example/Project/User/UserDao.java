package Link.example.Project.User;

import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User,Integer> {
    User findByEmail (String email);//select from user where email="...'



}
