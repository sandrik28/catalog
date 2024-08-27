package ru.isaev.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaev.Domain.ProductDtos.IdsOfFollowedProductsDto;
import ru.isaev.Service.Mapper.IMyMapper;
import ru.isaev.Domain.ProductDtos.ProductDto;
import ru.isaev.Domain.ProductDtos.ProductPreviewCardDto;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Domain.Products.Status;
import ru.isaev.Service.ProductService.IProductService;

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

    @GetMapping("/status")
    public ResponseEntity<List<ProductPreviewCardDto>> getByStatus(@RequestParam(name = "status", required = false) Status status) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsByStatus(status)),
                HttpStatus.OK
        );
    }

    // TODO. Is it necessary?
    @GetMapping("/followed_by_user/{id}")
    public ResponseEntity<List<ProductPreviewCardDto>> getProductsFollowedByUser(@PathVariable Long id) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsFollowedByUser(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("/title")
    public ResponseEntity<List<ProductPreviewCardDto>> getByTitle(@RequestParam(name = "title", required = false) String title) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsByTitle(title)),
                HttpStatus.OK
        );
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductPreviewCardDto>> getByCategory(@RequestParam(name = "category", required = false) String category) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsByCategory(category)),
                HttpStatus.OK
        );
    }

    @GetMapping("/title_and_category")
    public ResponseEntity<List<ProductPreviewCardDto>> getByTitleAndCategory(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "title", required = false) String category
    ) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getProductsByTitleAndCategory(title, category)),
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
            @RequestParam(name = "title", required = false) String category
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

    @GetMapping("/all")
    public ResponseEntity<List<ProductPreviewCardDto>> getAll() {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllProducts()),
                HttpStatus.OK
        );
    }


    @GetMapping("/all/{id}")
    public ResponseEntity<List<ProductPreviewCardDto>> getAllProductsOfUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfProductPreviewCardDtos(productService.getAllProductsByUserId(id)),
                HttpStatus.OK
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        Product product = mapper.productDtoToProduct(productDto);
        productService.addProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @PutMapping("/edit")
    public ResponseEntity<ProductDto> editProduct(@RequestBody ProductDto productDto) {
        Product product = mapper.productDtoToProduct(productDto);
        productService.updateProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(productDto);
    }

    @PostMapping("/subscribe_on_product/{id}")
    public ResponseEntity<IdsOfFollowedProductsDto> subscribeOnProductById(@PathVariable Long id) {
        IdsOfFollowedProductsDto response = productService.subscribeOnProductById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/unsubscribe_from_product/{id}")
    public ResponseEntity<IdsOfFollowedProductsDto> unsubscribeFromProductById(@PathVariable Long id) {
        IdsOfFollowedProductsDto response = productService.unsubscribeFromProductById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/approve_of_publishing_or_editing/{id}")
    public ResponseEntity<ProductDto> approveOfPublishingOrEditingProductById(@PathVariable Long id) {
        ProductDto productDto = mapper.productToProductDto(productService.approveProductById(id));
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @PostMapping("/decline_of_moderation/{id}")
    public ResponseEntity<ProductDto> declineOfModerationByProductId(@PathVariable Long id) {
        ProductDto productDto = mapper.productToProductDto(productService.declineOfModerationByProductId(id));
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @PostMapping("/unarchive/{id}")
    public ResponseEntity<Void> unarchiveProductById(@PathVariable Long id) {
        productService.unarchiveProductById(id);
        return (ResponseEntity<Void>) ResponseEntity.status(HttpStatus.CREATED);
    }

    @PostMapping("/archive/{id}")
    public ResponseEntity<Void> archiveProductById(@PathVariable Long id) {
        productService.archiveProductById(id);
        return (ResponseEntity<Void>) ResponseEntity.status(HttpStatus.CREATED);
    }
}
