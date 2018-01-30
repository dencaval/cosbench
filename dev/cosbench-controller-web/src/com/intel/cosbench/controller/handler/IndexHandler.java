/** 
 
Copyright 2013 Intel Corporation, All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/ 

package com.intel.cosbench.controller.handler;

import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.*;

import com.intel.cosbench.model.*;
import com.intel.cosbench.service.ControllerService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

public class IndexHandler extends AbstractClientHandler {

    private ControllerService controller;

    public void setController(ControllerService controller) {
        this.controller = controller;
    }
    
    @Override
    protected String process(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
    	DriverInfo[] drivers = controller.getControllerInfo().getDriverInfos();
    	JsonArrayBuilder json_drivers = Json.createArrayBuilder();
    	for (DriverInfo driver : drivers){
    		JsonObjectBuilder json_driver = Json.createObjectBuilder();
    		json_driver.add("id", driver.getName());
    		json_driver.add("url", driver.getUrl());  		
    		json_drivers.add(json_driver.build());
    	}
    	
    	WorkloadInfo[] workloads = controller.getActiveWorkloads();
    	JsonArrayBuilder json_workloads = Json.createArrayBuilder();
    	for (WorkloadInfo workload : workloads){
    		JsonObjectBuilder json_workload = Json.createObjectBuilder();
    		json_workload.add("id", workload.getId());
    		json_workload.add("status", workload.getState().toString());
    		
    		StageInfo[] stages = workload.getStageInfos();
        	JsonArrayBuilder json_stages = Json.createArrayBuilder();
        	for (StageInfo stage : stages){
        		JsonObjectBuilder json_state = Json.createObjectBuilder();
        		json_state.add("id", stage.getId());
        		json_state.add("name", stage.getStage().getName());
        		json_state.add("status", stage.getState().name().toString());
        		
        		json_stages.add(json_state.build());    		
        	}
        	
        	json_workload.add("stages", json_stages.build());        	
        	json_workloads.add(json_workload.build());    		
    	}
    	
    	JsonObjectBuilder data = Json.createObjectBuilder();
    	data.add("drivers", json_drivers.build());
    	data.add("workloads", json_workloads.build());
    	
    	StringWriter stringWriter = new StringWriter();
    	JsonWriter writer = Json.createWriter(stringWriter);
        writer.writeObject(data.build());
        writer.close();
        return stringWriter.toString();
    }
}