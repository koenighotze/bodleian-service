package org.koenighotze.bodleian.bookcatalog.entity.jpa

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.koenighotze.bodleian.bookcatalog.entity.OpenBookApiId

@Converter
class OpenBookApiIdStringConverter : AttributeConverter<OpenBookApiId?, String?> {
    override fun convertToDatabaseColumn(attribute: OpenBookApiId?) = attribute?.code

    override fun convertToEntityAttribute(dbData: String?): OpenBookApiId? =
        if (null == dbData) null else OpenBookApiId(dbData)
}
