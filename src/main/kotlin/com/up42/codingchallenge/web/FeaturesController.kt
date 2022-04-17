package com.up42.codingchallenge.web

import com.up42.codingchallenge.model.FeatureCollection
import com.up42.codingchallenge.service.FeatureService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FeaturesController @Autowired constructor (
    private val featureService: FeatureService
) {
    @GetMapping("/features")
    fun getFeatures(): List<FeatureCollection.Feature> =
        featureService.getFeatures()
}
