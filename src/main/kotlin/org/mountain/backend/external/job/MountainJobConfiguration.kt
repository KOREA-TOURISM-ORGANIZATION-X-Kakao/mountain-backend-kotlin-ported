package org.mountain.backend.external.job;

import com.fasterxml.jackson.databind.ObjectMapper
import org.mountain.backend.common.ResourceFilenameFilter
import org.mountain.backend.common.ResourcesLoader
import org.mountain.backend.common.StringUtils
import org.mountain.backend.external.job.reader.MountainAttributeReader
import org.mountain.backend.external.job.reader.MountainJsonFileRowMapperImpl
import org.mountain.backend.external.job.reader.ResourceZipItemReader
import org.mountain.backend.mountain.domain.HikingTrail
import org.mountain.backend.mountain.domain.Mountain
import org.mountain.backend.mountain.domain.MountainAttribute
import org.mountain.backend.mountain.dto.MountainExcelFileModel
import org.mountain.backend.mountain.dto.MountainInformationModel
import org.mountain.backend.mountain.repository.MountainRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.excel.poi.PoiItemReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.persistence.EntityManagerFactory

@Configuration
class MountainJobConfiguration @Autowired constructor(
//    val jobBuilderFactory: JobBuilderFactory,
//    val stepBuilderFactory: StepBuilderFactory,
//    val objectMapper: ObjectMapper,
//
//    val mountainRepository: MountainRepository
) {

//    @StepScope
//    @Bean
//    fun excelReader(): PoiItemReader<MountainExcelFileModel> {
//        val reader: PoiItemReader<MountainExcelFileModel> = PoiItemReader<MountainExcelFileModel>()
//
//        reader.setResource(ClassPathResource("/MNT_CODE.xlsx"))
//        reader.setLinesToSkip(1)
//        reader.setRowMapper(MountainJsonFileRowMapperImpl())
//
//        return reader
//    }
//
//    @Bean
//    fun mountainAttributeModelSaveStep(): Step {
//        return stepBuilderFactory.get("mountain_attribute_save_step")
//            .chunk<MountainInformationModel, Mountain>(10000)
//            .reader(zipReader())
//            .processor(mountainAttributeProcessor())
//            .writer(mountainAttributeWriter())
//            .build()
//    }
//
//    @StepScope
//    @Bean
//    fun zipReader(): ItemReader<out MountainInformationModel> {
//        // 읽어올 폴더의 _geojson zip file
//        val classPathResource = ResourcesLoader("/mountain", ResourceFilenameFilter("_geojson.zip"))
//        // zip file reader
//        val resourceZipItemReader = ResourceZipItemReader(classPathResource)
//        // resources/mountain 폴더의 모든 zip 아이템을 읽어오고 메모리에 올려놓습니다.
//        resourceZipItemReader.readAllItems()
//
//        return MountainAttributeReader(objectMapper, resourceZipItemReader)
//    }
//
//    @Bean
//    fun mountainAttributeWriter(): ItemWriter<Mountain> {
//        return ItemWriter { list ->
//            list.forEach {
//                if(it == null) println(it)
//            }
//        }
//    }
//
//    @Bean
//    fun mountainAttributeProcessor(): ItemProcessor<MountainInformationModel, Mountain> {
//        return ItemProcessor<MountainInformationModel, Mountain> { informationModel ->
//            val mountainId = informationModel.attributes[0].no
//            val mountain = mountainRepository.findById(mountainId).orElse(Mountain(mountainId, mountainId, ""))
//            val attributes = mutableSetOf<MountainAttribute>()
//
//            informationModel.attributes.forEach { attribute ->
//                val mountainAttribute = MountainAttribute(mountain, attribute.loadLength, attribute.difficulty)
//                mountainAttribute.hikingTrails = attribute.paths.map { HikingTrail(mountainAttribute, it.latitude, it.longitude) }.toMutableSet()
//
//                attributes.add(mountainAttribute)
//            }
//            mountain.mountainAttributes = attributes
//
//            mountain
//        }
//    }
//
//    @Bean
//    fun defaultMountainInformationSaveJob(): Job {
//        return jobBuilderFactory.get("default_mountain_information_jobs")
//            .incrementer(RunIdIncrementer())
//            .start(defaultMountainInformationSaveStep())
//            .start(mountainAttributeModelSaveStep())
//            .build()
//    }
//
//    @Bean
//    fun defaultMountainInformationSaveStep(): Step {
//        return stepBuilderFactory.get("default_mountain_information_step")
//            .chunk<MountainExcelFileModel, Mountain>(100)
//            .reader(excelReader())
//            .processor(defaultMountainInformationProcessor())
//            .writer(defaultMountainInformationWriter())
//            .build()
//    }
//
//    @Bean
//    fun defaultMountainInformationWriter(): ItemWriter<Mountain> {
//        return ItemWriter {
//            list -> mountainRepository.saveAll(list)
//        }
//    }
//
//    @Bean
//    fun defaultMountainInformationProcessor(): ItemProcessor<MountainExcelFileModel, Mountain> {
//        return ItemProcessor {
//                item -> Mountain(item.mountainCode, item.mountainName, StringUtils.trimAtStringLast(item.location))
//        }
//    }

}
