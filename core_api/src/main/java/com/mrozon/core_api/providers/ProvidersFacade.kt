package com.mrozon.core_api.providers

import com.mrozon.core_api.db.DatabaseProvider
import com.mrozon.core_api.navigation.NavigatorProvider
import com.mrozon.core_api.network.NetworkProvider
import com.mrozon.core_api.security.SecurityTokenProvider

interface ProvidersFacade : NetworkProvider, DatabaseProvider, AppProvider,
    NavigatorProvider, SecurityTokenProvider