package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.UserRole
import com.example.data.repository.CyberLinkRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("CyberLink Control AI", appName)
    }

    @Test
    fun `repository initial state and device operations`() {
        val repo = CyberLinkRepository()
        assertNotNull(repo.currentUser.value)
        assertEquals(UserRole.ADMIN_GUARDIAN, repo.currentUser.value?.role)

        val initialDevices = repo.devices.value
        assertTrue(initialDevices.isNotEmpty())

        val devId = initialDevices.first().id
        repo.toggleDeviceScreenLock(devId)
        val updatedDev = repo.devices.value.first { it.id == devId }
        assertTrue(updatedDev.isScreenLocked)
    }
}
