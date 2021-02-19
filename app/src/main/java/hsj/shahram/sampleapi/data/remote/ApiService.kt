package hsj.shahram.sampleapi.data.remote

import hsj.shahram.sampleapi.data.model.ResponseObj
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {


    @GET("users")
    fun getUserList()
            : Single<List<ResponseObj.UserItem>>

    @GET("comments")
    fun getComment(@Query("postId") postId : Int)
            : Single<List<ResponseObj.Comment>>


    @GET("posts")
    fun getPostList()
            : Single<MutableList<ResponseObj.Post>>

    @DELETE("posts/{post_id}")
    fun deletePost(@Path(value = "post_id") postId : Int) :Completable


    @PUT("posts/{post_id}")
    fun editPost(@Path(value = "post_id") postId : Int , @Body post:ResponseObj.Post)
    :Single<ResponseObj.Post>


    @POST("posts")
    fun createPost(@Body post:ResponseObj.Post)
            :Single<ResponseObj.Post>
}