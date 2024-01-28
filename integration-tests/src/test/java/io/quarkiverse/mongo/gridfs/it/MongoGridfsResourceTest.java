package io.quarkiverse.mongo.gridfs.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.ResourceArg;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.mongodb.MongoTestResource;
import io.smallrye.common.constraint.Assert;

@QuarkusTest
@QuarkusTestResource(value = MongoTestResource.class, initArgs = @ResourceArg(name = MongoTestResource.PORT, value = "27017"))
public class MongoGridfsResourceTest {

    @Inject
    MongoClient defaultMongoClient;

    @ConfigProperty(name = "quarkus.mongodb.database")
    String databaseName;

    @BeforeEach
    void onStart() {
        // defaultMongoClient.getDatabase(databaseName).createCollection("fs.files");
        GridFSBucket gridFSBucket = GridFSBuckets.create(defaultMongoClient.getDatabase(databaseName));
        Assert.assertNotNull(gridFSBucket);
    }

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/mongo-gridfs")
                .then()
                .statusCode(200)
                .body(is("Hello mongo-gridfs"));
    }
}
