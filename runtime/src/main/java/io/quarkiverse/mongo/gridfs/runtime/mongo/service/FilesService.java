package io.quarkiverse.mongo.gridfs.runtime.mongo.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import io.quarkiverse.mongo.gridfs.runtime.mongo.impl.FileInfo;

public interface FilesService {
    public List<FileInfo> listAll();

    public FileInfo findById(String id) throws FileNotFoundException;

    public List<FileInfo> findByName(String name);

    public Long count();

    public String create(String fileName, String contentType, Map<String, String> metadata, InputStream file);

    public InputStream openDownloadStream(String id);

    public String update(String id, InputStream file) throws FileNotFoundException;

    public void delete(String id) throws FileNotFoundException;

    public void update(String id, FileInfo fileInfo) throws FileNotFoundException;

    public void cleanUp();
}
