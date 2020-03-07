package com.example.chucknorrisapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Joke(

    var categories: List<String> = emptyList(),
    @SerialName("created_at")
    var createdAt: String = "2020-01-05 13:42:29.296379",
    @SerialName("icon_url")
    var iconUrl: String = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    var id: String = "p3GHoau2SLGTv04XhtIeeg",
    @SerialName("updated_at")
    var updatedAt: String = "2020-01-05 13:42:29.296379",
    var url: String = "https://api.chucknorris.io/jokes/p3GHoau2SLGTv04XhtIeeg",
    var value: String = "Which came first - the chicken or the egg? Chuck Norris said it was the beer which came first, the hell with these damn animals!!!"
)