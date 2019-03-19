package hujie.scut.plceditor.dao;

import hujie.scut.plceditor.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserDaoImpl implements UserDao {

    @Resource
    private MongoTemplate mongoTemplate;
//.and("password").gte(user.getPassword())
    @Override
    public User findUser(User user) {
//        Query query = new Query(Criteria.where("username").is(user.getUsername()).andOperator(Criteria.where("password").is(user.getPassword())));
        Query query = new Query(Criteria.where("username").is(user.getUsername()).and("password").is(user.getPassword()));
        return mongoTemplate.findOne(query, User.class);
    }
}
