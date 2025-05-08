package com.seat.consorcial_backend.application

import com.seat.consorcial_backend.domain.ChargingPoint

interface ChargingPointsRetriever {
    fun doUseCase(request: Request): List<ChargingPoint>

    data class Request(
        val latitude: Double?,
        val longitude: Double?,
        val radius: Double,
        val brands: List<String>?,
        val status: String,
        val demand: String,
        val amenities: List<String>?,
        val connectorTypes: List<String>?,
        val search: String?
    )
} 