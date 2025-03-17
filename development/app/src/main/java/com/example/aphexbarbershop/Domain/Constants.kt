package com.example.aphexbarbershop.Domain


import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
//import io.github.jan.supabase.gotrue.SettingsSessionManager
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod
import io.github.jan.supabase.storage.Storage

object Constants {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://voiqugocrxylekpfxpaa.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZvaXF1Z29jcnh5bGVrcGZ4cGFhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE4NzExMzcsImV4cCI6MjA1NzQ0NzEzN30.Dbi0BNELWrbMML9Cpakdc3U52h_QeGGp1gcqvbkU9EY"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}