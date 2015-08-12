package org.czs.hadoop.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.LineReader;


public class ExcelInputFormat extends FileInputFormat< LongWritable, Text> {

	@Override
	protected boolean isSplitable(JobContext context, Path filename) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RecordReader<LongWritable, Text> createRecordReader(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return new ExcelRecordReader();
	}
	
	public static class ExcelRecordReader extends RecordReader<LongWritable, Text>{
		public InputStream is; //行读取器
		public String[] strArrayofLines; // 每行数据类型
		public LongWritable key; // 自定义key类型
		public Text value; // 自定义value类型

		@Override
		public void initialize(InputSplit input, TaskAttemptContext context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			FileSplit split = (FileSplit) input;
			Configuration conf = context.getConfiguration();
			Path file = split.getPath();
			FileSystem fs = file.getFileSystem(conf);
			
			FSDataInputStream fileIn = fs.open(file);
			
			is = fileIn;
			String line = new ExcelParser().parseExcelData(is);
			this.strArrayofLines = line.split("\n");
		}

		@Override
		public boolean nextKeyValue() throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			if (key == null){
				key = new LongWritable(0);
				value = new Text(strArrayofLines[0]);
			} else {
				if (key.get() < this.strArrayofLines.length - 1){
					long pos =(int) key.get();
					
					key.set(pos + 1);
					value.set(strArrayofLines[(int) (pos + 1)]);
				} else {
					return false;
				}
			}
			
			if (key == null || value == null){
				return false;
			} else {
				return true;
			}
		}

		@Override
		public LongWritable getCurrentKey() throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			return key;
		}

		@Override
		public Text getCurrentValue() throws IOException, InterruptedException {
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
			if (is != null){
				is.close();
			}
			
		}
		
	}

}
