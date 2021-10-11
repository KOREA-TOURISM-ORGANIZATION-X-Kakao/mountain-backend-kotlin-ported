package org.mountain.backend.external.job.reader

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.mountain.backend.common.ResourceNameFilter
import org.mountain.backend.common.ResourcesLoader
import org.mountain.backend.common.UnzipContent
import org.mountain.backend.common.ZipInputStreamReader
import org.mountain.backend.mountain.dto.MountainAttribute
import org.mountain.backend.mountain.dto.MountainExcelFileModel
import org.mountain.backend.mountain.dto.MountainGeometry
import org.mountain.backend.mountain.dto.MountainInformationModel
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.excel.RowMapper
import org.springframework.batch.item.excel.support.rowset.RowSet

class MountainJsonFileRowMapperImpl : RowMapper<MountainExcelFileModel> {

    override fun mapRow(rs: RowSet?): MountainExcelFileModel? {
        if(rs == null || rs.currentRow == null) {
            return null
        }

        return MountainExcelFileModel(
            no = rs.getColumnValue(0),
            mountainName = rs.getColumnValue(1),
            location = rs.getColumnValue(2),
            mountainCode = rs.getColumnValue(3)
        )
    }

}

class ResourceZipItemReader(
    private val resourcesLoader: ResourcesLoader,
) {

    lateinit var loadedUnzipContent: MutableList<String>

    private fun getResourceNameFilterImpl(): ResourceNameFilter {
        return object : ResourceNameFilter {
            override fun filter(resourceName: String?): Boolean {
                return resourceName!!.contains("SPOT") || resourceName.contains(".txt")
            }
        }
    }

    fun readAllItems() {
        val zipInputStreamReader = ZipInputStreamReader(
            resourcesLoader,
            getResourceNameFilterImpl()
        )

        loadedUnzipContent = zipInputStreamReader.uncompressedAllResource().decompressedContents as MutableList<String>
    }

    fun getItem(): String {
        if(loadedUnzipContent.isNotEmpty()) {
            return loadedUnzipContent.removeAt(0)
        }

        return ""
    }

}

class MountainAttributeReader(
    private val objectMapper: ObjectMapper,
    private val resourceZipItemReader: ResourceZipItemReader
) : ItemReader<MountainInformationModel> {

    private fun makeMountainAttribute(jsonNode: JsonNode): MountainInformationModel {
        val featureFieldNode = jsonNode["features"]
        val mountainAttributes = mutableListOf<MountainAttribute>()

        featureFieldNode.forEach { feature ->
            val attribute = feature["attributes"]
            val geometry = feature["geometry"]["paths"][0]
            val paths = mutableListOf<MountainGeometry>()

            geometry.forEach {
                paths.add(MountainGeometry(it[0].asDouble(), it[1].asDouble()))
            }

            val mountainAttribute = MountainAttribute(
                no = attribute["MNTN_CODE"].asText(),
                loadLength = attribute["PMNTN_LT"].asDouble(),
                difficulty = attribute["PMNTN_DFFL"].asText(),
                paths = paths
            )
            mountainAttributes.add(mountainAttribute)
        }

        return MountainInformationModel(mountainAttributes)
    }

    override fun read(): MountainInformationModel? {
        val jsonText: String = resourceZipItemReader.getItem()

        if(jsonText.isEmpty()) {
            return null
        }
        val jsonNode = objectMapper.readTree(jsonText)

        return makeMountainAttribute(jsonNode)
    }
}

