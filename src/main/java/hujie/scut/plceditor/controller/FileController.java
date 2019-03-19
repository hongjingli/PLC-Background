package hujie.scut.plceditor.controller;

import com.alibaba.fastjson.JSONObject;
import hujie.scut.plceditor.model.ProjectFiles;
import hujie.scut.plceditor.utils.ConstantUtils;
import hujie.scut.plceditor.utils.FileUtils;
import hujie.scut.plceditor.model.Project;
import hujie.scut.plceditor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class FileController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/{userid}/project/{projectName}/file/{fileName}", method = POST, consumes="application/json")
    @ResponseBody
    public String newFile(@PathVariable String userid, @PathVariable String projectName, @PathVariable String fileName, @RequestBody String jsonParamStr) {
//        System.out.println("userid : " + userid);
//        System.out.println("projectName : " + projectName);
//        System.out.println("fileName : " + fileName);
//        System.out.println("jsonParamStr : " + jsonParamStr);
//        JSONObject object = JSONObject.parseObject(jsonParamStr);
//        System.out.println("jsonParamStr.content : " + object.getString("content"));
          // 1.找到该文档对象
          ArrayList<Project> list = (ArrayList<Project>)projectService.findProsByUserIdAndProName(userid, projectName);
          // 如果该用户的该工程不存在，则报错并返回
          if(list.size() == 0){
              JSONObject returnInfo = new JSONObject();
              returnInfo.put("message", "fail ! ");
              return returnInfo.toJSONString();
          }
          // 判断该工程中是否有该文件同名文件，如果有则报错返回
          Project project = list.get(0);
          System.out.println("Project : " + project.toString() + "-------" + project.getFiles().size());
          List<ProjectFiles> files = project.getFiles();
          for(ProjectFiles file : files){
              System.out.println(file.getName() + "-----" + fileName);
              if(file.getName().equals(fileName)){
                  JSONObject returnInfo = new JSONObject();
                  returnInfo.put("message", "File '" + fileName + "has been existed !");
                  return returnInfo.toJSONString();
              }
          }
        // 2.创建相关的html文件和缓存文件
        // 工程目录
        String proPath = ConstantUtils.PROPATH + userid + "/" + projectName;
        // 资源文件目录
        String resPath = ConstantUtils.PROPATH + userid + "/" + projectName + "/Resource/";

        JSONObject object = JSONObject.parseObject(jsonParamStr);
        String fileType = object.getString("type");
        String relLanguage = object.getString("language");

        // 需要创建的html文件路径
        String htmlPath = resPath + fileName + ".html";
        // 需要创建的缓存文件路径
        String filePath = resPath + fileName + "." + relLanguage;
        // 需要创建的图形文件缓存文件，如果有必要的话
        String grapXmlPath = resPath + fileName + ".gxml";
        FileUtils.createFiles(htmlPath, filePath, relLanguage, grapXmlPath);

        // 3.插入该文件数据
        ProjectFiles file = new ProjectFiles();
        file.setName(fileName);
        file.setFileType(fileType);
        file.setPath(proPath);
        file.setRelLanguage(relLanguage);
        file.setIsSave(false);
        file.setResource(resPath);
        files.add(file);

        for(ProjectFiles f : files){
            System.out.println(f.toString());
        }

        projectService.updateProjectFiles(project, files);

        JSONObject returnInfo = new JSONObject();
        returnInfo.put("message", "File '" + fileName + "create ok !");
        return returnInfo.toJSONString();
    }

//    @RequestMapping(value = "/{userid}/project/{projectName}/file/{fileName}", method = PUT, consumes="application/json")
//    @ResponseBody
//    public String newFile(@PathVariable String userid, @PathVariable String projectName, @PathVariable String fileName) {
//
//        return null;
//    }

    @RequestMapping(path = "/xml2json", method = RequestMethod.GET)
    @ResponseBody
    public void xmlJson() {
        // 读取json文件内容，获取json字符串
        String filePath = "F:/plcUserPros/test/graph.json";
        String content = FileUtils.readFileContent(filePath);
        // 将json字符串转换为xml字符串并打印
        String xml = FileUtils.json2xml(content);
        System.out.println(xml);
        System.out.println("------------------------华丽的分割线-----------------------");
        // 将前面转换的xml字符串转换为json字符串
        String json = FileUtils.xml2json(xml);
        System.out.println(json);
    }
}
