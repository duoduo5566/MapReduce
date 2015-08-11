package com.dajiangtai.hadoop.salary;

import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 基于样本数据做Hadoop工程师薪资统计：计算各工作年限段的薪水范围
 */
public class SalaryCount extends Configured implements Tool {
	
    public static class SalaryMapper extends Mapper< Object, Text, Text, Text> {
    
         public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
             //每行数据格式：美团  3-5年经验  15-30k 北京   hadoop高级工程
             String[] record = value.toString().split( "\\s+");
             //输出(key:3-5年经验  value:15-30k)
             context.write( new Text(record[1]), new Text(record[2]) );
        }
    }

    public static class SalaryReducer extends Reducer< Text, Text, Text, Text> {
         public void reduce(Text Key, Iterable< Text> Values, Context context) throws IOException, InterruptedException {
        	 int low = 0;//记录最低工资
        	 int high = 0;//记录最高工资
        	 int count = 1;
        	 for (Text value : Values) {
            	 String[] arr = value.toString().split("-");
            	 int l = filterSalary(arr[0]);
            	 int h = filterSalary(arr[1]);
            	 if(count==1 || l< low){
            		 low = l;
            	 }
            	 if(count==1 || h>high){
            		 high = h;
            	 }
            	 count++;
             }
             context.write(Key, new Text(low + "-" +high + "k"));
        }
    }

	 /**
     * @ param salary 薪资字符串
     * @ function 使用正则表达式，解析出最低工资 low 和 最高工资 high
     */
    public static int filterSalary(String salary) {
        String sal = Pattern.compile("[^0-9]").matcher(salary).replaceAll("");
        return Integer.parseInt(sal);
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();//读取配置文件
        
        Job job = new Job(conf, "SalaryCount" );//新建一个任务
        job.setJarByClass(SalaryCount.class);// 主类

        Path in = new Path(args[0]);
        Path out = new Path(args[1]);

        FileSystem hdfs = out.getFileSystem(conf);
        //如果输出文件路径存在，先删除
        if (hdfs.isDirectory(out)) {
            hdfs.delete(out, true);
        }
        
        FileInputFormat.addInputPath(job, in);// 输入路径
        FileOutputFormat.setOutputPath(job, out);// 输出路径
        
        job.setMapperClass(SalaryMapper.class);// Mapper
        job.setMapOutputKeyClass(Text.class);// Mapper key输出类型
        job.setMapOutputValueClass(Text.class);// Mapper value输出类型
        
        job.setReducerClass(SalaryReducer.class);// Reducer
        
        System.exit(job.waitForCompletion(true)?0:1);//等待作业完成退出		
		return 0;
    }

    /**
     * @param args 输入文件、输出路径，可在Eclipse中Run Configurations中配Arguments，如：
     * hdfs://single.hadoop.dajiangtai.com:9000/junior/salary.txt
     * hdfs://single.hadoop.dajiangtai.com:9000/junior/salary-out/
     */
    public static void main(String[] args) throws Exception {
        try {
        	int res = ToolRunner.run(new Configuration(), new SalaryCount(), args);
        	System.exit(res);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}