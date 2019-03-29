package hujie.scut.plceditor.controller;

import com.alibaba.fastjson.JSONObject;
import hujie.scut.plceditor.model.Configinfo;
import hujie.scut.plceditor.model.Project;
import hujie.scut.plceditor.model.ProjectFiles;
import hujie.scut.plceditor.service.ProjectService;
import hujie.scut.plceditor.utils.ConstantUtils;
import hujie.scut.plceditor.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // 注意：仅供测试使用
    @RequestMapping(path = "/getOnePro", method = RequestMethod.GET)
    @ResponseBody
    public String getOnePro(@RequestParam String proName, @RequestParam String username) {

        Project result = projectService.findOneProject(proName, username);
        if(result == null){
            return "failed";
        }else{
            System.out.println(result);
            System.out.println(JSONObject.toJSONString(result));
            return JSONObject.toJSONString(result);
        }
    }

    // 注意：仅供测试使用
    @RequestMapping(path = "/getAllPro", method = RequestMethod.GET)
    @ResponseBody
    public String getAllPro(@RequestParam String proName, @RequestParam String username) {

        ArrayList<Project> result = (ArrayList)projectService.findAllProjects(proName, username);
        if(result.size() == 0){
            return "failed";
        }else{
            for (Project project : result) {
                System.out.println(project);
                System.out.println(JSONObject.toJSONString(project));
                System.out.println("-----------------------------");
            }
            return JSONObject.toJSONString(result);
        }
    }

    /**
     *  创建一个工程
     * @param userid
     * @param projectName
     * @return
     */
    @PostMapping("/{userid}/project/{projectName}")
    @ResponseBody
    public String createNewProject(@PathVariable("userid")  String userid, @PathVariable("projectName") String projectName) {

        //查找该用户数据库中是否有同名工程
        ArrayList<Project> result = (ArrayList)projectService.findProsByUserIdAndProName(userid, projectName);

        if(result.size() > 0){
            JSONObject returnInfo = new JSONObject();
            returnInfo.put("message", "the project '" + projectName + "' has been exists ! ");
            return returnInfo.toJSONString();
        }else{
            //创建工程目录
            String proPath = ConstantUtils.PROPATH + userid + "/" + projectName;
            File proDir = new File(proPath);
            proDir.mkdir();
            System.out.println(proPath + "创建成功!");

            //创建Resource目录
            String resPath = ConstantUtils.PROPATH + userid + "/" + projectName + "/Resource/";
            File resDir = new File(resPath);
            resDir.mkdir();
            System.out.println(resPath + "创建成功!");

            //创建Result目录
            String resultPath = ConstantUtils.PROPATH + userid + "/" + projectName + "/Result/";
            File resultDir = new File(resultPath);
            resultDir.mkdir();
            System.out.println(resultPath + "创建成功!");

            //创建result的xml文件及其html保存文件
            String xmlPath = resultPath + projectName + ".xml";
            String xhtmlPath = resultPath + projectName + ".html";
            FileUtils.createXHtml(xmlPath, xhtmlPath);

            // 向数据库中插入该条工程数据
            Project project = new Project();
            project.setName(projectName);
            project.setUsername(userid);
            project.setInUse(true);
            project.setPath(proPath);
            Configinfo configInfo = new Configinfo();
            configInfo.setDeployIP("192.168.1.12");
            configInfo.setShowWholePro(true);
            project.setConfigInfo(configInfo);
            ArrayList<ProjectFiles> list = new ArrayList<ProjectFiles>();
            list.clear();
            project.setFiles(list);
            projectService.insertProject(project);

            JSONObject returnInfo = new JSONObject();
            returnInfo.put("message", "create ok !");
            return returnInfo.toJSONString();
        }
    }

    /**
     *  删除一个工程
     * @param userid
     * @param projectName
     * @return
     */
    @DeleteMapping("/{userid}/project/{projectName}")
    @ResponseBody
    public String deleteProject(@PathVariable("userid")  String userid, @PathVariable("projectName") String projectName) {

        //查找该用户数据库中是否有同名工程
        ArrayList<Project> result = (ArrayList)projectService.findProsByUserIdAndProName(userid, projectName);

        if(result.size() > 0){
            Project delProject = result.get(0);
            projectService.deleteProject(delProject);

            // 获取需要被删除工程的工程目录
            String proPath = ConstantUtils.PROPATH + userid + "/" + projectName;
            File delFile = new File(proPath);
            // 删除该目录及其下面所有文件和文件夹
            FileUtils.deleteFile(delFile);
            JSONObject returnInfo = new JSONObject();
            returnInfo.put("message", "the project '" + projectName + "' has been delete ! ");
            return returnInfo.toJSONString();
        }else{
            JSONObject returnInfo = new JSONObject();
            returnInfo.put("message", "delete project '" + projectName + "' fail ! ");
            return returnInfo.toJSONString();
        }
    }

    /**
     *  加载工程
     * @param userid
     * @param projectName
     * @return
     */
    @GetMapping("/{userid}/project/{projectName}")
    @ResponseBody
    public String loadProject(@PathVariable("userid")  String userid, @PathVariable("projectName") String projectName) {

        //查找该用户数据库中是否有同名工程
        ArrayList<Project> result = (ArrayList)projectService.findProsByUserIdAndProName(userid, projectName);

        if(result.size() > 0){
            Project loadProject = result.get(0);
            return JSONObject.toJSONString(loadProject);
//            JSONObject returnInfo = new JSONObject();
//            returnInfo.put("Data", loadProject);
//            return returnInfo.toJSONString();
        }else{
            JSONObject returnInfo = new JSONObject();
            returnInfo.put("message", "load project '" + projectName + "' fail ! ");
            return returnInfo.toJSONString();
        }
    }

    /**
     * 加载某用户的工程列表，成功则返回所有工程相关信息
     * @param userid
     * @return
     */
    @GetMapping("/{userid}/projects")
    @ResponseBody
    public String loadProjectlists(@PathVariable("userid")  String userid) {

        //查找该用户数据库中是否有工程
        ArrayList<Project> projects = (ArrayList<Project>)projectService.findProsByUserId(userid);

        if(projects.size() > 0){
            ArrayList<JSONObject> objects = new ArrayList<JSONObject>();
            for(Project project : projects){
                JSONObject fileInfo = new JSONObject();
                fileInfo.put("name", project.getName());
                fileInfo.put("inUse", project.getInUse());
                objects.add(fileInfo);
            }

            JSONObject projectsInfo = new JSONObject();
            projectsInfo.put("projects", objects);
            return projectsInfo.toJSONString();

//            JSONObject returnInfo = new JSONObject();
//            returnInfo.put("Data", projectsInfo);
//            return returnInfo.toJSONString();
        }else{
            JSONObject returnInfo = new JSONObject();
            returnInfo.put("message", "load projects fail !");
            return returnInfo.toJSONString();
        }
    }

    /**
     * 工程重命名
     */

    /**
     * 关闭工程
     */
}
