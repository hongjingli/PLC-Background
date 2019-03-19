package hujie.scut.plceditor.model;

/**
 * 配置类，一个配置内容如下：
 * {
 * 	"showWholePro": false,
 * 	"deployIP": "xxxx.xxxx.xxxx.xxxx"
 *  }
 */
public class Configinfo{
    private boolean showWholePro;
    private String deployIP;
    public void setShowWholePro(boolean showwholepro) {
        this.showWholePro = showwholepro;
    }
    public boolean getShowWholePro() {
        return showWholePro;
    }

    public void setDeployIP(String deployip) {
        this.deployIP = deployip;
    }

    public String getDeployIP() {
        return deployIP;
    }

    @Override
    public String toString() {
        return "Configinfo{" +
                "showWholePro=" + showWholePro +
                ", deployIp='" + deployIP + '\'' +
                '}';
    }
}
