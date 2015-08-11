package org.czs.hadoop.excel;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.LineReader;


public class ExcelInputFormat extends FileInputFormat< Text, IntWritable> {

	@Override
	protected boolean isSplitable(JobContext context, Path filename) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RecordReader<Text, IntWritable> createRecordReader(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return new ExcelRecordReader();
	}
	
	public static class ExcelRecordReader extends RecordReader<Text, IntWritable>{
		public LineReader in; //行读取器
		public Text key; // 自定义key类型
		public IntWritable value; // 自定义value类型
		public Text line; // 每行数据类型

		@Override
		public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean nextKeyValue() throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Text getCurrentKey() throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			return key;
		}

		@Override
		public IntWritable getCurrentValue() throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			return value;
		}

		@Override
		public float getProgress() throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void close() throws IOException {
			// TODO Auto-generated method stub
			if (in != null){
				in.close();
			}
			
		}
		
	}

}
