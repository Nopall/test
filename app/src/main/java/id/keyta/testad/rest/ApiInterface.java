package id.keyta.testad.rest;

import java.util.List;

import id.keyta.testad.model.ItemRequest;
import id.keyta.testad.model.ItemResponse;
import id.keyta.testad.model.MessageResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("get_data")
    Call<List<ItemResponse>> getListData();

    @POST("send_data")
    Call<MessageResponse> kirimData(@Body ItemRequest request);
}
