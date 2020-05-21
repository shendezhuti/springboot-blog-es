package cn.imooc.demo.springboot.es.repository.es;

import cn.imooc.demo.springboot.es.entity.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author ZEXIN HUANG
 * @version 1.0
 * @date 2020-05-18 22:49
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,Integer> {

}
