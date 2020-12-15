package com.cy.controller;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author 86158
 * 注意：使用ftl模板
 */
@Controller
public class TestController {

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public TestController(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    /**
     * 方式1： 这里map必须使用参数 否则会报错
     * 不推荐使用
     *
     * @param map 参数
     * @return 页面 "index.ftl"
     */
    @RequestMapping("/test1")
    public String test1(Map<Object, Object> map) {
        map.put("username", "张三");
        map.put("password", "123456");
        map.put("test", "测试1");
        try {
            Configuration configuration = new Configuration(Configuration.getVersion());
            FileTemplateLoader templateLoader = new FileTemplateLoader(new File("classpath:/templates/index.ftl"));
            // FileTemplateLoader templateLoader = new FileTemplateLoader(new File("classpath:/templates"));
            // java.io.FileNotFoundException: classpath:\templates\index.ftl does not exist. 此处会报错
            configuration.setTemplateLoader(templateLoader);
            Template template = configuration.getTemplate("index.ftl");
            Writer writer = new StringWriter();
            template.process(map, writer);
            // 这里如果想要获取填充好数据的html，那么
            String s = writer.toString();
            System.out.println("将参数和ftl模板合并之后转为的字符串 = " + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }


    /**
     * 方式2：
     * 1）通过获取容器中的 FreeMarkerConfigurer.getConfiguration()方法获取Configuration，然后FreeMarkerTemplateUtils调用
     * 2）浏览器会直接解析返回的字符串（html格式）
     * 3）使用该方式更香  简单快捷
     * 4) 通过map传参数  优先使用该方法
     *
     * @param map 参数
     * @return String字符串（html格式）
     */
    @RequestMapping("/test2")
    @ResponseBody
    public String test2(Map<Object, Object> map) {
        String s = "";
        map.put("username", "张三");
        map.put("password", "123456");
        map.put("test", "测试2");
        try {
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("index.ftl");
            s = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 此处可返回页面省略后缀的页面如："index"  / 返回html格式的字符串
        return s;
    }

    /**
     * 方式3：使用model传入 import org.springframework.ui.Model;
     *
     * @param model 参数
     * @return 页面 "index.ftl"
     */
    @RequestMapping("/test3")
    @ResponseBody
    public String test3(Model model) {
        String s = "";
        model.addAttribute("username", "张三");
        model.addAttribute("password", "123456");
        model.addAttribute("test", "测试3");
        try {
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("index.ftl");
            s = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
