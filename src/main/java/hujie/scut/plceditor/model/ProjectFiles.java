package hujie.scut.plceditor.model;

/**
 * 工程文件类，一个工程文件包含如下内容：
 * {
 * 	"name" : "test",
 * 	"path" : "/users/jack/xxxx",
 * 	"fileType" : "Program",
 * 	"relLanguage" : "ST",
 * 	"isSave" : true,
 * 	"resource" : "/users/jack/xxxx/Resource"
 * }
 */
public class ProjectFiles{
    private String name;
    private String path;
    private String fileType;
    private String relLanguage;
    private boolean isSave;
    private String resource;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }

    public void setFileType(String filetype) {
        this.fileType = filetype;
    }
    public String getFileType() {
        return fileType;
    }

    public void setRelLanguage(String rellanguage) {
        this.relLanguage = rellanguage;
    }
    public String getRelLanguage() {
        return relLanguage;
    }

    public void setIsSave(boolean issave) {
        this.isSave = issave;
    }
    public boolean getIsSave() {
        return isSave;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
    public String getResource() {
        return resource;
    }

    @Override
    public String toString() {
        return "ProjectFiles{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", fileType='" + fileType + '\'' +
                ", relLanguage='" + relLanguage + '\'' +
                ", isSave=" + isSave +
                ", resource='" + resource + '\'' +
                '}';
    }
}
