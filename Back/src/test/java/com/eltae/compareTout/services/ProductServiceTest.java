package com.eltae.compareTout.services;


import com.eltae.compareTout.converter.CategoryConverter;
import com.eltae.compareTout.converter.CriteriaProductConverter;
import com.eltae.compareTout.converter.product.ProductConverter;
import com.eltae.compareTout.dto.criteria.CriteriaFilterDto;
import com.eltae.compareTout.dto.criteria.CriteriaProductDto;
import com.eltae.compareTout.dto.product.ProductDto;
import com.eltae.compareTout.dto.product.ProductDtoForFront;
import com.eltae.compareTout.dto.product.ShortProductDto;
import com.eltae.compareTout.entities.*;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.repositories.CategoryRepository;
import com.eltae.compareTout.repositories.CriteriaProductRepository;
import com.eltae.compareTout.repositories.CriteriaRepository;
import com.eltae.compareTout.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {
    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    ProductConverter productConverter;

    @MockBean
    CriteriaRepository criteriaRepository;

    @MockBean
    CriteriaService criteriaService;

    @MockBean
    CriteriaProductRepository criteriaProductRepository;

    @Autowired
    CriteriaProductConverter criteriaProductConverter;

    @Test
    public void testGetAllProductsByCategoryReturnAllProductWhenGreatCategoryParam(){
        //Entry
        Long categoryId = 1L;

        //Effective
        List<Product> productReturnByRepo = new ArrayList<>();
        List<ProductDtoForFront> effectiveProductDtoForFronts= new ArrayList<>();

        //Mock
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(Category.builder().build()));
        Mockito.when(productRepository.findAllByCategoryId(categoryId)).thenReturn(productReturnByRepo);

        //Expected
        List<ProductDtoForFront> expectedProductDtoForFront = productService.getAllProductsByCategory(categoryId);

        assertEquals(effectiveProductDtoForFronts, expectedProductDtoForFront);
    }

    @Test(expected = ApplicationException.class)
    public void testGetAllProductsByCategoryReturnApplicationExceptionWhenCategoryNotExist() {
            //Entry
            Long categoryId = 1L;

            //Effective

            //Mock
            Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

            List<ProductDtoForFront> expectedProductDtoForFront = productService.getAllProductsByCategory(categoryId);
    }

    @Test(expected = ApplicationException.class)
    public void testGetAllProductByCategoryAndCriteriaReturnExceptionIfCategoryNotExist(){
        //Entry
        Long categoryId = 1L;
        List<CriteriaFilterDto> criteriaFilterDtos = new ArrayList<>();
        //Effective

        //Mock
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        productService.getAllProductByCategoryAndCriteria(categoryId, criteriaFilterDtos);
    }


    @Test(expected = ApplicationException.class)
    public void testGetAllProductByCategoryAndCriteriaReturnExceptionIfCriteriaNotExist(){
        //Entry
        Long categoryId = 1L;
        List<CriteriaFilterDto> criteriaFilterDtos = new ArrayList<>();
        criteriaFilterDtos.add(CriteriaFilterDto.builder()
                .idCriteria(1L)
                .build());
        //Effective

        //Mock
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(Category.builder().build()));
        Mockito.when(criteriaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        productService.getAllProductByCategoryAndCriteria(categoryId, criteriaFilterDtos);
    }



    @Test(expected = ApplicationException.class)
    public void testGetAllCriteriaByProductReturnExceptionIfProductNotExist(){
        //Entry
        Long categoryId = 1L;
        List<CriteriaFilterDto> criteriaFilterDtos = new ArrayList<>();
        criteriaFilterDtos.add(CriteriaFilterDto.builder()
                .idCriteria(1L)
                .build());
        //Mock
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(null);

        productService.getAllCriteriaByProduct(categoryId);
    }

    /*
    @Test
    public void testGetAllProduct(){
        Category category = Category.builder().name("cat").id(100L).build();
        Supplier s = Supplier.builder().firstName("firstname").id(1000L).lastName("lastName").build();


        List<Product> listReturnedByFind = new ArrayList<>();
        listReturnedByFind.add(Product.builder().id(1L).name("test1").supplierLink("url1").description("desc1").supplier(s).category(category).build());
        listReturnedByFind.add(Product.builder().id(2L).name("test2").supplierLink("url2").description("desc2").supplier(s).category(category).build());

        List<ShortProductDto> listReturnedConverter = productConverter.listEntityToShortDto(listReturnedByFind);

        List<ShortProductDto> listExpected = new ArrayList<>();
        listExpected.add(ShortProductDto.builder().id(1L).name("test1").build());
        listExpected.add(ShortProductDto.builder().id(2L).name("test2").build());



        Mockito.when(productRepository.findAll()).thenReturn(listReturnedByFind);
        Mockito.when(productConverter.listEntityToShortDto(listReturnedByFind)).thenReturn(listReturnedConverter);

        List<ShortProductDto> listReturned= this.productService.getAll();

        //assertEquals(listExpected,listReturned);
    }

     */


    @Test
    public void testCheckAllMandatoryCriteriaPresentInFileReturnTrue(){
        String recordsToTest[] = {"A","B","C","D","E","1","F","2","G","3"};
        ArrayList<Long> idList = new ArrayList<>();
        idList.add(1L); idList.add(2L); idList.add(3L);
        assertTrue(this.productService.checkAllMandatoryCriteriaPresentInFile(recordsToTest,idList));
    }


    @Test
    public void testCheckAllMandatoryCriteriaPresentInFileReturnFalse(){
        String recordsToTest[] = {"A","B","C","D","E","1","F","2","G"};
        ArrayList<Long> idList = new ArrayList<>();
        idList.add(1L); idList.add(2L); idList.add(3L);
        assertFalse(this.productService.checkAllMandatoryCriteriaPresentInFile(recordsToTest,idList));
    }

    @Test
    public void testInsertProduct(){
        Supplier supplier = Supplier.builder().id(6L).firstName("test").lastName("test").build();

        Criteria criteriaMocked = Criteria.builder().build();
        Category categoryMocked = Category.builder()
                .name("Category").id(1L).categoryCriteriaList(new ArrayList<>()).childList(new ArrayList<>()).build();
        categoryMocked.getCriteriaList().add(criteriaMocked);

        CategoryCriteria ccMocked = CategoryCriteria.builder().pk(new CategoryCriteriaPK()).build();
        ccMocked.getPk().setCategory(categoryMocked); ccMocked.getPk().setCriteria_cat(criteriaMocked);

        categoryMocked.getCategoryCriteriaList().add(ccMocked);

        String recordsToTest[] = {"A","B","C","D","1","1","F"};
        ArrayList<Long> idListMocked = new ArrayList<>();
        idListMocked.add(1L);
        Product productEntrySave = Product.builder()
                .category(categoryMocked)
                .name("A".toLowerCase())
                .description("B")
                .supplierLink("C")
                .criteriaProducts(new ArrayList<>())
                .supplier(supplier)
                .build();
        Product productReturnedBySave = Product.builder().name("product").id(10L).category(categoryMocked).supplier(supplier).build();




        Mockito.when(this.categoryService.getCategoryWithId(1L)).thenReturn(categoryMocked);
        Mockito.when(criteriaService.getAllMandatoryCriteriasIdWithIdCategory(categoryMocked)).thenReturn(idListMocked);
        ProductService productServiceMock = org.mockito.Mockito.mock(ProductService.class);
        Mockito.when(productServiceMock.checkAllMandatoryCriteriaPresentInFile(recordsToTest, idListMocked)).thenReturn(true);
        Mockito.when(productRepository.save(productEntrySave)).thenReturn(productReturnedBySave);

        Mockito.when(criteriaService.getCriteriaProductWithIdCriteria(1L)).thenReturn(criteriaMocked);

        assertTrue(productService.insertProduct(recordsToTest,1,supplier));
    }




     @Test
    public void testGetSupplierProducts(){

        Category c = Category.builder().id(1L).build();
        List<Product> productList = new ArrayList<>();
        productList.add(Product.builder().id(1L).category(c).build());

        List<ShortProductDto> listReturnByConverter = new ArrayList<>();
        listReturnByConverter.add(ShortProductDto.builder().category(1L).id(1L).build());

        Mockito.when(productRepository.findAllBySupplierId(1L)).thenReturn(productList);
        Mockito.when(productConverter.listEntityToShortDto(productList)).thenReturn(listReturnByConverter);

         List<ShortProductDto> res = this.productService.getSupplierProducts(1L);

        assertEquals(listReturnByConverter, res);
     }


}
