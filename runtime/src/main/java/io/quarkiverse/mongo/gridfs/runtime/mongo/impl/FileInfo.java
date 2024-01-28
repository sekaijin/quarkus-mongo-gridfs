package io.quarkiverse.mongo.gridfs.runtime.mongo.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "fs.files")
public class FileInfo {
    public ObjectId id; // used by MongoDB for the _id field
    public Long length;
    public Long chunkSize;
    public String filename;
    public LocalDate uploadDate;
    public Map<String, Object> metadata = new HashMap<>();
}
