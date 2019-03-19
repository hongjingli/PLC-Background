package hujie.scut.plceditor.dao;

import hujie.scut.plceditor.model.Project;
import hujie.scut.plceditor.model.ProjectFiles;

import java.util.List;

public interface ProjectDao {
    Project getOneProject(String proName, String username);

    List<Project> getAllProjects(String proName, String username);

    List<Project> findProsByUserIdAndProName(String userid, String projectName);

    void insertProject(Project project);

    void deleteProject(Project delProject);

    void updateProjectFiles(Project project, List<ProjectFiles> files);

    List<Project> findProsByUserId(String userid);
}
