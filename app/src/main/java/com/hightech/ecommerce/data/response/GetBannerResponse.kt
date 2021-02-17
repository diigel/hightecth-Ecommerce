package com.hightech.ecommerce.data.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GetBannerResponse(
    @Expose @SerializedName("code") val code: Int? = null,
    @Expose @SerializedName("data") val data: List<DataBanner>? = null,
    @Expose @SerializedName("message") val message: String? = null,
    @Expose @SerializedName("status") val status: Boolean? = null
) {
    @Parcelize
    data class DataBanner(
        val createdAt: String? = null,
        val updatedAt: String? = null,
        val expiredAt: String? = null,
        val createBy: String? = null,
        val color: String? = "#0160ee",
        val image: String? = null,
        val status: String? = null,
        val type: String? = null,
        val description: String? = null,
        val title: String? = null,
        val id: Int? = null
    ) : Parcelable
}
