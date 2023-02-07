package home.hello.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileInfoVO {
	private String id;
	private String ext;
	private String filePath;
	private String realFilename;
	private LocalDate savedDate;

	public static FileInfoVO createFileInfoVO(FileInfo entity) {
		FileInfoVO vo = new FileInfoVO();
		vo.setId(entity.getId());
		vo.setExt(entity.getExt());
		vo.setFilePath(entity.getFilePath());
		vo.setRealFilename(entity.getRealFilename());
		vo.setSavedDate(entity.getSavedDate());
		return vo;
	}

	public FileInfo createFileInfoEntity() {
		FileInfo entity = new FileInfo();
		entity.setId(this.id);
		entity.setExt(this.ext);
		entity.setFilePath(this.filePath);
		entity.setRealFilename(this.realFilename);
		entity.setSavedDate(this.savedDate);
		return entity;
	}
}
