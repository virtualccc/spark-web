package com.spark.web.dao;

import com.spark.web.doman.CourseClickCount;
import com.spark.web.utils.HBaseUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 课程访问数量数据访问层
 */


@Component//交给spring进行管理
public class CourseClickDAO {

    /**
     * 根据天进行查询
     * @param day
     * @return
     * @throws Exception
     */
    public List<CourseClickCount> query(String day) throws Exception{

        List<CourseClickCount> list = new ArrayList<>();

        //去HBase表中根据day获取实战课程对应的访问量
        Map<String,Long> map =   HBaseUtils.getInstance().query("imooc_course_clickcount", "20190427");

        for (Map.Entry<String, Long> entry:map.entrySet()){
            CourseClickCount model = new CourseClickCount();
            model.setName(entry.getKey());
            model.setValue(entry.getValue());

            list.add(model);
        }

        return list;
    }


    public static void main(String[] args) throws Exception {
        CourseClickDAO courseClickDAO=new CourseClickDAO();
        List<CourseClickCount> list = courseClickDAO.query("20190427");
        for (CourseClickCount model:list){
            System.out.println(model.getName()+":"+model.getValue());
        }
    }

}
