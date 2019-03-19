package hujie.scut.plceditor.utils;

import org.json.JSONObject;
import org.json.XML;

import java.io.*;

public class FileUtils {

    /**
     * 先根遍历序递归删除文件夹
     *
     * @param dirFile 要被删除的文件或者目录
     * @return 删除成功返回true, 否则返回false
     */
    public static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }
        return dirFile.delete();
    }

    /**
     * 文件拷贝功能，将modelFilePath内容拷贝到xhtmlPath文件中
     * @param xhtmlPath
     * @param modelFilePath
     */
    private static void copyHtmlModelFile(String xhtmlPath, String modelFilePath){
        // 封装数据源
        BufferedReader br = null;
        // 封装目的地
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(modelFilePath));
            bw = new BufferedWriter(new FileWriter(xhtmlPath));
            // 读写数据
            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
                bw.flush();
            }
            // 释放资源
            bw.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(xhtmlPath + "创建成功!");
    }

    public static String readFileContent(String filePath){
        BufferedReader br = null;
        StringBuffer fileContent = new StringBuffer();
        try {
            br = new BufferedReader(new FileReader(filePath));
            // 读写数据
            String line = null;
            while ((line = br.readLine()) != null) {
                fileContent.append(line);
            }
            // 释放资源
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent.toString();
    }
    /**
     *  创建XML文件
     * @param xmlPath
     * @param xhtmlPath
     */
    public static void createXHtml(String xmlPath, String xhtmlPath){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(xmlPath));
            bw.write("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>" + "\n");
            System.out.println(xmlPath + "创建成功!");
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        copyHtmlModelFile(xhtmlPath, ConstantUtils.XMLMODELPATH);
    }

    /**
     * 创建一系列各种语言文件
     * @param htmlPath
     * @param filePath
     * @param relLanguage
     * @param grapXmlPath
     */
    public static void createFiles(String htmlPath, String filePath, String relLanguage, String grapXmlPath) {
        if(relLanguage.equals(ConstantUtils.ST)){
            createSpecFile(htmlPath, filePath, ConstantUtils.STMODELPATH);
        }else if(relLanguage.equals(ConstantUtils.SFC)){
            createSpecFile(htmlPath, filePath, ConstantUtils.SFCMODELPATH, grapXmlPath);
        }else if(relLanguage.equals(ConstantUtils.FB) || relLanguage.equals(ConstantUtils.FBD) || relLanguage.equals(ConstantUtils.LD)){
            createSpecFile(htmlPath, filePath, ConstantUtils.GRAPHMODELPATH, grapXmlPath);
        }else if(relLanguage.equals(ConstantUtils.CONFIG)){
            createSpecFile(htmlPath, filePath, ConstantUtils.CONFIGMODELPATH);
        }else if(relLanguage.equals(ConstantUtils.IL)){
            createSpecFile(htmlPath, filePath, ConstantUtils.ILMODELPATH);
        }else if(relLanguage.equals(ConstantUtils.RPC)){
            createSpecFile(htmlPath, filePath, ConstantUtils.RPCMODELPATH);
        }else {
            System.out.println("unknown type : " + relLanguage);
        }
    }

    /**
     * 创建普通文本文件
     * @param htmlPath
     * @param filePath
     * @param modelFilePath
     */
    private static void createSpecFile(String htmlPath, String filePath, String modelFilePath) {
        File stFile = new File(filePath);
        try {
            stFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        copyHtmlModelFile(htmlPath, modelFilePath);
    }

    /**
     *  创建图形语言文件
     * @param htmlPath
     * @param filePath
     * @param modelFilePath
     * @param grapXmlPath
     */
    private static void createSpecFile(String htmlPath, String filePath, String modelFilePath, String grapXmlPath) {
        File spFile = new File(filePath);
        File gxmlFile = new File(grapXmlPath);
        try {
            spFile.createNewFile();
            gxmlFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        copyHtmlModelFile(htmlPath, modelFilePath);
    }

    /**
     * json to xml
     * @param json
     * @return
     */
    public static String json2xml(String json) {
        JSONObject jsonObj = new JSONObject(json);
        return XML.toString(jsonObj);
    }

    /**
     * xml to json
     * @param xml
     * @return
     */
    public static String xml2json(String xml) {
        JSONObject xmlJSONObj = XML.toJSONObject(xml);
        return xmlJSONObj.toString();
    }
}
