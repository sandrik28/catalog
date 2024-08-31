package ru.isaev.controller_made_by_isaev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaev.domain_made_by_isaev.productDtos.ProductDto;
import ru.isaev.domain_made_by_isaev.productDtos.IdsOfFollowedProductsDto;
import ru.isaev.domain_made_by_isaev.productDtos.ProductPreviewCardDto;
import ru.isaev.domain_made_by_isaev.products.Product;
import ru.isaev.domain_made_by_isaev.products.Status;
import ru.isaev.service_made_by_isaev.mapper.IMyMapper;
import ru.isaev.service_made_by_isaev.productService.IProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LogManager.getLogger(ProductController.class);

    private final IProductService productService;
    private final IMyMapper mapper;

    @Autowired
    public ProductController(IProductService productService, IMyMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping("all_approved")
    public ResponseEntity<List<ProductPreviewCardDto>> getAllApproved() {
        logger.info("ProductController - Received request to get all approved products");
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProducts());
        logger.info("ProductController - All approved products retrieved successfully");
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("all_approved/followed_by_user/{id}")
    public ResponseEntity<List<ProductPreviewCardDto>> getProductsFollowedByUser(@PathVariable("id") Long id) {
        logger.info("ProductController - Received request to get products followed by user with ID: {}", id);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsFollowedByUser(id));
        logger.info("ProductController - Products followed by user with ID {} retrieved successfully", id);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("all_approved/followed_by_user/{id}/title")
    public ResponseEntity<List<ProductPreviewCardDto>> getProductsFollowedByUserByTitle(
            @PathVariable("id") Long id,
            @RequestParam(name = "title") String title) {
        logger.info("ProductController - Received request to get products followed by user with ID {} and title: {}", id, title);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsFollowedByUserByTitle(id, title));
        logger.info("ProductController - Products followed by user with ID {} and title {} retrieved successfully", id, title);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("all_approved/followed_by_user/{id}/category")
    public ResponseEntity<List<ProductPreviewCardDto>> getProductsFollowedByUserByCategory(
            @PathVariable("id") Long id,
            @RequestParam(name = "category") String category) {
        logger.info("ProductController - Received request to get products followed by user with ID {} and category: {}", id, category);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsFollowedByUserByCategory(id, category));
        logger.info("ProductController - Products followed by user with ID {} and category {} retrieved successfully", id, category);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("all_approved/followed_by_user/{id}/title_and_category")
    public ResponseEntity<List<ProductPreviewCardDto>> getProductsFollowedByUserByTitleAndCategory(
            @PathVariable("id") Long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "category") String category) {
        logger.info("ProductController - Received request to get products followed by user with ID {}, title: {} and category: {}", id, title, category);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsFollowedByUserByTitleAndCategory(id, title, category));
        logger.info("ProductController - Products followed by user with ID {}, title {} and category {} retrieved successfully", id, title, category);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("all_approved/title")
    public ResponseEntity<List<ProductPreviewCardDto>> getByTitle(@RequestParam(name = "title") String title) {
        logger.info("ProductController - Received request to get approved products by title: {}", title);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsByTitle(title));
        logger.info("ProductController - Approved products by title {} retrieved successfully", title);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<List<ProductPreviewCardDto>> getAllByTitle(@RequestParam(name = "title") String title) {
        logger.info("ProductController - Received request to get all products by title: {}", title);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsByTitle(title));
        logger.info("ProductController - All products by title {} retrieved successfully", title);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("all_approved/category")
    public ResponseEntity<List<ProductPreviewCardDto>> getAllApprovedByCategory(@RequestParam(name = "category") String category) {
        logger.info("ProductController - Received request to get approved products by category: {}", category);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsByCategory(category));
        logger.info("ProductController - Approved products by category {} retrieved successfully", category);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("all_approved/title_and_category")
    public ResponseEntity<List<ProductPreviewCardDto>> getAllApprovedByTitleAndCategory(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "category") String category
    ) {
        logger.info("ProductController - Received request to get approved products by title: {} and category: {}", title, category);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsByTitleAndCategory(title, category));
        logger.info("ProductController - Approved products by title {} and category {} retrieved successfully", title, category);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/moderator/title")
    public ResponseEntity<List<ProductPreviewCardDto>> getForModeratorByTitle(@RequestParam(name = "title") String title) {
        logger.info("ProductController - Received request to get products for moderator by title: {}", title);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsForModeratorByTitle(title));
        logger.info("ProductController - Products for moderator by title {} retrieved successfully", title);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/moderator/category")
    public ResponseEntity<List<ProductPreviewCardDto>> getForModeratorByCategory(@RequestParam(name = "category") String category) {
        logger.info("ProductController - Received request to get products for moderator by category: {}", category);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsForModeratorByCategory(category));
        logger.info("ProductController - Products for moderator by category {} retrieved successfully", category);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("moderator/title_and_category")
    public ResponseEntity<List<ProductPreviewCardDto>> getForModeratorByTitleAndCategory(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "category") String category
    ) {
        logger.info("ProductController - Received request to get products for moderator by title: {} and category: {}", title, category);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsForModeratorByTitleAndCategory(title, category));
        logger.info("ProductController - Products for moderator by title {} and category {} retrieved successfully", title, category);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable("id") Long id) {
        logger.info("ProductController - Received request to get product by ID: {}", id);
        ProductDto productDto = mapper.productToProductDto(productService.getProductById(id));
        logger.info("ProductController - Product with ID {} retrieved successfully", id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<List<ProductPreviewCardDto>> getProductsOfUserByIdAndStatus(
            @PathVariable("id") Long id,
            @RequestParam(name = "status") Status status) {
        logger.info("ProductController - Received request to get products of user by ID: {} and status: {}", id, status);
        List<ProductPreviewCardDto> productDtos = mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsByUserIdAndStatus(id, status));
        logger.info("ProductController - Products of user with ID {} and status {} retrieved successfully", id, status);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        logger.info("ProductController - Received request to add product: {}", productDto);
        Product product = mapper.productDtoToProduct(productDto);
        productService.addProduct(product);
        logger.info("ProductController - Product added successfully: {}", productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.productToProductDto(product));
    }

    @PutMapping("/edit")
    public ResponseEntity<ProductDto> editProduct(@RequestBody ProductDto productDto) {
        logger.info("ProductController - Received request to edit product: {}", productDto);
        Product product = mapper.productDtoToProduct(productDto);
        product = productService.updateProduct(product);
        logger.info("ProductController - Product edited successfully: {}", productDto);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.productToProductDto(product));
    }

    @PostMapping("/subscribe_on_product/{id}")
    public ResponseEntity<IdsOfFollowedProductsDto> subscribeOnProductById(@PathVariable("id") Long id) {
        logger.info("ProductController - Received request to subscribe on product by ID: {}", id);
        IdsOfFollowedProductsDto response = productService.subscribeOnProductById(id);
        logger.info("ProductController - Subscribed on product with ID {} successfully", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/unsubscribe_from_product/{id}")
    public ResponseEntity<IdsOfFollowedProductsDto> unsubscribeFromProductById(@PathVariable("id") Long id) {
        logger.info("ProductController - Received request to unsubscribe from product by ID: {}", id);
        IdsOfFollowedProductsDto response = productService.unsubscribeFromProductById(id);
        logger.info("ProductController - Unsubscribed from product with ID {} successfully", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/approve_of_publishing_or_editing/{id}")
    public ResponseEntity<ProductDto> approveOfPublishingOrEditingProductById(@PathVariable("id") Long id) {
        logger.info("ProductController - Received request to approve product by ID: {}", id);
        ProductDto productDto = mapper.productToProductDto(productService.approveProductById(id));
        logger.info("ProductController - Product with ID {} approved successfully", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @PostMapping("/decline_of_moderation/{id}")
    public ResponseEntity<ProductDto> declineOfModerationByProductId(@PathVariable("id") Long id) {
        logger.info("ProductController - Received request to decline moderation of product by ID: {}", id);
        ProductDto productDto = mapper.productToProductDto(productService.declineOfModerationByProductId(id));
        logger.info("ProductController - Moderation of product with ID {} declined successfully", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @PostMapping("/unarchive/{id}")
    public ResponseEntity<Long> unarchiveProductById(@PathVariable("id") Long id) {
        logger.info("ProductController - Received request to unarchive product by ID: {}", id);
        productService.unarchiveProductById(id);
        logger.info("ProductController - Product with ID {} unarchived successfully", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/archive/{id}")
    public ResponseEntity<Long> archiveProductById(@PathVariable("id") Long id) {
        logger.info("ProductController - Received request to archive product by ID: {}", id);
        productService.archiveProductById(id);
        logger.info("ProductController - Product with ID {} archived successfully", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}