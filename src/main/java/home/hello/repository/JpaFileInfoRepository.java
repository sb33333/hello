package home.hello.repository;

import java.util.Optional;

import home.hello.entity.FileInfo;
import jakarta.persistence.EntityManager;

public class JpaFileInfoRepository implements FileInfoRepository {

    private EntityManager em;
	public JpaFileInfoRepository(EntityManager em) {
		this.em = em;
	}
    @Override
    public Optional<FileInfo> selectFileInfoById(String id) {
        return Optional.ofNullable(em.find(FileInfo.class, id));
    }

    @Override
    public FileInfo insertFileInfo(FileInfo fileInfo) {
        em.persist(fileInfo);
        return fileInfo;
    }
    @Override
    public boolean deleteFileInfo(String id) {
        FileInfo target = em.find(FileInfo.class, id);
        if (em.contains(target)) {
            em.remove(target);
            return false;
        }
        return false;
    }
    
    
}
