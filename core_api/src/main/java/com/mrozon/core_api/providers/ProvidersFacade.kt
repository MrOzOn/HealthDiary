package com.mrozon.core_api.providers

import com.mrozon.core_api.db.DatabaseProvider
import com.mrozon.core_api.network.NetworkProvider

interface ProvidersFacade : NetworkProvider, DatabaseProvider, AppProvider