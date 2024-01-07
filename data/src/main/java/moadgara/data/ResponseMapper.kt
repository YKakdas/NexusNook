package moadgara.data

interface ResponseMapper {
    fun toSmallViewData(): List<CommonResponseData>?
}