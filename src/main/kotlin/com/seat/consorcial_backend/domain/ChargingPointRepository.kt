package com.seat.consorcial_backend.domain

interface ChargingPointRepository {
    fun findByLocation(latitude: Double, longitude: Double, radius: Double): List<ChargingPoint>
    fun findByFilters(
        latitude: Double?,
        longitude: Double?,
        radius: Double?,
        brands: List<String>?,
        status: ChargingPointStatus?,
        demand: DemandLevel?,
        amenities: List<String>?,
        connectorTypes: List<String>?,
        search: String?
    ): List<ChargingPoint>
} 