package net.iconomi.api.client;

import net.iconomi.api.client.model.Balance;
import net.iconomi.api.client.model.Daa;
import net.iconomi.api.client.model.DaaChart;
import net.iconomi.api.client.model.DaaList;
import net.iconomi.api.client.model.DaaPrice;
import net.iconomi.api.client.model.DaaStructure;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IconomiService {

    @GET("/v1/daa")
    Call<DaaList> listDaa();

    @GET("/v1/daa/{daa}")
    Call<Daa> getDaa(@Path("daa") String daa);

    @GET("/v1/daa/{daa}/structure")
    Call<DaaStructure> getDaaStructure(@Path("daa") String ticker);

    @GET("/v1/daa/{daa}/price")
    Call<DaaPrice> getDaaPrice(@Path("daa") String ticker);


    @GET("/v1/daa/{ticker}/pricehistory")
    Call<DaaChart> getDaaPriceHistory(@Path("ticker") String ticker, @Query("from") Long from, @Query("to") Long to);


    @GET("/v1/user/balance")
    Call<Balance> getUserBalance(@Query("currency") String currency);


}
