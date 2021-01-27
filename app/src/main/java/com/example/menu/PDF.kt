package com.example.menu

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnDrawListener

class PDF : AppCompatActivity() {

//Se inicializa la variable de la libreria
    lateinit var pdfView: PDFView
    val PDF_SELECTION_CODE= 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p_d_f)

        //Se relaciona la variable pdfView con su id en el layout
        pdfView = findViewById(R.id.pdfView)
        selectPdfFromStorage()
    }

//Seleccionar de la memoria interna
    private fun selectPdfFromStorage() {
        Toast.makeText(this@PDF, "Selecciona el pdf", Toast.LENGTH_SHORT).show()  //Toast que muestra el mensaje de seleccionar por unos segundos
        val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
        browseStorage.type = "application/pdf"
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(Intent.createChooser(browseStorage, "Selecciona el pdf"),PDF_SELECTION_CODE)
    }
//Funcion mostrar documento con opciones
    fun showPdfFromUri (uri : Uri?) {
        pdfView.fromUri(uri)
                .defaultPage(0)
                .spacing( 10)
                .enableAnnotationRendering(true)
                .enableDoubletap(true)
                .load()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PDF_SELECTION_CODE && resultCode == Activity.RESULT_OK && data !=null){
            val selectedPdf = data.data    //Variable cuyo valor no cambia
            showPdfFromUri(selectedPdf) //Se llama a la funcion para ser ejecutada



        }
    }
}