package com.example.doglist.repository

import com.example.doglist.model.DogResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    //Pasar @url en la funcion en lo mismo que colocar en el @GET("URL") URL -> END Point
    //Se suele poner en el GET salvo que no conozca la ruta final
    @GET
    suspend fun getDogsByBreeds(@Url url: String): Response<DogResponse>

    @GET("/example/examplepath/{id}/myApiService")
    suspend fun pathExample(@Path("id") id: String): Response<DogResponse>


    //Esto va a hacer /example/examplepath/v2/myApiService&pet={pet}&name={name}
    @GET("/example/examplepath/v2/myApiService")
    suspend fun queryExample(
        @Query("pet") pet: String,
        @Query("name") name: String
    ): Response<DogResponse>

    //Body le dice que lo que espera post es el contenido de mi dto
    @POST
    suspend fun getEverything(@Body examplePostDto: examplePostDto): Call<*>

    //Para subir fotos en vez de subirlas enteras las sube de a pedazos
    @Multipart
    @POST
    suspend fun getEverything(
        @Part image: MultipartBody.Part,
        @Part("example") myExample: String
    ): Call<*>

    //como conseguir el multipart!
    //val requestBody= RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)),file)
    //val image = MultipartBody.Part.createFormData("picture", file.getName(), requestBody)
}

//name y age tiene que tener el mismo nombre que me pide el API Dto = Data transfer Object
data class examplePostDto(val name: String, val age: Int)