package com.dajiangtai.hadoop.unittest;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import com.dajiangtai.hadoop.stringsort.StringSortTest;
/**
 * Mapper 端的单元测试
 */
@SuppressWarnings("all")
public class StringSortTester {
	private Mapper mapper;//定义一个Mapper对象
	private MapDriver driver;//定义一个MapDriver 对象

	@Before
	public void init() {
		mapper = new StringSortTest.MapClass();//实例化一个Temperature中的TemperatureMapper对象
		driver = new MapDriver(mapper);//实例化MapDriver对象
	}

	@Test
	public void test() throws IOException {
		//输入一行测试数据
		String line = "adfds";
		driver.withInput(new Text(line), new Text())//跟TemperatureMapper输入类型一致
				.withOutput(new Text("addfs"), new Text(line))//跟TemperatureMapper输出类型一致
				.runTest();
	}
}