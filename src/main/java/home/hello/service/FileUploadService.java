package home.hello.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import home.hello.entity.FileInfo;
import home.hello.entity.FileInfoVO;
import home.hello.repository.FileInfoRepository;

@Transactional
public class FileUploadService {
	
	@Value("${hello.file.upload}")
	private String fileDir;
	@Value("{hello.file.removed}")
	private String removedDir;

	private FileInfoRepository fileInfoRepository;
	public FileUploadService(FileInfoRepository fileInfoRepository) {
		this.fileInfoRepository = fileInfoRepository;
	}

	public boolean removeFile(FileInfoVO fileInfo) {
		String savedPath = fileInfo.getFilePath();
		
		Path p = Paths.get(savedPath);
		if (Files.exists(p, LinkOption.NOFOLLOW_LINKS)) {
			String removedPath = removedDir + fileInfo.getId() + '.' + fileInfo.getExt();
			Path pr = Paths.get(removedPath);
			try {
				Files.move(p, pr);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	public FileInfoVO saveFile(MultipartFile file) {
		String originalFilename = null;
		if (file.isEmpty() || file.getOriginalFilename() == null) {
			throw new NullPointerException("file is null");
		}
		originalFilename = file.getOriginalFilename();
		StringBuffer sb = new StringBuffer();
		String ext = FilenameUtils.getExtension(originalFilename);
		LocalDateTime now = LocalDateTime.now();
		sb.append(now.toEpochSecond(ZoneOffset.UTC)).append('_').append(now.getNano());
		String id = sb.toString();	
		sb.append('.').append(ext);
		String savePath = sb.toString();
		Path p = Paths.get(fileDir + savePath);
		try {
			file.transferTo(p);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return new FileInfoVO();
		}
		FileInfo fileInfo = new FileInfo();
		fileInfo.setId(id);
		fileInfo.setExt(ext);
		fileInfo.setFilePath(fileDir + savePath);
		fileInfo.setRealFilename(originalFilename);
		fileInfo.setSavedDate(LocalDate.now());
		fileInfoRepository.insertFileInfo(fileInfo);
		return FileInfoVO.createFileInfoVO(fileInfo);
	}
	public FileInfoVO retrieveFileInfo(String id) {
		return fileInfoRepository.selectFileInfoById(id).map(FileInfoVO::createFileInfoVO).orElseGet(FileInfoVO::new);
	}
}
