package com.example.tuticket

import org.mockito.Mockito
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class RegistroActivityTest {
    @Mock
    private lateinit var mockDatabaseReference: DatabaseReference

    @Mock
    private lateinit var mockTask: Task<Void>

    private lateinit var objetoBajoPrueba: RegistroActivityTest

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        objetoBajoPrueba = RegistroActivityTest()
    }

    @Test
    fun guardarRegistroEnDatabase_guardadoExitoso_muestraMensajeCorrecto() {

        `when`(mockDatabaseReference.setValue(Mockito.anyMap<String, Any>()))
            .thenReturn(mockTask)

        `when`(mockTask.addOnSuccessListener(Mockito.any()))
            .thenAnswer { invocation ->
                val successListener = invocation.arguments[0] as OnSuccessListener<Void>
                successListener.onSuccess(null)
                mockTask
            }

        objetoBajoPrueba.guardarRegistroEnDatabase("userId", "email", "nombres", "apellidos", "numeroTel")

    }

    private fun guardarRegistroEnDatabase(userId: String, email: String, nombres: String, apellidos: String, numeroTel: String) {
        val datosUsuario = HashMap<String, Any>()
        datosUsuario["userId"] = userId
        datosUsuario["email"] = email
        datosUsuario["nombres"] = nombres
        datosUsuario["apellidos"] = apellidos
        datosUsuario["numeroTel"] = numeroTel

    }
}

