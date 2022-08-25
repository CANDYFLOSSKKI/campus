package com.campus.dataset.Article.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleReplyDoc{
    private String content;
    private Integer replyID;
}
