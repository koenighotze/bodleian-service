package org.koenighotze.bodleian.bookcatalog.openlibrary

data class OpenLibraryBook(

//    var identifiers: Identifiers? = Identifiers(),
    var title: String? = null,
    var authors: ArrayList<Authors> = arrayListOf(),
//    var publishDate: String? = null,
//    var publishers: ArrayList<String> = arrayListOf(),
    var covers: ArrayList<Int> = arrayListOf(),
//    var contributions: ArrayList<String> = arrayListOf(),
//    var languages: ArrayList<Languages> = arrayListOf(),
//    var sourceRecords: ArrayList<String> = arrayListOf(),
//    var localId: ArrayList<String> = arrayListOf(),
//    var type: Type? = Type(),
//    var firstSentence: FirstSentence? = FirstSentence(),
    var key: String? = null,
//    var numberOfPages: Int? = null,
//    var works: ArrayList<Works> = arrayListOf(),
//    var classifications: Classifications? = Classifications(),
//    var ocaid: String? = null,
    var isbn10: ArrayList<String> = arrayListOf(),
    var isbn13: ArrayList<String> = arrayListOf(),
//    var latestRevision: Int? = null,
//    var revision: Int? = null,
//    var created: Created? = Created(),
//    var lastModified: LastModified? = LastModified(),

)