package hsj.shahram.sampleapi.repository

import hsj.shahram.sampleapi.data.local.Pref
import hsj.shahram.sampleapi.data.model.ResponseObj
import hsj.shahram.sampleapi.data.remote.ApiService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class MainRepo @Inject constructor(val apiService: ApiService , val pref: Pref) {



   fun getUserInfo() :String? {

       return pref.getString(Pref.USER_ID);

   }


    fun saveUserData(user : ResponseObj.UserItem)
    {

        pref.putString(Pref.USER_ID , user.id.toString())
        pref.putString(Pref.USER_EMAIL , user.email)
        pref.putString(Pref.USER_NAME , user.userName)

    }

    fun getPostList() : Single<MutableList<ResponseObj.Post>>
    {
        return apiService.getPostList();
    }


    fun deletePost(postId : Int) :Completable{

       return apiService.deletePost(postId)

    }



    fun editPost(post:ResponseObj.Post) :Single<ResponseObj.Post>
    {

      return  apiService.editPost(post.id!! , post)

    }


    fun getComments(postId : Int) : Single<List<ResponseObj.Comment>>
    {
        return apiService.getComment(postId)
    }

    fun createPost(post:ResponseObj.Post) :Single<ResponseObj.Post>
    {

        return  apiService.createPost(post)

    }
    fun getUserList():Single<List<ResponseObj.UserItem>>{

        return apiService.getUserList()
    }


}