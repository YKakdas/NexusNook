package moadgara.data

interface ResponseMapper {
    fun toSmallViewData(): List<CommonResponseData>?

    fun toImageList(): List<String>?

    fun rawResponse(): Any?
}