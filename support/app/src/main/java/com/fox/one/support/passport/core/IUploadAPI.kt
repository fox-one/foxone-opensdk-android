package com.fox.one.support.passport.core

import com.fox.one.support.framework.network.FoxCall
import com.fox.one.support.passport.core.models.UploadResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-01-22
 */

interface IUploadAPI {

    @Multipart
    @PUT("/api/upload")
    fun uploadFile(@Part file: MultipartBody.Part): FoxCall<UploadResponse>
}