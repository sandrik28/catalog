package ru.isaev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaev.domain.ProductDtos.ProductDto;
import ru.isaev.domain.ProductDtos.IdsOfFollowedProductsDto;
import ru.isaev.domain.ProductDtos.ProductPreviewCardDto;
import ru.isaev.domain.Products.Product;
import ru.isaev.domain.Products.Status;
import ru.isaev.service.Mapper.IMyMapper;
import ru.isaev.service.ProductService.IProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    private final IMyMapper mapper;

    @Autowired
    public ProductController(IProductService productService, IMyMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping("all_approved")
    public ResponseEntity<List<ProductPreviewCardDto>> getAllApproved() {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProducts()),
                HttpStatus.OK
        );
    }

    @GetMapping("all_approved/followed_by_user/{id}")
    public ResponseEntity<List<ProductPreviewCardDto>> getProductsFollowedByUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsFollowedByUser(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("all_approved/followed_by_user/{id}/title")
    public ResponseEntity<List<ProductPreviewCardDto>> getProductsFollowedByUserByTitle(
            @PathVariable("id") Long id,
            @RequestParam(name = "title", required = false) String title) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsFollowedByUserByTitle(id, title)),
                HttpStatus.OK
        );
    }

    @GetMapping("all_approved/followed_by_user/{id}/category")
    public ResponseEntity<List<ProductPreviewCardDto>> getProductsFollowedByUserByCategory(
            @PathVariable("id") Long id,
            @RequestParam(name = "category", required = false) String category) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsFollowedByUserByCategory(id, category)),
                HttpStatus.OK
        );
    }

    @GetMapping("all_approved/followed_by_user/{id}/title_and_category")
    public ResponseEntity<List<ProductPreviewCardDto>> getProductsFollowedByUserByTitleAndCategory(
            @PathVariable("id") Long id,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "category", required = false) String category) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsFollowedByUserByTitleAndCategory(
                        id,
                        title,
                        category)),
                HttpStatus.OK
        );
    }

    @GetMapping("all_approved/title")
    public ResponseEntity<List<ProductPreviewCardDto>> getByTitle(@RequestParam(name = "title", required = false) String title) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsByTitle(title)),
                HttpStatus.OK
        );
    }

    @GetMapping("all_approved/category")
    public ResponseEntity<List<ProductPreviewCardDto>> getAllApprovedByCategory(@RequestParam(name = "category", required = false) String category) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsByCategory(category)),
                HttpStatus.OK
        );
    }

    @GetMapping("all_approved/title_and_category")
    public ResponseEntity<List<ProductPreviewCardDto>> getAllApprovedByTitleAndCategory(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "category", required = false) String category
    ) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllApprovedProductsByTitleAndCategory(title, category)),
                HttpStatus.OK
        );
    }

    @GetMapping("/moderator/title")
    public ResponseEntity<List<ProductPreviewCardDto>> getForModeratorByTitle(@RequestParam(name = "title", required = false) String title) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsForModeratorByTitle(title)),
                HttpStatus.OK
        );
    }

    @GetMapping("/moderator/category")
    public ResponseEntity<List<ProductPreviewCardDto>> getForModeratorByCategory(@RequestParam(name = "category", required = false) String category) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsForModeratorByCategory(category)),
                HttpStatus.OK
        );
    }

    @GetMapping("moderator/title_and_category")
    public ResponseEntity<List<ProductPreviewCardDto>> getForModeratorByTitleAndCategory(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "category", required = false) String category
    ) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsForModeratorByTitleAndCategory(title, category)),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                mapper.productToProductDto(productService.getProductById(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<List<ProductPreviewCardDto>> getProductsOfUserByIdAndStatus(
            @PathVariable("id") Long id,
            @RequestParam(name = "status", required = false) Status status) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsByUserIdAndStatus(id, status)),
                HttpStatus.OK
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        Product product = mapper.productDtoToProduct(productDto);
        productService.addProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.productToProductDto(product));
    }

    @PutMapping("/edit")
    public ResponseEntity<ProductDto> editProduct(@RequestBody ProductDto productDto) {
        Product product = mapper.productDtoToProduct(productDto);
        product = productService.updateProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.productToProductDto(product));
    }

    @PostMapping("/subscribe_on_product/{id}")
    public ResponseEntity<IdsOfFollowedProductsDto> subscribeOnProductById(@PathVariable("id") Long id) {
        IdsOfFollowedProductsDto response = productService.subscribeOnProductById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/unsubscribe_from_product/{id}")
    public ResponseEntity<IdsOfFollowedProductsDto> unsubscribeFromProductById(@PathVariable("id") Long id) {
        IdsOfFollowedProductsDto response = productService.unsubscribeFromProductById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/approve_of_publishing_or_editing/{id}")
    public ResponseEntity<ProductDto> approveOfPublishingOrEditingProductById(@PathVariable("id") Long id) {
        ProductDto productDto = mapper.productToProductDto(productService.approveProductById(id));
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @PostMapping("/decline_of_moderation/{id}")
    public ResponseEntity<ProductDto> declineOfModerationByProductId(@PathVariable("id") Long id) {
        ProductDto productDto = mapper.productToProductDto(productService.declineOfModerationByProductId(id));
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @PostMapping("/unarchive/{id}")
    public ResponseEntity<Long> unarchiveProductById(@PathVariable("id") Long id) {
        productService.unarchiveProductById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/archive/{id}")
    public ResponseEntity<Long> archiveProductById(@PathVariable("id") Long id) {
        productService.archiveProductById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
