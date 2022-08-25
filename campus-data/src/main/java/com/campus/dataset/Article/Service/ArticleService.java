package com.campus.dataset.Article.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.common.Entity.Resp;
import com.campus.common.Entity.RespContainData;
import com.campus.dataset.Article.Entity.Article;
import com.campus.dataset.Article.Entity.ArticleDoc;

import java.util.List;

public interface ArticleService extends IService<Article> {

    ArticleDoc fillPlate(ArticleDoc info);
    Resp addArticle(ArticleDoc info);
    Resp updateArticle(ArticleDoc info);
    Resp delArticle(ArticleDoc info);
    Article getArticleByPlateAndTitle(ArticleDoc info);
    Resp setAdArticle(ArticleDoc info);

    RespContainData showManageArticle();
    RespContainData showPlateArticle();

    List<Article> showArticleFilter(List<Article> list);

    Resp shiftIntoText(String title);
}
