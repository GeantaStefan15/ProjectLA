package Link.example.Project.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public void checkAndAddUser(String email, String password, String firstName, String lastName, String phoneNumber, int age) throws UserException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            if (password.length() < 3) {
                throw new UserException("Parola nu este buna");
            }
            if (firstName.length()<2&&lastName.length()<2) {
                throw new UserException("Nume invalid");
            }
            if(phoneNumber.length()>12) {
                throw  new UserException("nr tel nu este ok");
            }
            if (age<1 ) {
                throw new UserException("Varsta nu este buna");
            }
            User userToInsert = new User();
            userToInsert.setEmail(email);
            userToInsert.setPassword(password);
            userToInsert.setPhoneNumber(phoneNumber);
            userToInsert.setFirstName(firstName);
            userToInsert.setAge(age);
            userToInsert.setLastName(lastName);

            userDao.save(userToInsert);

        } else {
            throw new UserException("Utilizatorul exista deja");
        }
    }
    public void checkAndLoginUser(String email, String password) throws UserException{
        User user = userDao.findByEmail(email);
        if (user == null){
            throw new UserException("userul nu a putut fi autentificat");
        }
        if (!user.getPassword().equals(password)){
            throw new UserException("userul nu a putut fi autentificat");
        }
    }

    public int getUserId(String email){
        User user = userDao.findByEmail(email);
        return user.getId();
    }
}
