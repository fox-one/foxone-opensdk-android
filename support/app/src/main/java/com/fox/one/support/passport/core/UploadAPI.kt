package com.fox.one.support.passport.core

import com.fox.one.support.framework.network.APILoader
import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.passport.core.models.UploadResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-22
 */
object UploadAPI: IUploadAPI {

    private const val HOST = "https://dev-cloud.fox.one"
    const val HEADER_MERCHANT_ID = "fox-cloud-merchant-id"
    private const val MERCHANT_ID = "5c8a9491dca25af694004d5e1711b217"
    private val apiLoader = APILoader()

    init {
        apiLoader.setBaseUri(APILoader.BaseUrl(HOST, HOST, HOST))
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