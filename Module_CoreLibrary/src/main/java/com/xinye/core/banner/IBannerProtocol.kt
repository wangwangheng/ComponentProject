package com.xinye.core.banner

import java.io.Serializable

/**
 * Banner协议
 *
 * @author wangheng
 */
interface IBannerProtocol : Serializable {
    companion object {
        const val serialVersionUID = 4981938402094129713L
    }

    fun getBannerImageUrl(): String?
}