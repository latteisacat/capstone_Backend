package com.example.capstone_backend.domain.fileserver;

import com.example.capstone_backend.domain.fileserver.exception.AmazonS3SaveError;
import com.example.capstone_backend.domain.fileserver.exception.InvalidFileTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class S3FileRepository {

    private final S3Client s3Client;
    private final String bucket;

    public S3FileRepository(@Value("${cloud.aws.s3.bucket}") final String bucket,
                            @Autowired final S3Client s3Client) {
        this.bucket = bucket;
        this.s3Client = s3Client;
    }

    public String save(final MultipartFile file) {
        final String fileName = UUID.randomUUID() + "." +getFileExtension(file);
        saveToS3(file, fileName);

        return s3Client
                .utilities()
                .getUrl(builder -> builder
                        .bucket(bucket)
                        .key(fileName))
                .toString();
    }

    public void overwrite(final MultipartFile file, final String fileName) {
        saveToS3(file, fileName);
    }

    public void deleteAll(final List<String> savedFileUrls) {
        try {
            s3Client.deleteObjects(deleteObjectRequestBuilder -> deleteObjectRequestBuilder
                    .bucket(bucket)
                    .delete(objectIdentifiersBuilder -> objectIdentifiersBuilder
                            .objects(savedFileUrls.stream()
                                    .map(savedFileUrl -> ObjectIdentifier.builder()
                                            .key(savedFileUrl)
                                            .build())
                                    .toArray(ObjectIdentifier[]::new))
                            .build())
                    .build()
            );
        } catch (final SdkException e) {
            // TODO: DB에 로그를 저장하고, 주기적으로 Garbage Objects를 삭제하는 로직을 추가해야합니다.
            throw e;
        }
    }


    private void saveToS3(final MultipartFile file, final String fileName) {
        try {
            s3Client.putObject(builder -> builder
                            .bucket(bucket)
                            .key(fileName)
                            .acl(ObjectCannedACL.BUCKET_OWNER_FULL_CONTROL)
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

        } catch (final SdkException ex) {
            throw new AmazonS3SaveError(ex.getMessage());
        } catch (final IOException ex) {
            throw new InvalidFileTypeException();
        }
    }

    private String getFileExtension(final MultipartFile file) {
        final String fileName = file.getOriginalFilename();
        return Objects.requireNonNull(fileName).substring(fileName.lastIndexOf(".")).substring(1).toLowerCase();
    }
}
