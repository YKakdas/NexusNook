package moadgara.main.paging.games

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import moadgara.base.extension.orDefault
import moadgara.base.util.CoilUtil
import moadgara.base.util.ResourceProvider
import moadgara.main.BR
import moadgara.main.R
import moadgara.main.databinding.LayoutPagingGameItemViewBinding

class GamesPagingAdapter internal constructor(
    val resourceProvider: ResourceProvider,
    callback: DiffUtil.ItemCallback<GamesPagingItemData>
) :
    PagingDataAdapter<GamesPagingItemData, GamesPagingAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesPagingAdapter.ViewHolder = ViewHolder(
        LayoutPagingGameItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: GamesPagingAdapter.ViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class ViewHolder(private val binding: LayoutPagingGameItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: GamesPagingItemData?) {
            data?.let { binding.setVariable(BR.data, data) }

            binding.image.load(data?.image, CoilUtil.getCachedCoilImageLoader(binding.image.context)) {
                error(R.drawable.nexus_nook_image)
                listener(onSuccess = { _, _ ->
                    setCardBackgroundColor((binding.image.drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true))
                })
            }
            binding.executePendingBindings()
        }

        private fun setCardBackgroundColor(bitmap: Bitmap?) {
            val defaultColor = resourceProvider.getColor(moadgara.uicomponent.R.color.onyx)
            bitmap?.let {
                Palette.from(it).generate { palette ->
                    val gradientDrawable = GradientDrawable().apply {
                        colors = intArrayOf(
                            palette?.getDarkVibrantColor(defaultColor).orDefault(defaultColor),
                            palette?.getMutedColor(defaultColor).orDefault(defaultColor),
                            palette?.getDarkMutedColor(defaultColor).orDefault(defaultColor)
                        )
                        orientation = GradientDrawable.Orientation.LEFT_RIGHT
                        gradientType = GradientDrawable.LINEAR_GRADIENT
                        shape = GradientDrawable.RECTANGLE
                    }

                    gradientDrawable.cornerRadius =
                        resourceProvider.getDimension(moadgara.uicomponent.R.dimen.paged_game_item_card_corner_radius)
                    binding.card.setBackgroundDrawable(gradientDrawable)
                }
            }
        }
    }
}