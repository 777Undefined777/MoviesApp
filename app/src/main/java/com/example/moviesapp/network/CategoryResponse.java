package com.example.moviesapp.network;

import com.example.moviesapp.model.Category;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {
    @SerializedName("genres")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }
}
