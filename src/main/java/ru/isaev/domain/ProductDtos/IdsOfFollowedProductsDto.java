package ru.isaev.domain.ProductDtos;

import java.util.List;

public class IdsOfFollowedProductsDto {
    List<Long> idsOfFollowedProducts;

    public List<Long> getIdsOfFollowedProducts() {
        return idsOfFollowedProducts;
    }

    public void setIdsOfFollowedProducts(List<Long> idsOfFollowedProducts) {
        this.idsOfFollowedProducts = idsOfFollowedProducts;
    }
}
