package io.quarkiverse.mongo.gridfs.deployment;

import io.quarkiverse.mongo.gridfs.runtime.mongo.impl.FileDataRepository;
import io.quarkiverse.mongo.gridfs.runtime.mongo.impl.FileInfoRepository;
import io.quarkiverse.mongo.gridfs.runtime.mongo.impl.FilesServiceImpl;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class MongoGridfsProcessor {

    private static final String FEATURE = "mongo-gridfs";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    //    @BuildStep
    //    AdditionalBeanBuildItem registerFileInfoMapperBean() {
    //        return AdditionalBeanBuildItem.unremovableOf(FileInfoMapper.class);
    //    }

    @BuildStep
    AdditionalBeanBuildItem registerFileInfoRepositoryBean() {
        return AdditionalBeanBuildItem.unremovableOf(FileInfoRepository.class);
    }

    @BuildStep
    AdditionalBeanBuildItem registerFileDataRepositoryBean() {
        return AdditionalBeanBuildItem.unremovableOf(FileDataRepository.class);
    }

    @BuildStep
    AdditionalBeanBuildItem registerFilesServiceBean() {
        return AdditionalBeanBuildItem.unremovableOf(FilesServiceImpl.class);
    }

}
