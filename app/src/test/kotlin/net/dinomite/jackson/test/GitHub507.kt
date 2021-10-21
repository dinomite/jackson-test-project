package net.dinomite.jackson.test

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.PropertyName
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier
import com.fasterxml.jackson.databind.util.NameTransformer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

data class DataClassWithVal(val foo: String)

data class DataClassWithVar(var foo: String)

class ClassWithVal(val foo: String? = null)

class ClassWithVar(var foo: String? = null)

class ClassWithLateinitVar {
    lateinit var foo: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClassWithLateinitVar

        if (foo != other.foo) return false

        return true
    }

    override fun hashCode(): Int {
        return foo.hashCode()
    }
}

class GitHub507 {
    private val objectMapper = jacksonObjectMapper().apply {
        registerModule(
            SimpleModule()
                .setSerializerModifier(
                    object : BeanSerializerModifier() {
                        override fun changeProperties(
                            config: SerializationConfig,
                            beanDescription: BeanDescription?,
                            beanProperties: MutableList<BeanPropertyWriter>
                        ): MutableList<BeanPropertyWriter> {
                            return beanProperties.map { beanProperty ->
                                beanProperty.rename(
                                    object : NameTransformer() {
                                        override fun transform(name: String) = "bar"

                                        override fun reverse(transformed: String) = beanProperty.name
                                    })
                            }.toMutableList()
                        }
                    }
                )
                .setDeserializerModifier(
                    object : BeanDeserializerModifier() {
                        override fun updateProperties(
                            config: DeserializationConfig,
                            beanDescription: BeanDescription,
                            propertyDefinitions: MutableList<BeanPropertyDefinition>
                        ): MutableList<BeanPropertyDefinition> {
                            return propertyDefinitions.map { propertyDefinition ->
                                propertyDefinition
                                    .withName(PropertyName("bar"))
                                    .withSimpleName("bar")
                            }.toMutableList()
                        }
                    }
                )
        )
    }
    private val serialized = """{"bar":"foo"}"""

    @Test
    fun dataClassWithVal_Serialize() {
        val dataClassWithVal = DataClassWithVal("foo")

        assertEquals(serialized, objectMapper.writeValueAsString(dataClassWithVal))
        assertEquals(dataClassWithVal, objectMapper.readValue(serialized))
    }

    @Test
    fun dataClassWithVar_Serialize() {
        val dataClassWithVar = DataClassWithVar("foo")

        assertEquals(serialized, objectMapper.writeValueAsString(dataClassWithVar))
        assertEquals(dataClassWithVar, objectMapper.readValue(serialized))
    }

    @Test
    fun classWithVal_Serialize() {
        val classWithVal = ClassWithVal("foo")

        assertEquals(serialized, objectMapper.writeValueAsString(classWithVal))
        assertEquals(classWithVal, objectMapper.readValue(serialized))
    }

    @Test
    fun classWithVar_Serialize() {
        val classWithVar = ClassWithVar("foo")

        assertEquals(serialized, objectMapper.writeValueAsString(classWithVar))
        assertEquals(classWithVar, objectMapper.readValue(serialized))
    }

    @Test
    fun classWithLateinitVar_Serialize() {
        val classWithLateinitVar = ClassWithLateinitVar().apply { foo = "foo" }

        assertEquals(serialized, objectMapper.writeValueAsString(classWithLateinitVar))
        assertEquals(classWithLateinitVar, objectMapper.readValue(serialized))
    }
}