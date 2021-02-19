package hsj.shahram.sampleapi.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hsj.shahram.sampleapi.data.model.ResponseObj
import hsj.shahram.sampleapi.repository.MainRepo
import hsj.shahram.sampleapi.util.Resources
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostDetailViewModel  @Inject constructor(val repository : MainRepo)  : ViewModel() {


    private val commentsLiveData  = MutableLiveData<Resources<List<ResponseObj.Comment>>>()

    @SuppressLint("CheckResult")
    fun getCommentList(postId : Int)
    {
        commentsLiveData.postValue(Resources.loading(null))
        repository.getComments(postId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({commentsLiveData.postValue(Resources.success(it))}
                        , { error -> commentsLiveData.postValue(Resources.error(null , error.message))})



    }


    fun getCommentLiveData() : LiveData<Resources<List<ResponseObj.Comment>>> = commentsLiveData



}