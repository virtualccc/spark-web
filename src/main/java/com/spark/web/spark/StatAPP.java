package com.spark.web.spark;

import com.spark.web.dao.CourseClickDAO;
import com.spark.web.doman.CourseClickCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WEB层，课程关系和课程名称（一般在数据库写的）,这里在程序中直接定义
 */
@RestController
public class StatAPP {

    private static Map<String, String> course = new HashMap<>();

    static {
        course.put("112", "Spark SQL日志分析");
        course.put("128", "10小时入门大数据");
        course.put("145", "深度学习之神经网络核心原理与算法");
        course.put("146", "强大的Node.js在web开发的应用");
        course.put("131", "Vue+Django实战");
        course.put("130", "web前端性能优化");

    }
    //报错，需要将dao和count交给spring进行管理
    @Autowired
    CourseClickDAO courseClickDAO;


//    @RequestMapping(value = "/courseClickCount_dynamic",method = RequestMethod.GET)
//    public ModelAndView courseClickCount()throws Exception{
//
//        ModelAndView view = new ModelAndView("index");
//
//        //这个参数应该是传过来的，需要自己实现
//        List<CourseClickCount> list = courseClickDAO.query("20171022");
//        JSONArray json = null;
//        for(CourseClickCount model :list){
//            //已有的名称从第9个开始取
//            model.setName(course.get(model.getName().substring(9)));
//        }
//        json = JSONArray.fromObject(list);
//        view.addObject("data_josn", json);
//        return view;//需要使用Json返回
//    }

    @RequestMapping(value = "/courseClickCount_dynamic",method = RequestMethod.POST)
    @ResponseBody
    public List<CourseClickCount> courseClickCount()throws Exception{

        //这个参数应该是传过来的，需要自己实现
        List<CourseClickCount> list = courseClickDAO.query("20190427");
        for(CourseClickCount model :list){
            //已有的名称从第9个开始取
            model.setName(course.get(model.getName().substring(9)));
        }

        return list;//需要使用Json返回
    }


    @RequestMapping(value = "/echarts",method = RequestMethod.GET)
    public ModelAndView echarts(){
        return new ModelAndView("echarts");
    }

}
