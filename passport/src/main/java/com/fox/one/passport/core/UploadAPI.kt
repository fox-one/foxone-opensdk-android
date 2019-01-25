package com.fox.one.passport.core

import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.passport.core.model.UploadResponse
import com.fox.one.support.framework.network.HttpEngine
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import java.io.File

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-22
 */
object UploadAPI: IUploadAPI {

    private val apiLoader = APILoader()

    init {
        apiLoader.setBaseUri(APILoader.BaseUrl(PassportAPI.ALPHA_URL, PassportAPI.BETA_URL, PassportAPI.RELEASE_URL))
    }

    /**
     * use method [createUploadReqBody] to create parameter [file]
     */
    override fun uploadFile(file: MultipartBody.Part): FoxCall<UploadResponse> {
        return apiLoader.load(IUploadAPI::class.java)
            .uploadFile(file)
    }

    /**
     * 生成文件上传的数据结构
     */
    fun createUploadReqBody(file: File): MultipartBody.Part {
        val reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return MultipartBody.Part.createFormData("file", file.name, reqBody)
    }
}