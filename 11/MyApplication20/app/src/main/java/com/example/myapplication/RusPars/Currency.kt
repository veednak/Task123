package com.example.myapplication.RusPars

import com.example.myapplication.EnumFlag
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class Currency {
    @JacksonXmlProperty(localName = "ID")
    var ID = ""

    @JacksonXmlProperty(localName = "Value")
    var Value: String = "0.0"

    @JacksonXmlProperty(localName = "CharCode")
    var CharCode: EnumFlag = EnumFlag.RUB

    @JacksonXmlProperty(localName = "Name")
    var Name = ""

    @JacksonXmlProperty(localName = "Nominal")
    var Nominal: Int = 1

    @JacksonXmlProperty(localName = "NumCode")
    var NumCode = ""
    override fun toString(): String {
        return """$CharCode
$Name  $Value"""
    }
}