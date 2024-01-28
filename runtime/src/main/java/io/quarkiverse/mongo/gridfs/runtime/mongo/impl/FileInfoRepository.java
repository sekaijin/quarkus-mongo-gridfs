package io.quarkiverse.mongo.gridfs.runtime.mongo.impl;

import java.util.List;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheMongoRepository;

//@ApplicationScoped
public class FileInfoRepository implements PanacheMongoRepository<FileInfo> {

    // put your custom logic here as instance methods

    public List<? extends FileInfo> findByName(String name) {
        return find("filename", name).list();
    }

    public FileInfo findById(String id) {
        return FileInfo.class.cast(findById(new ObjectId(id)));
    }

}
