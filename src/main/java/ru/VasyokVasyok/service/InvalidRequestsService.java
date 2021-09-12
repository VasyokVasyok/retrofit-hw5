package ru.VasyokVasyok.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.VasyokVasyok.dto.InvalidRequest;

public interface InvalidRequestsService {
    @GET("categories/{id}")
    Call<InvalidRequest> getInvalidRequests(@Path("id") int id);
}
