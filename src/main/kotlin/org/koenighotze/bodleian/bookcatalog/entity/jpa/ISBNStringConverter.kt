package org.koenighotze.bodleian.bookcatalog.entity.jpa

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.koenighotze.bodleian.bookcatalog.entity.ISBN

@Converter
class ISBNStringConverter : AttributeConverter<ISBN?, String?> {
    override fun convertToDatabaseColumn(attribute: ISBN?) = attribute?.code

    override fun convertToEntityAttribute(dbData: String?): ISBN? = if (null == dbData) null else ISBN(dbData)
}
