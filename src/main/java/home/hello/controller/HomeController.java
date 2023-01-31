package home.hello.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import home.hello.entity.FileInfo;
import home.hello.entity.ResponseStatus;
import home.hello.service.FileUploadService;
import home.hello.service.TodoService;

@Controller
public class HomeController {
	private static final String FILE_PATH = "/home/namu/upload/";
	
	@Autowired
	private TodoService dailyJobService;
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("/day")
	public ModelAndView home(ModelMap model) {
		model.addAttribute("list", dailyJobService.findDailyJobs());
		model.addAttribute("today", LocalDate.now());
		return new ModelAndView("home", model);
	}
	@PostMapping("/day/upload")
	public ResponseEntity<ResponseStatus> uploadFile(@RequestParam MultipartFile[] files) {
		ResponseStatus responseStatus = new ResponseStatus();
		// if file is not attached
		if (files == null) {
			responseStatus.setStatus("error");
			responseStatus.setMessage("file is not attached");
			return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatusCode.valueOf(400));
		}

		for(MultipartFile file : files) {
			FileInfo fileInfo = fileUploadService.saveFile(file);
			if (fileInfo.getId() == null) {
				responseStatus.setStatus("error");
				responseStatus.setMessage("error caused while saving");
				return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatusCode.valueOf(400));
				// TODO 중간에 실패하면 나머지도 모두 삭제?
			}
		}
		responseStatus.setStatus("success");
		return new ResponseEntity<ResponseStatus>(responseStatus, HttpStatusCode.valueOf(200));
	}


	@GetMapping(value="/manager/day")
	public ModelAndView dailyJobForm() {
		return null;
	}

	@PostMapping(value="/manager/day")
	public ModelAndView saveNewDailyJob() {
		return null;
	}
	
	
}
