package net.dinomite.jackson.test

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test

class GitHub498 {
    private val mapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(KotlinModule())
    val json = """{"A":1,"B":2}"""

    @Test // succeeds
    fun upperAUpperB() {
        data class Data(var A: Int, var B: Int)
        mapper.readValue<Data>(json)
    }

    @Test // succeeds
    fun upperALowerB() {
        data class Data(var A: Int, var b: Int)
        mapper.readValue<Data>(json)
    }

    @Test // succeeds
    fun lowerAUpperB() {
        data class Data(var a: Int, var B: Int)
        mapper.readValue<Data>(json)
    }

    @Test // succeeds
    fun lowerA() {
        data class Data(var a: Int)
        mapper.readValue<Data>(json)
    }

    @Test // succeeds
    fun lowerB() {
        data class Data(var b: Int)
        mapper.readValue<Data>(json)
    }

    @Test // fails
    fun upperA() {
        data class Data(var A: Int)
        mapper.readValue<Data>(json)
    }

    @Test // Fails
    fun upperB() {
        data class Data(var B: Int)
        mapper.readValue<Data>(json)
    }

    @Test // ok
    fun lowercaseJsonStringProperty() {
        val json = """{"abc":"hi"}"""

        data class Data(val abc: String)
        mapper.readValue<Data>(json)
    }

    @Test // fails
    fun uppercaseJsonStringProperty() {
        val json = """{"Abc":"hi"}"""

        data class Data(val abc: String)
        mapper.readValue<Data>(json)
    }

    @Test // ok
    fun uppercaseJsonIntProperty() {
        val json = """{"Abc":1}"""

        data class Data(var abc: Int)
        mapper.readValue<Data>(json)
    }
}
