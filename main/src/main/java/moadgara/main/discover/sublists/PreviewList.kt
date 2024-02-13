package moadgara.main.discover.sublists

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import moadgara.data.ResponseMapper
import moadgara.domain.ListType
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData

abstract class PreviewList {

    abstract val previewListType: ListType

    abstract fun getPreviewListMetaData(): PreviewListMetaData

    abstract fun getInnerItemAction(id: Int?, name: String?, response: Any?): () -> Unit

    abstract fun getViewLiveData(): MutableLiveData<PreviewListViewData>

    abstract suspend fun invokeUseCase(): NetworkResult<ResponseMapper>
}