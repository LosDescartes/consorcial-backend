package com.seat.consorcial_backend.application

import com.seat.consorcial_backend.domain.ChargingPoint
import com.seat.consorcial_backend.domain.ChargingPointRepository
import com.seat.consorcial_backend.domain.ChargingPointStatus
import com.seat.consorcial_backend.domain.DemandLevel
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ChargingPointsRetrieverTest {

    private lateinit var repository: ChargingPointRepository
    private lateinit var useCase: ChargingPointsRetriever

    @BeforeEach
    fun setup() {
        repository = Mockito.mock(ChargingPointRepository::class.java)
        useCase = ChargingPointsRetrieverImpl(repository)
    }

    @Test
    fun `should return charging points when no filters are provided`() {
        // Given
        val chargingPoints = listOf(
            ChargingPoint(
                id = "1",
                name = "Test Charger 1",
                operator = "Test Operator",
                brand = "Test Brand",
                status = ChargingPointStatus.AVAILABLE,
                latitude = 41.3851,
                longitude = 2.1734,
                demand = DemandLevel.LOW
            )
        )
        
        whenever(repository.findByFilters(
            latitude = null,
            longitude = null,
            radius = null,
            brands = null,
            status = null,
            demand = null,
            amenities = null,
            connectorTypes = null,
            search = null
        )).thenReturn(chargingPoints)

        // When
        val request = ChargingPointsRetriever.Request(
            latitude = null,
            longitude = null,
            radius = null,
            brands = null,
            status = null,
            demand = null,
            amenities = null,
            connectorTypes = null,
            search = null
        )
        val result = useCase.doUseCase(request)

        // Then
        assert(result == chargingPoints)
        verify(repository).findByFilters(
            latitude = null,
            longitude = null,
            radius = null,
            brands = null,
            status = null,
            demand = null,
            amenities = null,
            connectorTypes = null,
            search = null
        )
    }

    @Test
    fun `should return filtered charging points when filters are provided`() {
        // Given
        val chargingPoints = listOf(
            ChargingPoint(
                id = "1",
                name = "Test Charger 1",
                operator = "Test Operator",
                brand = "Test Brand",
                status = ChargingPointStatus.AVAILABLE,
                latitude = 41.3851,
                longitude = 2.1734,
                demand = DemandLevel.LOW
            )
        )
        
        whenever(repository.findByFilters(
            latitude = 41.3851,
            longitude = 2.1734,
            radius = 50.0,
            brands = null,
            status = ChargingPointStatus.AVAILABLE,
            demand = DemandLevel.LOW,
            amenities = null,
            connectorTypes = null,
            search = null
        )).thenReturn(chargingPoints)

        // When
        val request = ChargingPointsRetriever.Request(
            latitude = 41.3851,
            longitude = 2.1734,
            radius = 50.0,
            brands = null,
            status = ChargingPointStatus.AVAILABLE,
            demand = DemandLevel.LOW,
            amenities = null,
            connectorTypes = null,
            search = null
        )
        val result = useCase.doUseCase(request)

        // Then
        assert(result == chargingPoints)
        verify(repository).findByFilters(
            latitude = 41.3851,
            longitude = 2.1734,
            radius = 50.0,
            brands = null,
            status = ChargingPointStatus.AVAILABLE,
            demand = DemandLevel.LOW,
            amenities = null,
            connectorTypes = null,
            search = null
        )
    }
} 