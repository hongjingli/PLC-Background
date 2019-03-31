package hujie.scut.plceditor.dao;

import hujie.scut.plceditor.model.Project;
import hujie.scut.plceditor.model.ProjectFiles;

import java.util.List;

public interface ProjectDao {
    Project getOneProject(String proName, String username);

    List<Project> getAllProjects(String proName, String username);

    List<Project> findProsByUserIdAndProName(String userid, String projectName);

    ProjectFiles findFileByUsername_proname_filename(String username, String projectName, String fileName);

    void insertProject(Project project);

    void saveProject(Project project);

    void deleteProject(Project delProject);

    void deleteProjectFiles(String userName, String projectName, String fileName);

    void saveProjectFiles(String userName, String projectName, String fileName);

    void updateProjectFiles(Project project, List<ProjectFiles> files);

    List<Project> findProsByUserId(String userid);
}
