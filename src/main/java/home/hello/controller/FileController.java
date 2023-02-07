package home.hello.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import home.hello.entity.FileInfoVO;
import home.hello.service.FileUploadService;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
	private FileUploadService fileUploadService;

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> downloadImage(@PathVariable String id) {
		FileInfoVO vo = fileUploadService.retrieveFileInfo(id);
		String filePath = vo.getFilePath();
		Path p = Paths.get(filePath);
		if(Files.exists(p, LinkOption.NOFOLLOW_LINKS)) {
			try(FileChannel fileChannel = FileChannel.open(p, StandardOpenOption.READ)) {
				ByteBuffer buffer = ByteBuffer.allocate((int)fileChannel.size());
				fileChannel.read(buffer);
				buffer.flip();
				byte[] download = new byte[(int)fileChannel.size()];
				buffer.get(download);
				HttpHeaders headers = new HttpHeaders();
				headers.setCacheControl(CacheControl.noCache().getHeaderValue());
				String ext = vo.getExt();
				MediaType type = null;
				if (ext.indexOf("png") != -1) {
					type = MediaType.IMAGE_PNG;
				} else if (ext.indexOf("jpg") != -1 || ext.indexOf("jpeg") != -1) {
					type = MediaType.IMAGE_JPEG;
				}
				headers.setContentType(type);
				return new ResponseEntity<byte[]>(download, headers, HttpStatus.OK);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<byte[]>(null, null, HttpStatus.BAD_REQUEST);
		
	} 
}
