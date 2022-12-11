package com.serdar.floward.modules

import com.serdar.floward.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepository(get()) }
}