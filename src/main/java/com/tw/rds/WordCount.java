package com.tw.rds;

import com.tw.rds.filter.Filter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by C1mone on 1/9/15.
 */
public class WordCount {
    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        String in = "input.txt";
        String out = "out/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(CountMapper.class);
        job.setReducerClass(CountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));
        System.out.println("The output goes to: " + out);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
    public static class CountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
        public void map(LongWritable offset, Text value, Context context) throws IOException, InterruptedException {
            context.write(new Text(value.toString().trim()), new IntWritable(1));
        }
    }
    public static class CountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            Filter filter = new Filter();
            System.out.println (key.toString() +"fkkkkkkkkkk    ");
            if(!filter.isPass(key.toString()))
                return;
            int count = 0;
            for(IntWritable value : values){
                count += value.get();
            }
            context.write(key, new IntWritable(count));
        }
    }
}
