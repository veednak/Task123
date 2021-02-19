package com.example.myapplication.RusPars

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class Valute {
    @JacksonXmlProperty(localName = "ID")
    var ID = ""

    @JacksonXmlProperty(localName = "Value")
    var Value = ""

    @JacksonXmlProperty(localName = "CharCode")
    var CharCode = ""

    @JacksonXmlProperty(localName = "Name")
    var Name = ""

    @JacksonXmlProperty(localName = "Nominal")
    var Nominal = ""

    @JacksonXmlProperty(localName = "NumCode")
    var NumCode = ""
    override fun toString(): String {
        return """$CharCode
$Name  $Value"""
    }
}