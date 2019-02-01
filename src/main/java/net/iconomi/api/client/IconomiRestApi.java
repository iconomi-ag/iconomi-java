package net.iconomi.api.client;

import net.iconomi.api.client.model.Balance;
import net.iconomi.api.client.model.Daa;
import net.iconomi.api.client.model.DaaChart;
import net.iconomi.api.client.model.StructureElement;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface IconomiRestApi {

    /**
     * @return List of daas
     */
    List<Daa> getDaaList() throws IOException;

    /**
     * @param ticker name of digital portfolio
     * @return {@link Daa} with information about digital portfolio
     */
    Daa getDaa(String ticker) throws IOException;

    /**
     * @param ticker name of daa
     * @return structure of Daa
     */
    List<StructureElement> getDaaStructure(String ticker) throws IOException;

    /**
     * @param ticker name of daa
     * @return price
     */
    BigDecimal getDaaPrice(String ticker) throws IOException;


    /**
     * @param ticker name of daa
     * @return price history
     */
    DaaChart getDaaPriceHistry(String ticker, long from, long to) throws IOException;

    /**
     * Returns balance for user that is logged in via api key
     *
     * @return {@link Balance} or null if user is not authorized
     */
    Balance getUserBalance() throws IOException;

}
