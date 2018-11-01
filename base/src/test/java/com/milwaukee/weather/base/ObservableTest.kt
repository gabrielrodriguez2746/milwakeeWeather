package com.milwaukee.weather.base

import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class ObservableTest {

    @Test(expected = Throwable::class)
    fun `Observable from sequence - throw error use on error return item - receive expected list`() {
        val expectedList = listOf("1", "3", "4", "5")
        Observable.just(1, 2, 3, 4, 5)
            .flatMap {
                Observable.just((if (it == 2) throw Throwable() else it.toString()))
                    .onErrorReturnItem("-1")
            }
            .filter { it != "-1" }
            .toList()
            .test()
            .assertValue { it == expectedList }
            .assertValue { it.size == 4 }
    }

}