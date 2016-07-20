package com.footprint.server.platform.uts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.footprint.common.WebResult;
import com.footprint.server.common.page.PageParam;
import com.footprint.server.common.page.PageResult;
import com.footprint.server.platform.uts.entity.JobRecord;
import com.footprint.server.platform.uts.service.JobRecordService;

@Controller
@RequestMapping("/uts")
public class JoRecordController {

	@Autowired
	private JobRecordService jobRecordService;

	@RequestMapping(value = "/getRecords")
	@ResponseBody
	public WebResult getRecords(String jobName, String jobGroup, @RequestParam(defaultValue = "1") Integer pageNo) {
		PageResult<JobRecord> page = jobRecordService.queryList(new PageParam(pageNo, 20));
		WebResult result = WebResult.newResult();
		result.put("page", page);
		return result;
	}

}