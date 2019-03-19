package hujie.scut.plceditor.dao;

import hujie.scut.plceditor.model.Project;
import hujie.scut.plceditor.model.ProjectFiles;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ProjectDaoImpl implements ProjectDao{

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Project getOneProject(String proName, String username) {
        Query query = new Query(Criteria.where("name").is(proName).andOperator(Criteria.where("username").is(username)));
        return mongoTemplate.findOne(query, Project.class);
    }

    @Override
    public List<Project> getAllProjects(String proName, String username) {
        Query query = new Query(Criteria.where("name").is(proName).andOperator(Criteria.where("username").is(username)));
        return mongoTemplate.find(query, Project.class);
    }

    @Override
    public List<Project> findProsByUserIdAndProName(String userid, String projectName) {
        Query query = new Query(Criteria.where("username").is(userid).and("name").is(projectName));
        return mongoTemplate.find(query, Project.class);
    }

    @Override
    public void insertProject(Project project) {
        mongoTemplate.save(project);
    }

    @Override
    public void deleteProject(Project delProject) {
        Query query = new Query(Criteria.where("username").is(delProject.getUsername()).and("name").is(delProject.getName()));
        mongoTemplate.remove(query, Project.class);
    }

    @Override
    public void updateProjectFiles(Project project, List<ProjectFiles> files) {
        Query query = new Query(Criteria.where("username").is(project.getUsername()).and("name").is(project.getName()));
        Update update = Update.update("files", files);
        mongoTemplate.upsert(query, update, Project.class);
    }

    @Override
    public List<Project> findProsByUserId(String userid) {
        Query query = new Query(Criteria.where("username").is(userid));
        return mongoTemplate.find(query, Project.class);
    }
}
