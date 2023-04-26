package com.example.holybibleapp.core

import junit.framework.Assert.assertTrue
import org.junit.Test
import java.io.IOException
import java.lang.Exception

class AbstractTest{
    @Test
    fun test_success(){
        val dataObject = TestDataObject.Success("a", "b")
        val domainObject = dataObject.map(DataMapper.Base())
        assertTrue(domainObject is DomainObject.Success)
    }
    @Test
    fun test_fail(){
        val dataObject = TestDataObject.Fail(IOException())
        val domainObject = dataObject.map(DataMapper.Base())
        assertTrue(domainObject is DomainObject.Fail)
    }

    private sealed class TestDataObject : Abstract.Object<DomainObject, DataMapper> (){
        abstract override fun map(mapper: DataMapper): DomainObject

        class Success(
            private val textOne: String,
            private val textTwo: String
            ) : TestDataObject(){
            override fun map(mapper: DataMapper): DomainObject {
                return mapper.map(textOne, textTwo)
            }

        }

        class Fail (private val exception: java.lang.Exception) : TestDataObject() {
            override fun map(mapper: DataMapper): DomainObject {
                return mapper.map(exception)
            }

        }
    }

    private interface DataMapper : Abstract.Mapper{

        fun map(textOne: String, textTwo: String) : DomainObject

        fun map(exception: java.lang.Exception) : DomainObject

        class Base : DataMapper {
            override fun map(textOne: String, textTwo: String): DomainObject {
                return DomainObject.Success("$textOne $textTwo")
            }

            override fun map(exception: Exception): DomainObject {
                return DomainObject.Fail()
            }

        }
    }

    private sealed class DomainObject : Abstract.Object<UiObject, DomainToUiMapper> (){
        class Success(private val textCombined: String) : DomainObject() {
            override fun map(mapper: DomainToUiMapper): UiObject {
                TODO("Not yet implemented")
            }
        }

        class Fail : DomainObject(){
            override fun map(mapper: DomainToUiMapper): UiObject {
                TODO("Not yet implemented")
            }
        }
    }

    private interface DomainToUiMapper : Abstract.Mapper

    private sealed class UiObject : Abstract.Object<Unit, Abstract.Mapper.Empty>(){

    }
}