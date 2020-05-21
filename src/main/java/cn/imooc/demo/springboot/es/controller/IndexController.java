package cn.imooc.demo.springboot.es.controller;

import cn.imooc.demo.springboot.es.entity.mysql.MysqlBlog;
import cn.imooc.demo.springboot.es.repository.mysql.MysqlBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ZEXIN HUANG
 * @version 1.0
 * @date 2020-05-18 18:42
 */
@Controller
public class IndexController {


    @Autowired
    MysqlBlogRepository mysqlBlogRepository;

    @RequestMapping("/")
    public String index(){
        List<MysqlBlog> all = mysqlBlogRepository.findAll();
        System.out.println(all.size());
        return "index.html";
    }
}