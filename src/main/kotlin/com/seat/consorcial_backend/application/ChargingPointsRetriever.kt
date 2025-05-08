package com.seat.consorcial_backend.application

import com.seat.consorcial_backend.domain.ChargingPoint
import com.seat.consorcial_backend.domain.ChargingPointStatus
import com.seat.consorcial_backend.domain.DemandLevel

interface ChargingPointsRetriever {
    fun doUseCase(request: Request): List<ChargingPoint>

    data class Request(
        val latitude: Double?,
        val longitude: Double?,
        val radius: Double?,
        val brands: List<String>?,
        val status: ChargingPointStatus?,
        val demand: DemandLevel?,
        val amenities: List<String>?,
        val connectorTypes: List<String>?,
        val search: String?
    )
} 