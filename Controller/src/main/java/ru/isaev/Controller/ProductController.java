package ru.isaev.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaev.Controller.Mapper.IMyMapper;
import ru.isaev.Domain.ProductDtos.ProductDto;
import ru.isaev.Domain.Products.Product;
import ru.isaev.Service.ProductService.IProductService;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class ProductController {
    private final IProductService productService;

    private final IMyMapper mapper;

    @Autowired
    public ProductController(IProductService productService, IMyMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
                mapper.productToProductDto(productService.getProductById(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAll() {
        return new ResponseEntity<>(
                mapper.mapListOfProductsToListOfDtos(productService.getAllProducts()),
                HttpStatus.OK
        );
    }
    @PostMapping("/add")
    public ResponseEntity<ProductDto> addCat(@RequestBody ProductDto productDto) {
        Product product = mapper.productDtoToProduct(productDto);
        productService.addProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @PutMapping("/edit")
    public ResponseEntity<ProductDto> editCat(@RequestBody ProductDto productDto) {
        Product product = mapper.productDtoToProduct(productDto);
        productService.updateProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(productDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCatById(@PathVariable Long id) {
        productService.removeProductById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

