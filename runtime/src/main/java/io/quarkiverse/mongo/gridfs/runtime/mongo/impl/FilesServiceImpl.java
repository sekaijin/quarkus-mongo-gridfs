package io.quarkiverse.mongo.gridfs.runtime.mongo.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.inject.Inject;

import io.quarkiverse.mongo.gridfs.runtime.mongo.service.FilesService;

//@ApplicationScoped
public class FilesServiceImpl implements FilesService {
    @Inject
    FileInfoRepository fileInfoRepository;

    @Inject
    FileDataRepository fileDataRepository;

    //    @Inject
    //    FileInfoMapper mapper;

    public List<FileInfo> listAll() {
        return fileInfoRepository.listAll().stream()
                //.map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public String create(String fileName, String contentType, Map<String, String> metadata, InputStream file) {
        return fileDataRepository.create(fileName, contentType, metadata, file).toString();
    }

    public FileInfo findById(String id) throws FileNotFoundException {

        FileInfo fileInfo = fileInfoRepository.findById(id);
        if (Objects.isNull(fileInfo)) {
            throw new FileNotFoundException(id);
        }

        return fileInfo; //mapper.toDto(fileInfo);
    }

    public InputStream openDownloadStream(String id) {
        return fileDataRepository.openDownloadStream(id);
    }

    public String update(String id, InputStream file) throws FileNotFoundException {

        FileInfo fileInfo = fileInfoRepository.findById(id);
        if (Objects.isNull(fileInfo)) {
            throw new FileNotFoundException(id);
        }
        return fileDataRepository.update(fileInfo, file).toString();
    }

    public List<FileInfo> findByName(String name) {
        return fileInfoRepository.findByName(name).stream()
                //.map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(String id) throws FileNotFoundException {

        FileInfo fileInfo = fileInfoRepository.findById(id);
        if (Objects.isNull(fileInfo)) {
            throw new FileNotFoundException(id);
        }
        fileDataRepository.delete(id);
    }

    public Long count() {
        return fileInfoRepository.count();
    }

    @Override
    public void update(String id, FileInfo fileInfo) throws FileNotFoundException {

        FileInfo oldFileInfo = fileInfoRepository.findById(id);
        if (Objects.isNull(oldFileInfo)) {
            throw new FileNotFoundException(id);
        }
        oldFileInfo.filename = fileInfo.filename;
        oldFileInfo.metadata = fileInfo.metadata;
        fileInfoRepository.update(oldFileInfo);
    }

    public void cleanUp() {
        fileDataRepository.cleanUp();
    }

}
