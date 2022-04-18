package com.up42.codingchallenge.web

import com.up42.codingchallenge.model.FeatureCollection
import com.up42.codingchallenge.service.FeatureService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class FeaturesController @Autowired constructor (
    private val featureService: FeatureService
) {
    @GetMapping("/features")
    fun getFeatures(): List<FeatureCollection.Feature> =
        featureService.getFeatures()

    @GetMapping("/features/{featureId}/quicklook")
    fun getFeatureById(@PathVariable featureId: UUID): ResponseEntity<ByteArray> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_PNG
        return ResponseEntity(
            featureService.getFeatureQuicklookById(featureId),
            headers,
            HttpStatus.OK
        )
    }
}
