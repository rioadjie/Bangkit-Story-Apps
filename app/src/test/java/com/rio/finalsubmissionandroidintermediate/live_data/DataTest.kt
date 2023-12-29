package com.rio.finalsubmissionandroidintermediate.live_data

import com.rio.finalsubmissionandroidintermediate.live_data.response.HomeResponse
import com.rio.finalsubmissionandroidintermediate.live_data.response.ListStoryItem


object DataTest {

    fun generateDummyStoryResponse(): HomeResponse {
        val listStories = ArrayList<ListStoryItem>()
        for (i in 1..20) {
            val story = ListStoryItem(
                photoUrl = "https://img.freepik.com/free-vector/tiny-people-testing-quality-assurance-software-isolated-flat-vector-illustration-cartoon-character-fixing-bugs-hardware-device-application-test-it-service-concept_74855-10172.jpg",
                createdAt = "2012-12-12T12:12:12Z",
                name = "name $i",
                description = "description $i",
                lon = i.toDouble() * 10,
                id = "id $i",
                lat = i.toDouble() * 10
            )
            listStories.add(story)
        }
        return HomeResponse(
            error = false,
            message = "Story fetched successfully",
            listStory = listStories
        )
    }
}