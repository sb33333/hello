package home.hello.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import home.hello.entity.FileInfoVO;
import home.hello.entity.TodoId;
import home.hello.entity.TodoStatus;
import home.hello.entity.TodoVO;
import home.hello.service.FileUploadService;
import home.hello.service.TodoServiceImpl;

@Controller
@RequestMapping("/todo")
public class TodoController {
	
	@Autowired
	private TodoServiceImpl todoService;

	@Autowired
	private FileUploadService fileUploadService;

	@GetMapping
	public ModelAndView home(ModelMap model) {
		model.addAttribute("todo", todoService.todoList(null));
		model.addAttribute("today", LocalDate.now());
		return new ModelAndView("home", model);
	}
	@GetMapping("/{date}")
	public ModelAndView todoListFilterByDate(ModelMap model, @PathVariable LocalDate date) {
		model.addAttribute("todo", todoService.todoList(date));
		model.addAttribute("today", date);
		return new ModelAndView("home", model);
	}

	@GetMapping("/{date}/{seq}")
	public @ResponseBody TodoVO getTodo(@PathVariable LocalDate date, @PathVariable int seq) {
		TodoId id = new TodoId();
		id.setDate(date);
		id.setSequence(seq);
		return todoService.findTodo(id);
	}

	@PostMapping("/{date}/{seq}/file")
	public @ResponseBody TodoVO uploadFileToTodo
	(
		@PathVariable LocalDate date
		, @PathVariable int seq
		, @RequestParam MultipartFile[] uploadFiles
	) {
		TodoId id = new TodoId();
		id.setDate(date);
		id.setSequence(seq);
		TodoVO todo = todoService.findTodo(id);
		
		if (uploadFiles != null) {
			for (MultipartFile file : uploadFiles) {
				FileInfoVO fileInfo = fileUploadService.saveFile(file);
				todo = todoService.addFileToTodo(todo, fileInfo);
			}
		}
		return todo;
	}

	@PatchMapping("/{date}/{seq}")
	public @ResponseBody String completeTodo(@PathVariable LocalDate date, @PathVariable int seq) {
		TodoId id = new TodoId();
		id.setDate(date);
		id.setSequence(seq);
		try {
			todoService.updateTodoStatus(id, TodoStatus.COMPLETED);
			return "true";
		} catch (IllegalArgumentException e) {
			return "false";
		}
		
	}
	

	

	
}
