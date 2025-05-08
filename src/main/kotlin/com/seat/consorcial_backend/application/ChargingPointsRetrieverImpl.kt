package com.seat.consorcial_backend.application

import com.seat.consorcial_backend.domain.ChargingPoint
import com.seat.consorcial_backend.domain.ChargingPointRepository
import org.springframework.stereotype.Service

@Service
class ChargingPointsRetrieverImpl(
    private val repository: ChargingPointRepository
) : ChargingPointsRetriever {

    override fun doUseCase(request: ChargingPointsRetriever.Request): List<ChargingPoint> {
        return repository.findByFilters(
            latitude = request.latitude,
            longitude = request.longitude,
            radius = request.radius,
            brands = request.brands,
            status = request.status,
            demand = request.demand,
            amenities = request.amenities,
            connectorTypes = request.connectorTypes,
            search = request.search
        )
    }
} 