package com.milwaukee.weather.places.mappers

import com.milwaukee.weather.base.model.Location
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.text.DecimalFormat

class MilwaukeeLocationMapperTest {

    @Mock
    private lateinit var formatter: DecimalFormat

    private lateinit var mapper: MilwaukeeLocationMapper

    @Before
    fun setupMapper() {
        MockitoAnnotations.initMocks(this)
        mapper = MilwaukeeLocationMapper(formatter)
    }

    @Test
    fun `double with points separators - get element - valid coordinates`() {
        val coordinate = Location(4.678638, -74.055483)
        Assert.assertEquals("4.678638,-74.055483", mapper.getFromElement(coordinate))
    }

}