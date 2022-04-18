package com.up42.codingchallenge.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.up42.codingchallenge.model.FeatureCollection
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class FeatureService {
    private lateinit var features: List<FeatureCollection.Feature>
    fun getFeatures(): List<FeatureCollection.Feature> {
        if (!this::features.isInitialized)
            features = ClassPathResource("/static/source-data.json").file.readText()
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
                    throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No features found")
                }
        return features
    }

    fun getFeatureById(searchId: UUID): FeatureCollection.Feature =
        getFeatures()
            .filter { it.id == searchId }
            .ifEmpty {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "No features found with ID: $searchId")
            }.first()

    fun getFeatureQuicklookById(searchId: UUID): ByteArray {
        return Base64.decodeBase64(
            getFeatureById(searchId).properties?.quicklook!!
        )
    }
}