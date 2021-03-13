package zagbor.practice.CloudStorage.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import zagbor.practice.CloudStorage.CloudStorage;

import java.io.IOException;
import java.io.InputStream;

import static zagbor.practice.CloudStorage.impl.CloudStorageAmazonS3Manager.BucketName.ZAGBORRESTAPI;

public class CloudStorageAmazonS3Manager implements CloudStorage {
    static AmazonS3 s3client;

    public static CloudStorageAmazonS3Manager manager;

    public static void init() {
        manager = new CloudStorageAmazonS3Manager();
        AWSCredentials credentials = new BasicAWSCredentials(
         "ss","sd"
        );

        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();


    }

    public static CloudStorageAmazonS3Manager getInstance() {
        return manager;
    }

    public void putFile(String name, InputStream input) {
        ObjectMetadata objectMetadata = null;
        s3client.putObject(
                ZAGBORRESTAPI.name, name, input, objectMetadata

        );
    }


    public void deleteFile(String name) {
        s3client.deleteObject(ZAGBORRESTAPI.name, name);
    }


    @Override
    public InputStream getUrl(String key) throws IOException {
        S3Object s3Object = s3client.getObject(ZAGBORRESTAPI.name, key);
        return s3Object.getObjectContent();
    }

    public enum BucketName {

        ZAGBORRESTAPI("zagborrestapi");

        String name;

        BucketName(String name) {
            this.name = name;
        }
    }

    public void changeName(String oldName, String newName) {
        s3client.copyObject(ZAGBORRESTAPI.name, oldName, ZAGBORRESTAPI.name, newName);
        s3client.deleteObject(ZAGBORRESTAPI.name, oldName);
    }
}
