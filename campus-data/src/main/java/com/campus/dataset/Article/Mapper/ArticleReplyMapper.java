package com.campus.dataset.Article.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.dataset.Article.Entity.ArticleReply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ArticleReplyMapper extends BaseMapper<ArticleReply> {
}
