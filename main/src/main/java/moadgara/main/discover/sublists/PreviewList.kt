package moadgara.main.discover.sublists

import androidx.lifecycle.MutableLiveData
import com.moadgara.common_model.network.NetworkResult
import com.moadgara.common_model.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import moadgara.main.discover.PreviewListMetaData
import moadgara.main.discover.PreviewListViewData

abstract class PreviewList(previewListCommonParameters: PreviewListCommonParameters, useCase: FlowUseCase<out Any, out Any>) {

    abstract val previewListType: PreviewListType

    abstract fun getPreviewListMetaData(): PreviewListMetaData

    abstract fun getInnerItemAction(name: String?): () -> Unit

    abstract fun getViewLiveData(): MutableLiveData<PreviewListViewData>

    abstract suspend fun invokeUseCase(): Flow<NetworkResult<Any>>
}