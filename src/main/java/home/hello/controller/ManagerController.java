package home.hello.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import home.hello.entity.FileInfoVO;
import home.hello.entity.TodoId;
import home.hello.entity.TodoStatus;
import home.hello.entity.TodoVO;
import home.hello.service.FileUploadService;
import home.hello.service.TodoServiceImpl;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private TodoServiceImpl todoService;
	@Autowired
	private FileUploadService fileUploadService;

	@GetMapping("/todo")
	public ModelAndView managerHome(ModelMap model) {
		model.addAttribute("todo", todoService.todoList(null));
		return new ModelAndView("/manager/home", model);
	}

	@PostMapping("/todo")
	public @ResponseBody TodoVO newTodo(@RequestBody TodoVO vo) {
		return todoService.newTodo(vo);
	}

	@PostMapping("/todo/{date}/{seq}")
	public @ResponseBody TodoVO confirmTodo(@PathVariable LocalDate date, @PathVariable int seq) {
		TodoId id = new TodoId();
		id.setDate(date);
		id.setSequence(seq);
		TodoVO result = todoService.updateTodoStatus(id, TodoStatus.COMFIRMED);
		return result;
	}


	@DeleteMapping("todo/{date}/{seq}")
	public @ResponseBody String deleteTodo(@PathVariable LocalDate date, @PathVariable int seq) {
		TodoId id = new TodoId();
		id.setDate(date);
		id.setSequence(seq);
		List<FileInfoVO> attachedFiles = todoService.retrieveFileInfoList(id);
		boolean result = todoService.deleteTodo(id);
		StringBuffer sb = new StringBuffer();
		if (result) {
			sb.append("TODO[").append(date.toString()).append(' ').append(seq).append("] is deleted");
			for (FileInfoVO fileInfoVO : attachedFiles) {
				fileUploadService.removeFile(fileInfoVO);
			}
			return sb.toString();
		}
		sb.append("failed");
		return sb.toString();
	}

	@PatchMapping("/todo/{date}/{seq}")
	public @ResponseBody String updateTodo(@PathVariable LocalDate date, @PathVariable int seq, @RequestBody TodoVO vo) {
		TodoId id = new TodoId();
		id.setDate(date);
		id.setSequence(seq);
		boolean result = todoService.updateTodoContent(id, vo);
		return Boolean.valueOf(result).toString();
	}

	@PatchMapping("/todo/{date}/{seq}/{status}") 
	public @ResponseBody TodoVO changeTodoStatus (@PathVariable LocalDate date, @PathVariable int seq, @PathVariable TodoStatus status) {
		TodoId id = new TodoId();
		id.setDate(date);
		id.setSequence(seq);
		return todoService.updateTodoStatus(id, status);
	}
}
