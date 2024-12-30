/*
 * SPDX-FileCopyrightText: 2024 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.glimpse.datasources

/**
 * [MediaDataSource] errors.
 */
enum class MediaError {
    /**
     * This feature isn't implemented.
     */
    NOT_IMPLEMENTED,

    /**
     * I/O error, can also be network.
     */
    IO,

    /**
     * Authentication error.
     */
    AUTHENTICATION_REQUIRED,

    /**
     * Invalid credentials.
     */
    INVALID_CREDENTIALS,

    /**
     * The item was not found.
     */
    NOT_FOUND,

    /**
     * Value returned on write requests: The value already exists.
     */
    ALREADY_EXISTS,

    /**
     * Response deserialization error.
     */
    DESERIALIZATION,
}
