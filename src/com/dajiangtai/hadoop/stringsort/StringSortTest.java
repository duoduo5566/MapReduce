package com.dajiangtai.hadoop.stringsort;

import java.io.IOException;
import java.util.Arrays;

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
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Hadoop程序基础模板
 */
public class StringSortTest extends Configured implements Tool {

	public static class MapClass extends Mapper< Text,Text,Text,Text> {
	
		public void map(Text key, Text value, Context context) throws IOException, InterruptedException {	
			// 每行数据格式: hello
			char[] strs = key.toString().toCharArray();
			Arrays.sort(strs);
			// 输出(key: key按字典顺序排序后的值, value: key)
			context.write(new Text(new String(strs)), key);
		}
	}
	
	
	
	public static class ReduceClass extends Reducer< Text, Text, Text, Text> {
		public void reduce(Text key, Iterable< Text> values, Context context)  throws IOException, InterruptedException {
			String str = "";
			for(Text val:values) {
				if(str.length() > 0)
					str += ",";
				str += val.toString();
			}
			
			context.write(key, new Text(str));
		}
	}
	
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();	//读取配置文件
		
		Job job = new Job(conf, "StringSortTest");//新建一个任务
		job.setJarByClass(StringSortTest.class);//主类

		Path in = new Path(args[0]);
		Path out = new Path(args[1]);

		FileSystem hdfs = out.getFileSystem(conf);
		if (hdfs.isDirectory(out)) {
			hdfs.delete(out, true);
		}
		
		FileInputFormat.setInputPaths(job, in);//文件输入
		FileOutputFormat.setOutputPath(job, out);//文件输出
		
		job.setMapperClass(MapClass.class);//Mapper
		job.setReducerClass(ReduceClass.class);//Reducer
		
		job.setInputFormatClass(KeyValueTextInputFormat.class);//文件输入格式
		job.setOutputFormatClass(TextOutputFormat.class);//文件输出格式
		job.setOutputKeyClass(Text.class);//设置作业输出值 Key 的类 
		job.setOutputValueClass(Text.class);//设置作业输出值 Value 的类 
		
		System.exit(job.waitForCompletion(true)?0:1);//等待作业完成退出	
		return 0;
	}

	/**
	 * @param args 输入文件、输出路径，可在Eclipse的Run Configurations中配Arguments如：
	 * hdfs://single.hadoop.dajiangtai.com:9000/stringsort/
	 * hdfs://single.hadoop.dajiangtai.com:9000/stringsort/out/
	 */
	public static void main(String[] args) {
		try {
			int res = ToolRunner.run(new Configuration(), new StringSortTest(), args);
			System.exit(res);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
	