package com.serdar.floward.modules

import com.serdar.floward.fragments.list.UserListViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { UserListViewModel(get()) }
}