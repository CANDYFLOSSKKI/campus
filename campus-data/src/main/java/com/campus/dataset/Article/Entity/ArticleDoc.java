package com.campus.dataset.Article.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDoc {
    private String plate;
    private String title;
    private String content;
}
