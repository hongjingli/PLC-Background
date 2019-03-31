package hujie.scut.plceditor.service;

import hujie.scut.plceditor.dao.ProjectDao;
import hujie.scut.plceditor.model.Project;
import hujie.scut.plceditor.model.ProjectFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectService {
    @Autowired
    private ProjectDao projectDao;

    public Project findOneProject(String proName, String username){
        return projectDao.getOneProject(proName, username);
    }

    public List<Project> findAllProjects(String proName, String username){
        return projectDao.getAllProjects(proName, username);
    }

    public List<Project> findProsByUserIdAndProName(String userid, String projectName) {
        return projectDao.findProsByUserIdAndProName(userid, projectName);
    }

    public ProjectFiles findFileByUsername_projectname_filename(String username, String projectName, String fileName){
        return projectDao.findFileByUsername_proname_filename(username, projectName, fileName);
    }

    public void insertProject(Project project){
        projectDao.insertProject(project);
    }

    public void deleteProject(Project delProject) {
        projectDao.deleteProject(delProject);
    }

    public void deleteProjectFiles(String userName, String projectName, String fileName){
        projectDao.deleteProjectFiles(userName, projectName, fileName);
    }

    public void updateProjectFiles(Project project, List<ProjectFiles> files) {
        projectDao.updateProjectFiles(project, files);
    }

    public List<Project> findProsByUserId(String userid) {
        return projectDao.findProsByUserId(userid);
    }
}
