package hujie.scut.plceditor.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 工程类，一个工程中的内容如下：
 * {
 * 	"name" : "xxxx",
 * 	"username" : "jack",
 *   "inUse" : true,
 * 	"path" : "/client/public/users/jack/xxxx",
 * 	"configInfo":{
 * 	"showWholePro": false,
 * 	"deployIP": "xxxx.xxxx.xxxx.xxxx"
 *        },
 *     "files" : [
 *         {
 *             "name" : "test",
 *             "path" : "/users/jack/xxxx",
 *             "fileType" : "Program",
 *             "relLanguage" : "ST",
 *             "isSave" : true,
 *             "resource" : "/users/jack/xxxx/Resource"
 *         }
 *     ]
 * }
 */
@Document(collection = "projects")
public class Project {
    private String name;
    private String username;
    private boolean inUse;
    private String path;
    private Configinfo configInfo;
    private List<ProjectFiles> files;

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setInUse(boolean inuse) {
        this.inUse = inuse;
    }
    public boolean getInUse() {
        return inUse;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }

    public void setConfigInfo(Configinfo configinfo) {
        this.configInfo = configinfo;
    }
    public Configinfo getConfigInfo() {
        return configInfo;
    }

    public void setFiles(List<ProjectFiles> files) {
        this.files = files;
    }
    public List<ProjectFiles> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", inUse=" + inUse +
                ", path='" + path + '\'' +
                ", configInfo=" + configInfo +
                ", files=" + files +
                '}';
    }
}

