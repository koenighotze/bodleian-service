package org.koenighotze.bodleian.bookcatalog.entity

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class ISBNStringConverter : AttributeConverter<ISBN?, String?> {
    override fun convertToDatabaseColumn(attribute: ISBN?) = attribute?.code

    override fun convertToEntityAttribute(dbData: String?): ISBN? = if (null == dbData) null else ISBN(dbData)
}
