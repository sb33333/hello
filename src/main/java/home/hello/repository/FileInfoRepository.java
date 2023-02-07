package home.hello.repository;

import java.util.Optional;

import home.hello.entity.FileInfo;

public interface FileInfoRepository {
    public FileInfo insertFileInfo(FileInfo fileInfo);
    public Optional<FileInfo> selectFileInfoById(String id);
    public boolean deleteFileInfo(String id);

}
