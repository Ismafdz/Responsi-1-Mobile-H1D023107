package com.responsi1mobileh1d023107.network

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("shortName") val shortName: String?,
    @SerializedName("crest") val crestUrl: String?,
    @SerializedName("venue") val venue: String?,
    @SerializedName("founded") val founded: Int,
    @SerializedName("coach") val coach: Coach?,
    @SerializedName("squad") val squad: List<Player>
) : Parcelable

@Parcelize
data class Coach(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("nationality") val nationality: String?,
    @SerializedName("dateOfBirth") val dateOfBirth: String?
) : Parcelable

@Parcelize
data class Player(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("position") val position: String?,
    @SerializedName("nationality") val nationality: String?,
    @SerializedName("dateOfBirth") val dateOfBirth: String?
) : Parcelable