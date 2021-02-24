package com.example.myapplication.RusPars

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class ValCurs {
    @JacksonXmlProperty(localName = "Date")
    var date = ""

    @JacksonXmlProperty(localName = "name")
    var name = ""

    @JacksonXmlProperty(localName = "Valute")
    @JacksonXmlElementWrapper(useWrapping = false)
    var valute: List<Currency>? = null
    override fun toString(): String {
        var str = ""
        for (e in valute!!) {
            str += """
                $e
                
                """.trimIndent()
        }
        return str
    }
}
