package ru.isaev.service_made_by_rodina;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.isaev.domain_made_by_isaev.productDtos.IdsOfFollowedProductsDto;
import ru.isaev.domain_made_by_isaev.products.Product;
import ru.isaev.domain_made_by_isaev.products.Status;
import ru.isaev.domain_made_by_isaev.users.Roles;
import ru.isaev.domain_made_by_isaev.users.User;
import ru.isaev.repo_made_by_isaev.ProductRepo;
import ru.isaev.repo_made_by_isaev.IUserRepo;
import ru.isaev.service_made_by_isaev.notificationService.NotificationService;
import ru.isaev.service_made_by_isaev.productService.ProductService;
import ru.isaev.service_made_by_isaev.security.MyUserDetails;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.NotYourProductException;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.ProductNotFoundExceptions;
import ru.isaev.service_made_by_isaev.utilities.Exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepo productRepo;
    @Mock
    private IUserRepo userRepo;
    @Mock
    private NotificationService notificationService;
    private Product product;
    private User user;
    private MyUserDetails myUserDetails;
    private Authentication authentication;

    @BeforeEach
    void before()
    {
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(Roles.ROLE_USER);

        myUserDetails = new MyUserDetails(user);
        authentication = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());

        product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");
        product.setCategory("Test Category");
        product.setOwner(user);
        product.setStatus(Status.APPROVED);
    }

    @Test
    public void getAllProductsTest()
    {
        List<Product> products = Arrays.asList(product);
        when(productRepo.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(products, result);
    }

    @Test
    public void getAllApprovedProductsTest()
    {
        List<Product> products = Arrays.asList(product);
        when(productRepo.findByStatus(Status.APPROVED)).thenReturn(products);

        List<Product> result = productService.getAllApprovedProducts();

        assertNotNull(result);
        assertEquals(products, result);
    }

    @Test
    public void getAllProductsByUserNotAdminTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        assertThrows(NotYourProductException.class, () -> productService.getAllProductsByUser(user));
    }

    @Test
    public void getProductsByUserIdAndStatusTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        List<Product> products = Arrays.asList(product);
        when(productRepo.findProductsByOwnerAndStatus(user, Status.APPROVED)).thenReturn(products);

        List<Product> result = productService.getProductsByUserIdAndStatus(1L, Status.APPROVED);

        assertNotNull(result);
        assertEquals(products, result);
    }

    @Test
    public void getProductsByUserIdAndStatusNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> productService.getProductsByUserIdAndStatus(1L, Status.APPROVED));
    }

    @Test
    public void getAllProductsByUserIdTest()
    {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        List<Product> products = Arrays.asList(product);
        when(productRepo.getProductsByOwner(user)).thenReturn(products);

        List<Product> result = productService.getAllProductsByUserId(1L);

        assertNotNull(result);
        assertEquals(products, result);
    }

    @Test
    public void getAllApprovedProductsByCategoryTest()
    {
        List<Product> products = Arrays.asList(product);
        when(productRepo.findProductsByStatusAndCategory(Status.APPROVED, "Test Category")).thenReturn(products);

        List<Product> result = productService.getAllApprovedProductsByCategory("Test Category");

        assertNotNull(result);
        assertEquals(products, result);
    }

    @Test
    public void getAllApprovedProductsFollowedByUserByTitleAndCategoryTest()
    {
        List<Product> products = Arrays.asList(product);
        when(productRepo.findProductsByStatusAndTitleAndCategory(Status.APPROVED, "Test Product", "Test Category")).thenReturn(products);

        List<Product> result = productService.getAllApprovedProductsByTitleAndCategory("Test Product", "Test Category");

        assertNotNull(result);
        assertEquals(products, result);
    }

    @Test
    public void addProductTest()
    {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        productService.addProduct(product);
        verify(productRepo, times(1)).save(product);
    }

    @Test
    public void getProductByIdTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(product, result);
    }

    @Test
    public void getProductByIdNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundExceptions.class, () -> productService.getProductById(1L));
    }

    @Test
    public void updateProductTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setTitle("Updated Product");

        Product result = productService.updateProduct(updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Product", result.getTitle());
    }

    @Test
    public void updateProductNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);

        assertThrows(ProductNotFoundExceptions.class, () -> productService.updateProduct(updatedProduct));
    }

    @Test
    public void removeProductByIdTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        productService.removeProductById(1L);

        verify(productRepo, times(1)).deleteById(1L);
    }

    @Test
    public void removeProductByIdNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundExceptions.class, () -> productService.removeProductById(1L));
    }

    @Test
    public void subscribeOnProductByIdNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundExceptions.class, () -> productService.subscribeOnProductById(1L));
    }

    @Test
    public void unsubscribeFromProductByIdTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(productRepo.save(any(Product.class))).thenReturn(product);

        IdsOfFollowedProductsDto result = productService.unsubscribeFromProductById(1L);

        assertNotNull(result);
        assertEquals(0, result.getIdsOfFollowedProducts().size());
    }

    @Test
    public void unsubscribeFromProductByIdNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundExceptions.class, () -> productService.unsubscribeFromProductById(1L));
    }

    /*@Test
    public void approveProductByIdTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        user.setRole(Roles.ROLE_ADMIN);
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenReturn(product);

        Product result = productService.approveProductById(1L);

        assertNotNull(result);
        assertEquals(Status.APPROVED, result.getStatus());
    }*/

    @Test
    public void approveProductByIdNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        user.setRole(Roles.ROLE_ADMIN);
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundExceptions.class, () -> productService.approveProductById(1L));
    }

   /* @Test
    public void declineOfModerationByProductIdTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        user.setRole(Roles.ROLE_ADMIN);
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenReturn(product);

        Product result = productService.declineOfModerationByProductId(1L);

        assertNotNull(result);
        assertEquals(Status.MODERATION_DENIED, result.getStatus());
    }*/

    @Test
    public void declineOfModerationByProductIdNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        user.setRole(Roles.ROLE_ADMIN);
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundExceptions.class, () -> productService.declineOfModerationByProductId(1L));
    }

    @Test
    public void archiveProductByIdTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenReturn(product);

        productService.archiveProductById(1L);

        verify(productRepo, times(1)).save(product);
        assertEquals(Status.ARCHIVED, product.getStatus());
    }

    @Test
    public void archiveProductByIdNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundExceptions.class, () -> productService.archiveProductById(1L));
    }

   /* @Test
    public void unarchiveProductByIdTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenReturn(product);

        Product result = productService.unarchiveProductById(product.getId());

        assertNotNull(result);
        assertEquals(Status.ON_MODERATION, result.getStatus());
    }
*/

    @Test
    public void unarchiveProductByIdNotFoundTest()
    {
        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundExceptions.class, () -> productService.unarchiveProductById(1L));
    }
}
