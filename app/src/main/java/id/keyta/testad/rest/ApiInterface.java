package id.keyta.testad.rest;

import java.util.List;

import id.keyta.testad.model.ItemRequest;
import id.keyta.testad.model.ItemResponse;
import id.keyta.testad.model.MessageResponse;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("api/v1/get_data/")
    Observable<List<ItemResponse>> getListData();

    @POST("api/v1/send_data/")
    Observable<MessageResponse> kirimData(@Body ItemRequest request);
}
