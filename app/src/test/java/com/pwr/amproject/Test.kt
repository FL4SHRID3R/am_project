package com.pwr.amproject

import com.pwr.amproject.logic.Coordinator
import org.junit.Test

class Test {
    @Test
    fun tesd() {
        val coordinator = Coordinator(100, 50, 4, 0)
        coordinator.roundSwitcher()
    }
}
