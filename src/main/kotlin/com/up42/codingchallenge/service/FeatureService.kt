package com.up42.codingchallenge.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.up42.codingchallenge.error.EmptyDatabaseException
import com.up42.codingchallenge.error.FeatureNotFoundException
import com.up42.codingchallenge.model.FeatureCollection
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.util.UUID

private const val RESOURCE_PATH = "/static/source-data.json"

@Service
class FeatureService {
    private lateinit var features: List<FeatureCollection.Feature>
    fun getFeatures(): List<FeatureCollection.Feature> {
        if (!this::features.isInitialized)
            features = ClassPathResource(RESOURCE_PATH).file.readText()
                .let { jsonString ->
                    jacksonObjectMapper().readValue<List<FeatureCollection>>(jsonString)
                }.flatMap {
                    it.features
                }.map {
                    it.apply {
                        id = properties?.id
                        timestamp = properties?.timestamp
                        beginViewingDate = properties?.acquisition?.beginViewingDate
                        endViewingDate = properties?.acquisition?.endViewingDate
                        missionName = properties?.acquisition?.missionName
                    }
                }
                .ifEmpty {
                    throw EmptyDatabaseException()
                }
        return features
    }

    fun getFeatureById(searchId: UUID): FeatureCollection.Feature =
        getFeatures()
            .filter { it.id == searchId }
            .ifEmpty {
                throw FeatureNotFoundException(searchId)
            }.first()

    fun getFeatureQuicklookById(searchId: UUID): ByteArray {
        return Base64.decodeBase64(
            getFeatureById(searchId).properties?.quicklook!!
        )
    }
}