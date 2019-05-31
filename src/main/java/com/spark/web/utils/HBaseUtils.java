package com.spark.web.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HBaseUtils {
    HBaseAdmin admin = null;
    Configuration configuration = null;


    /**
     * 私有改造方法
     */
    private HBaseUtils(){
        configuration = new Configuration();
        //地址在hbase-site.xml下有设置
        configuration.set("hbase.zookeeper.quorum", "mini1:2181");
        configuration.set("hbase.rootdir", "hdfs://mini1:8080/hbase");

        try {
            this.admin = new HBaseAdmin(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HBaseUtils instance = null;

    public  static synchronized HBaseUtils getInstance() {
        if(null == instance) {
            instance = new HBaseUtils();
        }
        return instance;
    }


    /**
     * 根据表名获取到HTable实例（HTable实例就是Hbase中的一张表）
     */
    public HTable getTable(String tableName) {

        HTable table = null;

        try {
            table = new HTable(configuration, tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

    /**
     * 根据表名和输入条件获取HBase记录数
     * @param tableName
     * @param condition
     * @return
     */
    public Map<String,Long> query(String tableName,String condition) throws Exception{
        Map<String,Long> map = new HashMap<>();

        HTable table = getTable(tableName);
        String cf = "info";
        String qualifier = "click_count";

        Scan scan =new Scan();

        Filter filter = new PrefixFilter(Bytes.toBytes(condition));
        scan.setFilter(filter);

        ResultScanner rs = table.getScanner(scan);
        for(Result result:rs){
            String row= Bytes.toString(result.getRow());
            long clickCount = Bytes.toLong(result.getValue(cf.getBytes(),qualifier.getBytes()));

            map.put(row,clickCount);

        }
        return map;
    }

//Map.Entry是为了更方便的输出map键值对。一般情况下，要输出Map中的key 和 value  是先得到key的集合keySet()，然后再迭代（循环）由每个key得到每个value。values()方法是获取集合中的所有值，不包含键，没有对应关系。而Entry可以一次性获得这两个值。
    public static void main(String[] args) throws Exception {
        //如果报静态方法不能调用的错误，就用工具类.getInstance().创造实例来调用
        Map<String,Long> map =   HBaseUtils.getInstance().query("imooc_course_clickcount", "20190427");

        for(Map.Entry<String,Long> entry:map.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }

    }

}
