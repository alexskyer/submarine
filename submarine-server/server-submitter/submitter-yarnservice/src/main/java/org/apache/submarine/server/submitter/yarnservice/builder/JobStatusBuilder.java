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

package org.apache.submarine.server.submitter.yarnservice.builder;

import org.apache.hadoop.yarn.service.api.records.Component;
import org.apache.hadoop.yarn.service.api.records.Service;
import org.apache.hadoop.yarn.service.api.records.ServiceState;
import org.apache.submarine.commons.runtime.api.JobComponentStatus;
import org.apache.submarine.commons.runtime.api.JobState;
import org.apache.submarine.commons.runtime.api.JobStatus;

import java.util.ArrayList;
import java.util.List;

public class JobStatusBuilder {
  public static JobStatus fromServiceSpec(Service serviceSpec) {
    JobStatus status = new JobStatus();
    status.setState(fromServiceState(serviceSpec.getState()));

    // If it is a final state, return.
    if (JobState.isFinal(status.getState())) {
      return status;
    }

    List<JobComponentStatus> componentStatusList = new ArrayList<>();

    for (Component component : serviceSpec.getComponents()) {
      componentStatusList.add(
          JobComponentStatusBuilder.fromServiceComponent(component));
    }
    status.setComponentStatus(componentStatusList);

    // TODO(keqiu) handle tensorboard differently.
    // status.setTensorboardLink(getTensorboardLink(serviceSpec, clientContext));

    status.setJobName(serviceSpec.getName());

    return status;
  }

  private static JobState fromServiceState(ServiceState serviceState) {
    switch (serviceState) {
      case STOPPED:
        // TODO(keqiu), once YARN-8488 gets committed, we need to update this.
        return JobState.SUCCEEDED;
      case FAILED:
        return JobState.FAILED;
    }

    return JobState.RUNNING;
  }
}
