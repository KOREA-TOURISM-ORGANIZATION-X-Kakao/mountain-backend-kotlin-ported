package org.mountain.backend.common;

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.zip.ZipInputStream

interface ResourceNameFilter {
    fun filter(resourceName: String?): Boolean
}

data class UnzipContent(
    val decompressedContents: List<String>
)

class ResourceFilenameFilter(
    private val containsChars: String
) : ResourceNameFilter {

    override fun filter(resourceName: String?): Boolean {
        if(resourceName == null) {
            return false
        }

        return resourceName.contains(containsChars)
    }

}

class ResourcesLoader(
    path: String, // path is directory!
    private val resourceNameFilter: ResourceNameFilter
) {

    private var classPathResource: ClassPathResource = ClassPathResource(path)

    private fun readFilePaths(): List<String> {
        val paths: MutableList<String> = mutableListOf()
        val inputStreamReader = InputStreamReader(classPathResource.inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        for(line in bufferedReader.lines()) {
            if(resourceNameFilter.filter(line)) {
                paths.add(classPathResource.path + "/" + line)
            }
        }

        return paths
    }

    fun read(): List<ClassPathResource> {
        val classPathResources: MutableList<ClassPathResource> = mutableListOf()
        val paths = readFilePaths()

        for(path in paths) {
            classPathResources.add(ClassPathResource(path))
        }

        return classPathResources
    }

}

class ZipInputStreamReader(
    private val resourcesLoader: ResourcesLoader,
    private val resourceNameFilter: ResourceNameFilter
) {

    private fun inputStream(classPathResource: ClassPathResource): ZipInputStream {
        val fileInputStream = FileInputStream(classPathResource.file)
        return ZipInputStream(fileInputStream, Charset.forName("Cp437"))
    }

    private fun unzip(resource: ClassPathResource): MutableList<String> {
        val filteredJsonList = mutableListOf<String>()

        inputStream(resource).use { zipInputStream ->
            generateSequence { zipInputStream.nextEntry }
                .filter { !resourceNameFilter.filter(it.name) }
                .map {
                    String(zipInputStream.readBytes())
                }.toList()
                .iterator().forEach {
                    filteredJsonList.add(it)
                }
        }

        return filteredJsonList
    }

    fun uncompressedAllResource(): UnzipContent {
        val classPathResources = resourcesLoader.read()
        val jsonList: MutableList<String> = mutableListOf()

        for (resource in classPathResources) {
            val unzipContents = unzip(resource)
            jsonList.addAll(unzipContents)
        }

        return UnzipContent(jsonList)
    }

}