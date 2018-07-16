package com.miniworld.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;


import freemarker.template.Configuration;
import freemarker.template.Template;

public class CreateHtmlUtil{

    /**
     * 通过freemarker生成静态HTML页面
     * @param state                 判断PC端或移动端的状态位
     * @param seasonId              以赛季ID作为一层目录，用于区分不同赛季的静态页面
     * @param templateName          模版名称
     * @param targetFileName        模版生成后的文件名
     * @param map                   freemarker生成的数据都存储在MAP中，
     */
    public static void createHtml(int state,String seasonId,String templateName,String targetFileName,Map<String, Object> map) throws Exception{
    	//从参数文件中获取指定输出路径 ,路径示例：C:/Workspace/shop-test/src/main/webapp/html
    	String path = CreateHtmlUtil.class.getClassLoader().getResource("").getPath();  
    	//执行后rootPath项目在服务器保存路径
    	String rootPath = path.substring(0, path.indexOf("/WEB-INF/")); 
        //创建fm的配置
        Configuration config = new Configuration(Configuration.getVersion());
        //指定默认编码格式 
        config.setDefaultEncoding("UTF-8");
        //设置模版文件的路径 
        if(state == 1){
        	config.setDirectoryForTemplateLoading(new File(rootPath+"/WEB-INF/ftl/pc"));
        }else{
        	config.setDirectoryForTemplateLoading(new File(rootPath+"/WEB-INF/ftl/mobile"));
        }
        

        //获得模版包
        Template template = config.getTemplate(templateName);
        String rootPath1=null;
        //判断需要生成的模版是pc版的还是移动版的
        if(state == 1){
//        	rootPath1=rootPath+"/WEB-INF/html/" + seasonId + "/pc";
        	rootPath1="/home/eventsys/"+seasonId+"/pc";
        	
            File file=new File(rootPath1);
            
            if(!file.exists()){
            	//判断文件夹是否存在不存在的话创建一个
            	file.setWritable(true, false);
            	file.mkdirs();
            }
        }else{
//        	rootPath1=rootPath+"/WEB-INF/html/" + seasonId + "/mobile";
        	rootPath1="/home/eventsys/"+seasonId+"/mobile";
            File file=new File(rootPath1);
            if(!file.exists()){
            	//判断文件夹是否存在不存在的话创建一个
            	file.setWritable(true, false);
            	file.mkdirs();
            }
        }
        
        
        //定义输出流，注意必须指定编码
        File file1=new File(rootPath1+"/"+targetFileName);
        file1.setWritable(true,false);
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1),"UTF-8"));
        
        //生成模版
        template.process(map, writer);
    }

}