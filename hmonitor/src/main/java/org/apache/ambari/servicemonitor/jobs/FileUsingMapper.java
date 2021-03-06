/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ambari.servicemonitor.jobs;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;

/**
 * A mapper that probably implements file operations.
 */
public class FileUsingMapper extends MapReduceBase
  implements Mapper<IntWritable, IntWritable, IntWritable, IntWritable> {

  public static final String NAME = "fileusingmapper";

  private ProbableFileOperation operation;

  @Override
  public void configure(JobConf job) {
    super.configure(job);
    operation = new ProbableFileOperation(NAME, job);
  }

  /**
   * emit: (key, value)
   *
   * @param key the input key.
   * @param value the input value.
   * @param output collects mapped keys and values.
   * @param reporter facility to report progress.
   * @throws IOException
   */
  @Override
  public void map(IntWritable key,
                  IntWritable value,
                  OutputCollector<IntWritable, IntWritable> output,
                  Reporter reporter) throws IOException {
    operation.execute(reporter);
    output.collect(key, value);
  }
}
