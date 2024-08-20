package ru.isaev.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.Users.Roles;
import ru.isaev.Domain.Users.User;
import ru.isaev.Repo.ProductRepo;
import ru.isaev.Repo.UserRepo;
import ru.isaev.Service.Security.MyUserDetails;
import ru.isaev.Service.Utilities.Exceptions.NotYourProductException;
import ru.isaev.Service.Utilities.Exceptions.NotYourProfileException;
import ru.isaev.Service.Utilities.Exceptions.ProductNotFoundExceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService implements IProductService {
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    @Autowired
    public ProductService(ProductRepo productRepo, UserRepo userRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public void addProduct(Product product) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        product.setOwner(currentUser);
        List<Product> productsOfUser = currentUser.getProductsList();
        productsOfUser.add(product);
        currentUser.setProductsList(productsOfUser);

        productRepo.save(product);
        userRepo.save(currentUser);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + id)
        );
    }

    @Override
    public void updateProduct(Product product) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        if (!Objects.equals(product.getOwner().getId(), currentUser.getId()) && currentUser.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProductException("Not your product with id = " + product.getId());

        productRepo.save(product);
    }

    @Override
    public void removeProductById(Long id) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

        productRepo.findById(id).orElseThrow(
                () -> new ProductNotFoundExceptions("Not found product with id = " + id)
        );

        if (!Objects.equals(currentUser.getId(), id)  && currentUser.getRole() != Roles.ROLE_ADMIN)
            throw new NotYourProfileException("Not your profile with id = " + id);

        productRepo.deleteById(id);
    }
}
