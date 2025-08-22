package com.example.final_project_apk.network

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {

    private const val PREF_NAME = "app_preferences"
    private const val IP_LIST_KEY = "ip_list"
    private const val ACTIVE_INDEX_KEY = "active_index"
    private const val MAX_STEP_KEY = "max_step"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // Menyimpan entri baru
    fun saveIpEntry(context: Context, ip: String, socketPort: String, streamPort: String) {
        val prefs = getPrefs(context)
        val editor = prefs.edit()
        val list = getIpList(context).toMutableList()
        list.add(Triple(ip, socketPort, streamPort))
        val serialized = list.joinToString("|") { "${it.first},${it.second},${it.third}" }
        editor.putString(IP_LIST_KEY, serialized)
        editor.putInt(ACTIVE_INDEX_KEY, list.size - 1) // Set otomatis ke yang baru
        editor.apply()
    }

    // Mengambil list IP
    fun getIpList(context: Context): List<Triple<String, String, String>> {
        val prefs = getPrefs(context)
        val raw = prefs.getString(IP_LIST_KEY, "") ?: ""
        if (raw.isBlank()) return emptyList()

        return raw.split("|").mapNotNull {
            val parts = it.split(",")
            if (parts.size == 3) Triple(parts[0], parts[1], parts[2]) else null
        }
    }

    // Mendapatkan index aktif
    fun getActiveIndex(context: Context): Int {
        return getPrefs(context).getInt(ACTIVE_INDEX_KEY, 0)
    }

    // Set index aktif
    fun setActiveIndex(context: Context, index: Int) {
        getPrefs(context).edit().putInt(ACTIVE_INDEX_KEY, index).apply()
    }

    // Ambil entri aktif
    fun getActiveIpEntry(context: Context): Triple<String, String, String>? {
        val list = getIpList(context)
        val index = getActiveIndex(context)
        return if (list.isNotEmpty() && index in list.indices) list[index] else null
    }

    // URL untuk socket (http://ip:socketPort)
    fun getActiveSocketUrl(context: Context): String {
        val (ip, socketPort, _) = getActiveIpEntry(context) ?: return ""
        return "http://$ip:$socketPort"
    }

    // URL untuk stream (http://ip:streamPort/video_feed)
    fun getActiveStreamUrl(context: Context): String {
        val (ip, _, streamPort) = getActiveIpEntry(context) ?: return ""
        return "http://$ip:$streamPort/video_feed"
    }

    fun removeIpEntry(context: Context, index: Int) {
        val prefs = getPrefs(context)
        val editor = prefs.edit()
        val list = getIpList(context).toMutableList()

        if (index in list.indices) {
            list.removeAt(index)
            val serialized = list.joinToString("|") { "${it.first},${it.second},${it.third}" }
            editor.putString(IP_LIST_KEY, serialized)

            val activeIndex = getActiveIndex(context)
            // Jika yang dihapus adalah yang aktif, atau index aktif lebih besar dari list baru, reset ke 0
            if (index == activeIndex || activeIndex >= list.size) {
                editor.putInt(ACTIVE_INDEX_KEY, 0)
            } else if (index < activeIndex) {
                // Jika yang dihapus berada sebelum index aktif, kurangi index aktif
                editor.putInt(ACTIVE_INDEX_KEY, activeIndex - 1)
            }
            editor.apply()
        }
    }

    fun getMaxStep(context: Context): Int {
        val prefs = getPrefs(context)
        return prefs.getInt(MAX_STEP_KEY, 13000) // Default 13000
    }

    fun setMaxStep(context: Context, value: Int) {
        val prefs = getPrefs(context)
        prefs.edit().putInt(MAX_STEP_KEY, value).apply()
    }

    // Hapus semua IP
    fun clearAll(context: Context) {
        getPrefs(context).edit().clear().apply()
    }
}