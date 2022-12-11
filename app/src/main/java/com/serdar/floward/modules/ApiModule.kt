package com.serdar.floward.modules

import com.serdar.floward.api.UserApi
import com.serdar.floward.api.UserApiImpl
import org.koin.dsl.module

val apiModule = module {
    single<UserApi> { UserApiImpl(get()) }
}