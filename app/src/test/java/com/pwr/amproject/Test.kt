package com.pwr.amproject

import com.pwr.amproject.logic.Coordinator
import org.junit.Test

class Test {
    @Test
    fun tesd() {
        var coordinator = Coordinator(100, 50, 4, 0)
        coordinator.roundSwitcher()

        coordinator = Coordinator(100, 50, 0, 4)
        coordinator.roundSwitcher()
        
        coordinator = Coordinator(100, 50, 2, 2)
        coordinator.roundSwitcher()
    }
}
