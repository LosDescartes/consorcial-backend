package com.seat.consorcial_backend.infrastructure.web

import com.seat.consorcial_backend.domain.ChargingPoint
import com.seat.consorcial_backend.domain.ChargingPointStatus
import com.seat.consorcial_backend.domain.DemandLevel

data class ChargingPointResponse(
    val id: String,
    val name: String,
    val operator: String,
    val brand: String,
    val status: ChargingPointStatus,
    val latitude: Double,
    val longitude: Double,
    val demand: DemandLevel
)

fun ChargingPoint.toResponse() = ChargingPointResponse(
    id = id,
    name = name,
    operator = operator,
    brand = brand,
    status = status,
    latitude = latitude,
    longitude = longitude,
    demand = demand
) 