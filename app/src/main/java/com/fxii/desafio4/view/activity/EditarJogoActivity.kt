package com.fxii.desafio4.view.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fxii.desafio4.GlideApp
import com.fxii.desafio4.R
import com.fxii.desafio4.databinding.ActivityEditarJogoBinding
import com.fxii.desafio4.extensions.validateRequiredField
import com.fxii.desafio4.model.Jogo
import com.fxii.desafio4.viewModel.EditarJogoViewModel

class EditarJogoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarJogoBinding

    private lateinit var editarJogoViewModel: EditarJogoViewModel

    private var jogo: Jogo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarJogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editarJogoViewModel = ViewModelProvider(this).get(EditarJogoViewModel::class.java)

        jogo = intent.getParcelableExtra<Jogo>("JOGO")


        setupView()

        setupObservables()
    }

    private fun setupView() {
        jogo?.let { jogo ->
            with(binding) {
                edEditarJogoNome.setText(jogo.nome)
                // nome do jogo é o id no Firestore, não deixa editar
                tfEditarJogoNome.isEnabled = false
                edEditarJogoDescricao.setText(jogo.descricao)
                edEditarJogoAno.setText(jogo.lancamento.toString())
                ivEditarJogoCapa.tag = "EDITANDO"

                GlideApp.with(this@EditarJogoActivity)
                        .load(jogo.imagem)
                        .into(ivEditarJogoCapa)
            }
        }
    }

    private fun setupObservables() {
        binding.btnEditarJogoSalvar.setOnClickListener {
            // salva o jogo
            with(binding) {
                // valida campos
                if(ivEditarJogoCapa.tag == null) {
                    Toast.makeText(baseContext, "Image Required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val checkValidate = tfEditarJogoNome.validateRequiredField(R.string.name)
                        .and(tfEditarJogoDescricao.validateRequiredField(R.string.description))
                        .and(tfEditarJogoAno.validateRequiredField(R.string.created_at))


                if(checkValidate) {
                    val nome = edEditarJogoNome.text.toString()
                    val lancamento = edEditarJogoAno.text.toString().toInt()
                    val descricao = edEditarJogoDescricao.text.toString()
                    var imagem:Uri? = null
                    if(! ivEditarJogoCapa.tag.equals("EDITANDO"))
                        imagem = ivEditarJogoCapa.tag as Uri
                    editarJogoViewModel.saveJogo(nome, descricao, lancamento, imagem)
                }
            }
            //finish()
        }

        editarJogoViewModel.errorMsg.observe(this) { erroMsg ->
            Toast.makeText(baseContext, erroMsg,
                Toast.LENGTH_SHORT).show()
        }

        editarJogoViewModel.jogoSalvo.observe(this) {
            if(it) {
                finish()
            }
        }

        binding.fabEditarJogoCapa.setOnClickListener {
            Log.i("Teste", "Alterar imagem capa")
            // pega imagem da galeria
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }

        // mesmo inibindo ediçao o ellipsis não funcionou
//        with(binding.edEditarJogoDescricao) {
//            keyListener = null
//
//            setOnFocusChangeListener { v, hasFocus ->
//                if (hasFocus) {
//                    keyListener = TextKeyListener(TextKeyListener.Capitalize.WORDS, false)
//                } else {
//                    keyListener = null
//                }
//            }
//        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        //startActivityForResult(intent, IMAGE_PICK_CODE)
        resultLauncher.launch(intent)
    }

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { img ->
                binding.ivEditarJogoCapa.setImageURI(img)
                binding.ivEditarJogoCapa.tag = img
            }
        }
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //handle result of picked image
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
//            data?.data?.let {
//                binding.ivEditarJogoCapa.setImageURI(data?.data)
//
//
////                // salva imagem no store
////                val fileRef = storageRef.child("userId/teste.jpg")
////
////                fileRef.putFile(it).addOnSuccessListener {
////                    Toast.makeText(
////                            this@EditarJogoActivity,
////                            "Foto salva com sucesso",
////                            Toast.LENGTH_SHORT
////                    ).show()
////                }.addOnFailureListener {
////                    Toast.makeText(
////                            this@EditarJogoActivity,
////                            it.localizedMessage,
////                            Toast.LENGTH_SHORT
////                    ).show()
////                }
//            }
//        }
//    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }
}