package cn.imooc.demo.springboot.es.controller;

import cn.imooc.demo.springboot.es.entity.es.EsBlog;
import cn.imooc.demo.springboot.es.entity.mysql.MysqlBlog;
import cn.imooc.demo.springboot.es.repository.es.EsBlogRepository;
import cn.imooc.demo.springboot.es.repository.mysql.MysqlBlogRepository;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author ZEXIN HUANG
 * @version 1.0
 * @date 2020-05-19 00:02
 */
@RestController
public class DataController {
    @Autowired
    MysqlBlogRepository mysqlBlogRepository;
    @Autowired
    EsBlogRepository esBlogRepository;

    @GetMapping("/blogs")
    public Object blog(){
        List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryAll();
        return mysqlBlogs;
    }

    @PostMapping("/search")
    public Object search(@RequestBody Param param){
        HashMap<String,Object> map = new HashMap<>();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String type = param.getType();
        if(type.equalsIgnoreCase("mysql")){
            List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryBlogs(param.getKeyword());
            map.put("list",mysqlBlogs);
        }else if(type.equalsIgnoreCase("es")){
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title",param.getKeyword()));
            builder.should(QueryBuilders.matchPhraseQuery("content",param.getKeyword()));
            String s = builder.toString();
            System.out.println(s);
            Page<EsBlog> search = (Page<EsBlog>)esBlogRepository.search(builder);
            List<EsBlog> content = search.getContent();
            map.put("list",content);
        }else{
            return "i dont know";
        }
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        map.put("duration",totalTimeMillis);
        return map;
    }


    @GetMapping("blog/{id}")
    public Object blog(@PathVariable("id") Integer id){
        Optional<MysqlBlog> byId= mysqlBlogRepository.findById(id);
        MysqlBlog blog = byId.get();
        return blog;
    }

    @Data
    public static class Param{
        //mysql , es
        private String type;

        private String keyword;

    }

}
