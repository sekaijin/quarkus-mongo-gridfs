package io.quarkiverse.mongo.gridfs.runtime.mongo.impl;

import static com.mongodb.client.model.Filters.lte;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.mongodb.client.MongoClient;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

import io.vertx.core.http.HttpHeaders;

//@ApplicationScoped
public class FileDataRepository {

    @Inject
    MongoClient defaultMongoClient;

    @ConfigProperty(name = "quarkus.mongodb.database")
    String databaseName;

    GridFSBucket gridFSBucket;

    @PostConstruct
    void onStart() {
        gridFSBucket = GridFSBuckets.create(defaultMongoClient.getDatabase(databaseName));
    }

    // put your custom logic here as instance methods

    public void delete(String id) {
        gridFSBucket.delete(new ObjectId(id));
    }

    public void cleanUp() {
        Bson filter = lte("metadata.expireDate", LocalDateTime.now());
        gridFSBucket.find(filter).map(f -> f.getId()).forEach(fileId -> gridFSBucket.delete(fileId));
    }

    public InputStream openDownloadStream(String id) {
        return gridFSBucket.openDownloadStream(new ObjectId(id));
    }

    public ObjectId create(String fileName, String contentType, Map<String, String> metadata, InputStream file) {

        Document document = new Document();

        metadata.forEach((k, v) -> document.put(k, v));
        document.put(HttpHeaders.CONTENT_TYPE.toString(), contentType);
        LocalDateTime date = LocalDateTime.now();
        Duration duration = Duration.parse(metadata.getOrDefault("holdTime", "P365D"));
        document.put("expireDate", date.plus(duration));

        GridFSUploadOptions options = new GridFSUploadOptions().metadata(document);
        return gridFSBucket.uploadFromStream(fileName, file, options);

    }

    public void update(String fileName, String contentType, Map<String, String> metadata, InputStream file) {
        create(fileName, contentType, metadata, file);

    }

    public ObjectId update(FileInfo fileInfo, InputStream file) {

        var id = move(fileInfo);

        var bId = new BsonObjectId(fileInfo.id);
        gridFSBucket.uploadFromStream(bId, fileInfo.filename, file, getOptions(fileInfo));

        gridFSBucket.delete(id);

        return fileInfo.id;
    }

    public ObjectId move(FileInfo fileInfo) {
        var id = copy(fileInfo);
        gridFSBucket.delete(fileInfo.id);
        return id;
    }

    public ObjectId copy(FileInfo fileInfo) {
        var is = gridFSBucket.openDownloadStream(fileInfo.id);
        return gridFSBucket.uploadFromStream(fileInfo.filename, is, getOptions(fileInfo));
    }

    private GridFSUploadOptions getOptions(FileInfo fileInfo) {
        Document document = new Document();
        fileInfo.metadata.forEach((k, v) -> document.put(k, v));

        GridFSUploadOptions options = new GridFSUploadOptions().metadata(document);
        return options;
    }

}
