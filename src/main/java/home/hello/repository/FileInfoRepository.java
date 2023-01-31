package home.hello.repository;

import java.util.Optional;

import home.hello.entity.FileInfo;

public interface FileInfoRepository {
    public FileInfo save(FileInfo fileInfo);
    public Optional<FileInfo> findById(String id);

}
