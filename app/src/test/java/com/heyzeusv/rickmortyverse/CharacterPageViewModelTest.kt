package com.heyzeusv.rickmortyverse

import androidx.test.core.app.ApplicationProvider
import com.heyzeusv.rickmortyverse.viewmodels.CharacterPageViewModel
import org.junit.jupiter.api.Test
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

class CharacterPageViewModelTest {

    val cpViewModel = CharacterPageViewModel()

    @Test
    @DisplayName("TESTETSETS")
    fun test() {

        assertThat(true).isTrue()

    }

    @Nested
    @DisplayName("Given input 1 and 2")
    inner class OneAndTwo {

        @Nested
        @DisplayName("When we add them, Then")
        inner class Add {

            @Test
            @DisplayName("result is 3")
            fun plus() {

                assertThat(1 + 2).isEqualTo(3)
            }

            @Test
            @DisplayName("result is -1")
            fun minus() {

                assertThat(1 - 2).isEqualTo(-1)
            }
        }
    }
}