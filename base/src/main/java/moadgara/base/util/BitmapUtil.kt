package moadgara.base.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.HardwareRenderer
import android.graphics.PixelFormat
import android.graphics.RenderEffect
import android.graphics.RenderNode
import android.graphics.Shader
import android.hardware.HardwareBuffer
import android.media.ImageReader
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.annotation.RequiresApi

object BitmapUtil {
    fun blurImage(inputBitmap: Bitmap, context: Context): Bitmap? {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            blurBitmapWithRenderScript(inputBitmap, context)
        } else {
            blurBitmapWithRenderEffect(inputBitmap)
        }

    }

    @Suppress("Deprecation")
    private fun blurBitmapWithRenderScript(inputBitmap: Bitmap, context: Context): Bitmap? {
        val renderScript = RenderScript.create(context)
        val input = Allocation.createFromBitmap(renderScript, inputBitmap)
        val output = Allocation.createTyped(renderScript, input.type)

        val blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        blurScript.setRadius(25f)
        blurScript.setInput(input)
        blurScript.forEach(output)
        output.copyTo(inputBitmap)

        renderScript.destroy()
        return inputBitmap
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun blurBitmapWithRenderEffect(inputBitmap: Bitmap): Bitmap? {
        val imageReader = ImageReader.newInstance(
            inputBitmap.width, inputBitmap.height,
            PixelFormat.RGBA_8888, 1,
            HardwareBuffer.USAGE_GPU_SAMPLED_IMAGE or HardwareBuffer.USAGE_GPU_COLOR_OUTPUT
        )
        val renderNode = RenderNode("BlurEffect")
        val hardwareRenderer = HardwareRenderer()

        hardwareRenderer.setSurface(imageReader.surface)
        hardwareRenderer.setContentRoot(renderNode)
        renderNode.setPosition(0, 0, imageReader.width, imageReader.height)
        val blurRenderEffect = RenderEffect.createBlurEffect(
            25f, 25f,
            Shader.TileMode.MIRROR
        )
        renderNode.setRenderEffect(blurRenderEffect)

        val renderCanvas = renderNode.beginRecording()
        renderCanvas.drawBitmap(inputBitmap, 0f, 0f, null)
        renderNode.endRecording()
        hardwareRenderer.createRenderRequest()
            .setWaitForPresent(true)
            .syncAndDraw()

        val image = imageReader.acquireNextImage() ?: throw RuntimeException("No Image")
        val hardwareBuffer = image.hardwareBuffer ?: throw RuntimeException("No HardwareBuffer")
        val bitmap = Bitmap.wrapHardwareBuffer(hardwareBuffer, null)

        hardwareBuffer.close()
        image.close()
        imageReader.close()
        renderNode.discardDisplayList()
        hardwareRenderer.destroy()

        return bitmap?.copy(Bitmap.Config.ARGB_8888, false)
    }

}