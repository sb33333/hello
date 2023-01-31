package home.hello.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import home.hello.entity.FileInfo;
import home.hello.repository.FileInfoRepository;

@Transactional
public class FileUploadService {
	
	@Value("${hello.file.upload}")
	private String fileDir;

	private FileInfoRepository fileInfoRepository;
	public FileUploadService(FileInfoRepository fileInfoRepository) {
		this.fileInfoRepository = fileInfoRepository;
	}

	public FileInfo saveFile(MultipartFile file) {
		String originalFilename = null;
		if (file.isEmpty() || file.getOriginalFilename() == null) {
			throw new NullPointerException("file is null");
		}
		
		originalFilename = file.getOriginalFilename();
		String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
		String savedPath = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + ext;
		Path p = Paths.get(fileDir + savedPath);
		try {
			file.transferTo(p);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return new FileInfo();
		}
		FileInfo fileInfo = new FileInfo();
		fileInfo.setId(savedPath);
		fileInfo.setFilePath(fileDir + savedPath);
		fileInfo.setRealFilename(originalFilename);
		fileInfoRepository.save(fileInfo);
		return fileInfo;
	}
}
