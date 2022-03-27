/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.submarine.commons.runtime;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the type of Machine learning framework to work with.
 */
public enum Framework {
  TENSORFLOW(Constants.TENSORFLOW_NAME), PYTORCH(Constants.PYTORCH_NAME), MXNET(Constants.MXNET_NAME);

  private String value;

  Framework(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Framework parseByValue(String value) {
    for (Framework fw : Framework.values()) {
      if (fw.value.equalsIgnoreCase(value)) {
        return fw;
      }
    }
    return null;
  }

  public static String getValues() {
    List<String> values = Lists.newArrayList(Framework.values()).stream()
        .map(fw -> fw.value).collect(Collectors.toList());
    return String.join(",", values);
  }

  private static class Constants {
    static final String TENSORFLOW_NAME = "tensorflow";
    static final String PYTORCH_NAME = "pytorch";
    static final String MXNET_NAME = "mxnet";
  }
}