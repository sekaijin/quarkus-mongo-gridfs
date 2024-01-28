//package io.quarkiverse.mongo.gridfs.runtime.mongo.service;
//
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.Map;

//@Schema(name = "FileInfo", example = "{\n" +
//        "    \"id\": \"617047bddb392d40352bc5f9\",\n" +
//        "    \"filename\": \"code-with-quarkus-dev.jar\",\n" +
//        "    \"length\": 5439,\n" +
//        "    \"metadata\":{\n" +
//        "        \"valid\": \"true\",\n" +
//        "        \"contentType\": \"application/x-java-archive\"\n" +
//        "    },\n" +
//        "    \"uploadDate\": \"2021-10-21\"\n" +
//        "}", properties = {
//                @SchemaProperty(name = "id", description = "File resource identifier"),
//                @SchemaProperty(name = "length", description = "File size in bytes"),
//                @SchemaProperty(name = "filename", description = "File Name"),
//                @SchemaProperty(name = "uploadDate", description = "File upload date"),
//                @SchemaProperty(name = "metadata", description = "Key Value metadata colection")
//        })
//public class FileInfoDTO {
//    public String id;
//    public Long length;
//    public String filename;
//    public LocalDate uploadDate;
//    public Map<String, Object> metadata = new HashMap<>();
//}