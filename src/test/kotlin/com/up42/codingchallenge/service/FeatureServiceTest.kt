package com.up42.codingchallenge.service

import com.up42.codingchallenge.error.FeatureNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.util.UUID

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeatureServiceTest {

    private val featureService = FeatureService()

    data class ExpectedFeature(
        val id: String,
        val timestamp: Long,
        val beginViewingDate: Long,
        val endViewingDate: Long,
        val missionName: String
    )
    @Test
    fun `should get feature list`() {

        val expectedFeatures = mapOf(
            0 to ExpectedFeature(
                id = "39c2f29e-c0f8-4a39-a98b-deed547d6aea",
                timestamp = 1554831167697,
                beginViewingDate = 1554831167697,
                endViewingDate = 1554831202043,
                missionName = "Sentinel-1B"
            ),
            1 to ExpectedFeature(
                id = "cf5dbe37-ab95-4af1-97ad-2637aec4ddf0",
                timestamp = 1556904743783,
                beginViewingDate = 1556904743783,
                endViewingDate = 1556904768781,
                missionName = "Sentinel-1B"
            ),
            2 to ExpectedFeature(
                id = "ca81d759-0b8c-4b3f-a00a-0908a3ddd655",
                timestamp = 1558155123786,
                beginViewingDate = 1558155123786,
                endViewingDate = 1558155148785,
                missionName = "Sentinel-1A"
            ),
            3 to ExpectedFeature(
                id = "0b598c52-7bf2-4df0-9d09-94229cdfbc0b",
                timestamp = 1560661222337,
                beginViewingDate = 1560661222337,
                endViewingDate = 1560661247336,
                missionName = "Sentinel-1A"
            ),
            4 to ExpectedFeature(
                id = "aeaa71d6-c549-4620-99ce-f8cae750b8d5",
                timestamp = 1560015145495,
                beginViewingDate = 1560015145495,
                endViewingDate = 1560015170493,
                missionName = "Sentinel-1B"
            ),
            5 to ExpectedFeature(
                id = "12d0b505-2c70-4420-855c-936d05c55669",
                timestamp = 1555477219508,
                beginViewingDate = 1555477219508,
                endViewingDate = 1555477244506,
                missionName = "Sentinel-1A"
            ),
            6 to ExpectedFeature(
                id = "7f23a853-76a8-4787-a2ba-fdfe93e74165",
                timestamp = 1561051946263,
                beginViewingDate = 1561051946263,
                endViewingDate = 1561051971261,
                missionName = "Sentinel-1B"
            ),
            7 to ExpectedFeature(
                id = "b3ac34e1-12e6-4dee-9e21-b717f472fcfd",
                timestamp = 1557941519219,
                beginViewingDate = 1557941519219,
                endViewingDate = 1557941544218,
                missionName = "Sentinel-1B"
            ),
            8 to ExpectedFeature(
                id = "6df9b09a-3a93-4064-bf9f-47e5809b0ffe",
                timestamp = 1557118373086,
                beginViewingDate = 1557118373086,
                endViewingDate = 1557118398085,
                missionName = "Sentinel-1A"
            ),
            9 to ExpectedFeature(
                id = "2ed68fe5-f719-48c3-aa27-b0cc155f06cb",
                timestamp = 1560015170494,
                beginViewingDate = 1560015170494,
                endViewingDate = 1560015204843,
                missionName = "Sentinel-1B"
            ),
            10 to ExpectedFeature(
                id = "f556abfe-5558-4d3a-9849-c0de4d2766fd",
                timestamp = 1561051971263,
                beginViewingDate = 1561051971263,
                endViewingDate = 1561052005606,
                missionName = "Sentinel-1B"
            ),
            11 to ExpectedFeature(
                id = "63fded50-842f-4384-ac65-e83d584beb4c",
                timestamp = 1556904768782,
                beginViewingDate = 1556904768782,
                endViewingDate = 1556904803124,
                missionName = "Sentinel-1B"
            ),
            12 to ExpectedFeature(
                id = "b0d3bf6a-ff54-49e0-a4cb-e57dcb68d3b5",
                timestamp = 1558155148786,
                beginViewingDate = 1558155148786,
                endViewingDate = 1558155173785,
                missionName = "Sentinel-1A"
            ),
            13 to ExpectedFeature(
                id = "08a190bf-8c7e-4e94-a22c-7f3be11f642c",
                timestamp = 1555044772083,
                beginViewingDate = 1555044772083,
                endViewingDate = 1555044797082,
                missionName = "Sentinel-1A"
            ),
        )

        val response = featureService.getFeatures()

        expectedFeatures.forEach { feature ->
            val actual = response.firstOrNull { it.id == UUID.fromString(feature.value.id) }
            assertNotNull(actual)
            assertEquals(feature.value.id, actual?.id.toString())
            assertEquals(feature.value.timestamp, actual?.timestamp)
            assertEquals(feature.value.beginViewingDate, actual?.beginViewingDate)
            assertEquals(feature.value.endViewingDate, actual?.endViewingDate)
            assertEquals(feature.value.missionName, actual?.missionName)
        }
    }

    @Test
    fun `should get feature by id`() {
        val expected = ExpectedFeature(
            id = "aeaa71d6-c549-4620-99ce-f8cae750b8d5",
            timestamp = 1560015145495,
            beginViewingDate = 1560015145495,
            endViewingDate = 1560015170493,
            missionName = "Sentinel-1B"
        )

        val actual = featureService.getFeatureById(UUID.fromString(expected.id))
        assertNotNull(actual)
        assertEquals(expected.id, actual.id.toString())
        assertEquals(expected.timestamp, actual.timestamp)
        assertEquals(expected.beginViewingDate, actual.beginViewingDate)
        assertEquals(expected.endViewingDate, actual.endViewingDate)
        assertEquals(expected.missionName, actual.missionName)
    }
    @Test
    fun `should not get feature by invalid id`() {
        val invalidId = UUID.fromString("aeaa71d6-c549-4620-99ce-f8cae750b8d6")
        assertThrows<FeatureNotFoundException> {
            featureService.getFeatureById(invalidId)
        }
    }
    @Test
    fun `should get feature's quicklook by id`() {
        val expectedId = "aeaa71d6-c549-4620-99ce-f8cae750b8d5"
        val actual = featureService.getFeatureQuicklookById(UUID.fromString(expectedId))
        assertNotNull(actual)
    }
}