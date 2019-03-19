package hujie.scut.plceditor.service;

import hujie.scut.plceditor.dao.UserDao;
import hujie.scut.plceditor.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findUser(User user){
        return userDao.findUser(user);
    }
}
