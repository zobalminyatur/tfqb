package com.tresfellas.queenbee

import com.tresfellas.queenbee.utils.BindingAdapterUtil
import org.junit.Test

import org.junit.Assert.*
import kotlin.math.roundToInt

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun esttestetset(){
        val distance = testDistance(0.4255)

        val distance2 = testDistance(21.4255)

        println("@@@@@@@$distance")
        println("@@@@@@@$distance2")
    }

    fun testDistance (distanceKm: Double) : String{
        return if (distanceKm < 1) {
            val distanceMeter = String.format("%.1f", distanceKm * 1000).toDouble()
            return distanceMeter.roundToInt().toString() + "m"
        } else {
            val distanceSecondDecimal = String.format("%.1f", distanceKm).toDouble()
            distanceSecondDecimal.toString() + "km"
        }
    }
}