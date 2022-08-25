package com.campus.dataset.Article.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.dataset.Article.Entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {}
