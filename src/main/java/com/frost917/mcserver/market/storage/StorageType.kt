package com.frost917.mcserver.market.storage

enum class StorageType {
    // File base storage
    SQLITE,
    H2,

    // Remote storage
    MYSQL,
    MARIADB,
    POSTGRES
}