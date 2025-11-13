package com.app.babycare.domain.repository

import com.app.babycare.domain.model.DevTip
import com.app.babycare.domain.model.DevTipData

interface DevTipsRepository {
    suspend fun getAllDevTips(): Result<List<DevTip>>

    suspend fun getDevTipById(tipId: Int): Result<DevTip>

    // Las operaciones create/update/delete devuelven un mensaje (el backend devuelve {"message": "..."})
    suspend fun createDevTip(devTipData: DevTipData): Result<String>

    suspend fun updateDevTip(tipId: Int, devTipData: DevTipData): Result<String>

    suspend fun deleteDevTip(tipId: Int): Result<String>
}