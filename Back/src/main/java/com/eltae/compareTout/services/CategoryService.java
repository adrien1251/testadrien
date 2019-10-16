package com.eltae.compareTout.services;

import com.eltae.compareTout.entities.Category;
import com.eltae.compareTout.exceptions.BadCsvLine;
import com.eltae.compareTout.repositories.CategoryRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public int create(MultipartFile multipartFile) {
        try {
            return this.readCSV(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int readCSV(InputStream inputStream) throws IOException {
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8), ';');
        String[] nextRecord;
        int nbLineAdd = 0;
        while ((nextRecord = csvReader.readNext()) != null) {
            if(this.saveCategorie(nextRecord, 1)) nbLineAdd++;
        }
        return nbLineAdd;
    }

    private boolean saveCategorie(String[] records, int idx) {
        if (!(idx < records.length) || records[idx].isEmpty()) return true;
        String actualCategoryName = records[idx];
        String childCategoryName = idx + 1 < records.length && !records[idx + 1].isEmpty() ? records[idx + 1] : null;
        idx++;

        Optional<Category> OActualCategory = this.categoryRepository.findByName(actualCategoryName);
        if (!OActualCategory.isPresent()) {
            Category actualCategory = Category.builder().name(actualCategoryName).build();
            List<Category> childList = new ArrayList<>();
            /* Crée le fils uniquement si il peut en avoir un */
            if (childCategoryName != null) {
                Optional<Category> OChildCategory = this.categoryRepository.findByName(childCategoryName);
                Category childCategory = null;
                if (!OChildCategory.isPresent()) {
                    childCategory = Category.builder().name(childCategoryName).parent(actualCategory).build();
                } else {
                    childCategory = OChildCategory.get();
                    if (childCategory.getParent() != null) {
                        throw new BadCsvLine(HttpStatus.resolve(566), "Child category `" + childCategory.getName() + "` has already parent[`" + childCategory.getParent().getName() + "`]");
                    }
                }
                childList.add(childCategory);
            }
            actualCategory.setChildList(childList);
            this.categoryRepository.save(actualCategory);
        } else {
            if (childCategoryName == null) {
                return this.saveCategorie(records, idx);
            }
            //Categ existe et on doit lui rajouter un fils
            Category actualCategory = OActualCategory.get();
            Optional<Category> OChildCategory = this.categoryRepository.findByName(childCategoryName);
            if (OChildCategory.isPresent()) {
                if (OChildCategory.get().getParent() == null) {
                    OChildCategory.get().setParent(actualCategory);
                    List<Category> childList = actualCategory.getChildList();
                    if (childList == null) childList = new ArrayList<>();
                    childList.add(OChildCategory.get());
                    this.categoryRepository.save(actualCategory);

                } else {
                    if (actualCategory.getId().equals(OChildCategory.get().getParent().getId())) {
                        return this.saveCategorie(records, idx);
                    } else {
                        throw new BadCsvLine(HttpStatus.resolve(566), "Child category `" + OChildCategory.get().getName() + "` has already parent[`" + OChildCategory.get().getParent().getName() + "`]");
                    }
                }
            }

            Category childCategory = Category.builder().name(childCategoryName).childList(new ArrayList<>()).parent(actualCategory).build();
            List<Category> childList = actualCategory.getChildList();
            if(childList == null) childList = new ArrayList<>();
            childList.add(childCategory);

            this.categoryRepository.save(actualCategory);
//            this.categoryRepository.save(childCategory);
        }
        return this.saveCategorie(records, idx);
    }
}