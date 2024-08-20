package ru.isaev.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.Users.User;
import ru.isaev.Repo.ProductRepo;
import ru.isaev.Repo.UserRepo;
import ru.isaev.Service.Security.MyUserDetails;

import java.util.List;

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
        return null;
    }

    @Override
    public void addProduct(Product product) {
        MyUserDetails currentPrincipal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentPrincipal.getUser();

    }

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void removeProductById(Long id) {

    }
}
