package com.dajiangtai.hadoop.unittest;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import com.dajiangtai.hadoop.temperature.Temperature;
/**
 * Mapper 端的单元测试
 */
@SuppressWarnings("all")
public class TemperatureMapperTest {
	private Mapper mapper;//定义一个Mapper对象
	private MapDriver driver;//定义一个MapDriver 对象

	@Before
	public void init() {
		mapper = new Temperature.TemperatureMapper();//实例化一个Temperature中的TemperatureMapper对象
		driver = new MapDriver(mapper);//实例化MapDriver对象
	}

	@Test
	public void test() throws IOException {
		//输入一行测试数据
		String line = "1985 07 31 02   200    94 10137   220    26     1     0 -9999";
		driver.withInput(new LongWritable(), new Text(line))//跟TemperatureMapper输入类型一致
				.withOutput(new Text("weatherStationId"), new IntWritable(200))//跟TemperatureMapper输出类型一致
				.runTest();
	}
}