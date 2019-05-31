package com.spark.web.spark;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController//生命周期交给这个管理
public class HelloBoot {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)//如果请求是Hello就执行这个方法
    public  String HelloBoot(){
        return "Hello World Spring Boot ...";
    }

    @RequestMapping(value = "/first",method = RequestMethod.GET)
    public ModelAndView firstDemo(){
        return new ModelAndView("test");
    }

    @RequestMapping(value = "/CourseClick",method = RequestMethod.GET)
    public ModelAndView CourseClickCountSat(){
        return new ModelAndView("demo");
    }



}
