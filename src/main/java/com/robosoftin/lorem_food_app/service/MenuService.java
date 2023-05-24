package com.robosoftin.lorem_food_app.service;

import com.robosoftin.lorem_food_app.dao.MenuItemRepository;
import com.robosoftin.lorem_food_app.entity.Restaurant.MenuItem;
import com.robosoftin.lorem_food_app.enums.DishCategory;
import com.robosoftin.lorem_food_app.enums.DishType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuItemRepository menuItemRepository;
    public List<MenuItem> fetchMenu(int restId, Pageable paging){
        return menuItemRepository.findRestaurantMenu(restId,paging).getContent();
    }

    public List<MenuItem> getMenuItemsByPattern(int restId,String pattern){
        String keyword=pattern.toLowerCase();
        if(keyword.equals("breakfast"))
            return menuItemRepository.findBreakfastMenu(restId);
        else
        {
            DishCategory dishCategory=fetchDishCategory(keyword);
            DishType dishType=fetchDishType(keyword);
            return getFilteredData(dishCategory,dishType,restId,keyword);
        }
    }

    private List<MenuItem> getFilteredData(DishCategory dishCategory,DishType dishType,int restId,String keyword){
        if(dishType == DishType.OTHER){
            return dishCategory==DishCategory.NONE?menuItemRepository.findByPattern(restId,keyword):
            menuItemRepository.findByCategory(restId, dishCategory == DishCategory.VEG);
        }else {
            return dishCategory==DishCategory.NONE?menuItemRepository.findByDishType(restId,dishType):
            menuItemRepository.findByDishTypeAndCategory(restId,dishType, dishCategory == DishCategory.VEG);
        }
    }

    private DishCategory fetchDishCategory(String keyword){
        DishCategory dishCategory=DishCategory.NONE;
        if(keyword.contains("veg"))
            dishCategory=DishCategory.VEG;
        if(keyword.contains("nonveg") || keyword.contains("non veg"))
            dishCategory=DishCategory.NONVEG;
        return dishCategory;
    }

    private DishType fetchDishType(String keyword){
        DishType dishType=DishType.OTHER;
        if(keyword.contains("appetizer") || keyword.contains("appetizers") || keyword.contains("starter") || keyword.contains("starters"))
            dishType=DishType.APPETIZER;
        if(keyword.contains("soup") || keyword.contains("soups"))
            dishType=DishType.SOUP;
        if(keyword.contains("maincourse") || keyword.contains("lunch") || keyword.contains("dinner"))
            dishType=DishType.MAINCOURSE;
        if(keyword.contains("gravy") || keyword.contains("gravies"))
            dishType=DishType.GRAVY;
        if(keyword.contains("beverage") || keyword.contains("beverages") || keyword.contains("drink") || keyword.contains("drinks"))
            dishType=DishType.BEVERAGE;
        return dishType;
    }
}
