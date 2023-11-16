package com.example.berandaberanda.activitity.alokasi


data class DataItem(
    val title : String,
    val desc : String?,
    val poster : Int?,
    val logo : Int?
)
sealed class DataItemTypes {
    data class ResponseOne(
        val title: String,
        val desc: String?,
        val poster: Int?,
    ) : DataItemTypes()

    data class ResponseTwo(
        val title : String,
        val desc : String?,
        val poster : Int?,
        ): DataItemTypes()
}