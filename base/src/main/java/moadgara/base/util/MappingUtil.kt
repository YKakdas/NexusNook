package moadgara.base.util

object MappingUtil {
    fun mapValue(value: Float, sourceMin: Float, sourceMax: Float, targetMin: Float, targetMax: Float): Float {
        val sourceRange = sourceMax - sourceMin
        val targetRange = targetMax - targetMin
        return (value - sourceMax) * (targetRange / (sourceRange)) + targetMax
    }
}